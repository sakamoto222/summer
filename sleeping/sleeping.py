from flask import Flask, g, render_template, request, redirect, url_for, flash
import sqlite3
import matplotlib.pyplot as plt
import japanize_matplotlib
from matplotlib.backends.backend_agg import FigureCanvasAgg as FigureCanvas
from matplotlib.figure import Figure
import io
import base64
from datetime import datetime

app = Flask(__name__)
app.secret_key = 'supersecretkey'  # Flaskのセッション用の秘密鍵

DATABASE = 'sleep_data.db'

# データベースに接続する関数
def get_db():
    # ｇをきちんと定義する
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

# アプリケーションコンテキストが終了したときにデータベース接続を閉じる
@app.teardown_appcontext
def close_connection(exception):
    # ｇをきちんと定義する
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

# DBの初期化関数
def init_db():
    with app.app_context():
        db = get_db()
        cursor = db.cursor()

        # テーブルが存在しない場合は作成
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS sleep_data (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nickname TEXT,
                sleep_time INTEGER,
                wakeup_mood INTEGER,
                date DATE
            )
        ''')
        db.commit()

# ルートURLへのリクエストがあったとき
@app.route('/')
def index():
    return render_template('index.html')

# submitで送られてきたとき
@app.route('/submit', methods=['POST'])
def submit():
    nickname = request.form['nickname']
    sleep_time = int(request.form['sleep_time'])
    wakeup_mood = int(request.form['wakeup_mood'])

    # データベースにデータを挿入
    db = get_db()
    cursor = db.cursor()
    cursor.execute('''
        INSERT INTO sleep_data (nickname, sleep_time, wakeup_mood, date)
        VALUES (?, ?, ?, CURRENT_DATE)
    ''', (nickname, sleep_time, wakeup_mood))
    db.commit()

    # 目標達成の条件を設定（例: 睡眠時間が7時間以上かつ起床時の気分が8以上）
    if sleep_time >= 7 and wakeup_mood >= 8:
        flash('おめでとうございます！目標が達成されました。', 'success')

    return redirect(url_for('index'))

# graph() 関数内の SQL クエリを修正して、現在の月のデータを取得するようにします。
@app.route('/graph')
def graph():
    # 現在の月の睡眠時間データを取得
    db = get_db()
    cursor = db.cursor()
    cursor.execute('''
        SELECT date, sleep_time
        FROM sleep_data
        WHERE strftime('%Y-%m', date) = strftime('%Y-%m', CURRENT_DATE, 'localtime')
    ''')
    data = cursor.fetchall()

    if not data:
        flash('今月のデータがありません。', 'warning')
        return redirect(url_for('index'))

        # グラフの描画
    dates = [row[0] for row in data]
    sleep_times = [row[1] for row in data]

    # 日付データをdatetimeオブジェクトに変換する
    dates = [datetime.strptime(date, '%Y-%m-%d') for date in dates]

    # 日付と睡眠時間のデータをソートする
    sorted_data = sorted(zip(dates, sleep_times), key=lambda x: x[0])
    sorted_dates, sorted_sleep_times = zip(*sorted_data)

    # 日付をx軸のラベルとして使用するためのリスト
    x_labels = []

    # Matplotlibを使用してサブプロットを作成するためのコード
    fig, ax = plt.subplots()

    # データをプロットする
    for i, (date, sleep_time) in enumerate(zip(sorted_dates, sorted_sleep_times)):
        ax.plot([date], [sleep_time], marker='o', color='b')  # 日付ごとにプロット

        # x軸のラベルとして日付を追加
        x_labels.append(date.strftime('%Y-%m-%d'))

    # 前後の日付のデータのみを線でつなぐ
    for i in range(1, len(sorted_dates)):
        ax.plot([sorted_dates[i - 1], sorted_dates[i]], [sorted_sleep_times[i - 1], sorted_sleep_times[i]], color='b')

    # x軸のラベルを設定
    ax.set_xticks(sorted_dates)
    # x軸のラベルを45度で回転
    ax.set_xticklabels(x_labels, rotation=45, ha='right', fontsize=7)
    ax.set_xlabel('日付')
    # y軸のラベルを設定
    ax.set_ylabel('睡眠時間 (時間)')
    ax.set_title('現在の月の睡眠時間推移')

    # グラフを画像に変換
    img = io.BytesIO()
    plt.savefig(img, format='png')
    img.seek(0)
    plot_url = base64.b64encode(img.getvalue()).decode()

    return render_template('graph.html', plot_url=plot_url)


# insert_test_data() 関数を修正して、現在の月のテストデータを挿入するようにします。
def insert_test_data():
    # Flaskのアプリケーションコンテキストをセット
    with app.app_context():
        db = get_db()
        try:
            cursor = db.cursor()
            test_data = [
                ('2024-02-01', 7),
                ('2024-02-02', 6),
                ('2024-02-03', 7.5),
                # 他の日付も同様に追加
            ]
            # テストデータをループして挿入
            for date, sleep_time in test_data:
                cursor.execute('''
                    INSERT INTO sleep_data (date, sleep_time)
                    VALUES (?, ?)
                ''', (date, sleep_time))
            db.commit()
        except Exception as e:
            db.rollback()
            raise e
        finally:
            if cursor:
                cursor.close()

# このコードが直接実行された場合の処理
if __name__ == '__main__':
    init_db()  # データベースの初期化
    insert_test_data()  # テストデータの挿入
    app.run(debug=True)



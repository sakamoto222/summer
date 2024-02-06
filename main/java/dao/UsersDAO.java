package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;
import model.UsersLogin;

public class UsersDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/memo";
	private final String USER = "postgres";
	private final String PASS = "admin"; 
	
	UsersLogin user = null;
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	public UsersLogin findByLogin(Login login) {
//	SELECT文を準備
	String sql = "SELECT username,password FROM users WHERE username = ? AND password = ?;";
	
//データベースへ接続
	try(Connection conn = DriverManager.getConnection(URL, USER, PASS)){

	//preparedステートメントを生成
		pStmt = conn.prepareStatement(sql);
		pStmt.setString(1, login.getUsername());
		pStmt.setString(2, login.getPassword());
	//	SELECT文を実行 検索
		rs = pStmt.executeQuery();
	//	一致したユーザーが存在した場合
	//	そのユーザーを表すAccountインスタンスを生成
		if(rs.next()) {
	//		結果表からデータを取得
			String username = rs.getString("username");
			String password = rs.getString("password");
			user = new UsersLogin(username,password);
		}
	}catch(SQLException e) {
		e.printStackTrace();
		return null;
	}finally {
	//	データベース閉じる
		try {
			if(rs != null)rs.close();
			if(pStmt != null)pStmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//見つかったユーザーまたはnullを返す
	return user;
	}
	
	public void setUser(String username, String password) {
	    String sql = "INSERT INTO users(username, password) VALUES(?, ?);";

	    // Connectionを定義
	    Connection conn = null;
	    try {
	        // データベースへ接続
	        conn = DriverManager.getConnection(URL, USER, PASS);
	        // preparedステートメントを生成
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        // パラメータに値をセット
	        stmt.setString(1, username);
	        stmt.setString(2, password);

	        // SQLの実行
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        // エラーハンドリング
	        System.out.println("データをセットできませんでした");
	        System.out.println(e);
	        try {
	            // ロールバック処理
	            if (conn != null) {
	                conn.rollback();
	                System.out.println("ロールバック処理を行いました");
	            }
	        } catch (SQLException rollbackException) {
	            System.out.println("ロールバックできませんでした");
	            System.out.println(rollbackException);
	        }
	    } finally {
	        // Connectionをクローズ
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException closeException) {
	            System.out.println("Connectionをクローズできませんでした");
	            System.out.println(closeException);
	        }
	    }
	}
}

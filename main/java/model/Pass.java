package model;

public class Pass {
//	パスワードを表すクラス
		private String username;
		
		private String password;
		
		public Pass() {
		}
		
//	フィールド初期化コンストラクタ
		public Pass(String username, String password) {
			this.username = username;
			this.password = password;
		}
//	UserNameメソッド
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
}

package model;

public class UsersLogin {
	private String username;
	private String password;
	
	public UsersLogin() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public UsersLogin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() { return username; }
	public String getPassword() { return password; }
}
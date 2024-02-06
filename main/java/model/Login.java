package model;

public class Login {
	private String username;
	private String password;
	
	public Login() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {return username;}
	public String getPassword() {return password;}
}

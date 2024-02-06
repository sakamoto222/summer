package model;

import dao.UsersDAO;

public class LoginLogic {
	public boolean execute(Login login) {
		UsersDAO dao = new UsersDAO();
		UsersLogin user = dao.findByLogin(login);
		return user != null;
	}
}

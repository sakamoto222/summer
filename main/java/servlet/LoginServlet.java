package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemoDAO;
import model.Login;
import model.LoginLogic;
import model.Memo;
import model.PasswordHasher;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if(username == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}else {
			MemoDAO mdao = new MemoDAO();
			List<Memo>memoList = new ArrayList<>();
			memoList = mdao.findMemo(username);
			session.setAttribute("memoList", memoList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainmemo.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
//		リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
//			入力されたパスワードをハッシュ化
			String hashedPass = PasswordHasher.hashPassword(password);
			
//		ログイン処理の実行
			Login login = new Login(username,hashedPass);
			LoginLogic bo = new LoginLogic();
			boolean result = bo.execute(login);
//		ログイン処理の成否によって処理を分岐
			if(result) {//ログイン成功時
//			セッションスコープにユーザーIDを保存
			session.setAttribute("username", username);
				
//		ログイン後に表示するメモリストを表示
			MemoDAO mdao = new MemoDAO();
			List<Memo>memoList = new ArrayList<>();
			memoList = mdao.findMemo(username);
			session.setAttribute("memoList", memoList);
				
//			フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainmemo.jsp");
				dispatcher.forward(request, response);
			}else {//ログイン失敗時
//			リダイレクト
				request.setAttribute("errorMsg", "ユーザー名とパスワードが違います");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

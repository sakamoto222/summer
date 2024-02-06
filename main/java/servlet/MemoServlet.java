package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemoDAO;
import model.Memo;

/**
 * Servlet implementation class MemoServlet
 */
@WebServlet("/MemoServlet")
public class MemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Memo> memoList = new ArrayList<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action");

	        if (action == null) {
	            action = "view"; // デフォルトのアクションはメモを表示する
	        }

	        switch (action) {
	            case "add":
	                addMemo(request, response);
	                break;
	            case "view":
	            default:
	                viewMemos(request, response);
	                break;
	        }
	    }

	private void addMemo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Smemo_id = request.getParameter("memo_id");
		 // IntegerクラスのparseInt()メソッドを使用してString型をint型に変換
		int memo_id =  Integer.parseInt(Smemo_id);
		String Sdate = request.getParameter("date");
	    String content = request.getParameter("content");
	    String username = null;
	   
	    
	    try {
	    	
            // 変更する日付のフォーマットを指定
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 日付文字列を java.util.Dateに変換
            java.util.Date parsedDate = dateFormat.parse(Sdate);
            // java.util.Date を java.sql.Date に変換
            Date date = new java.sql.Date(parsedDate.getTime());
            
            Memo memo = new Memo(memo_id,date, content);
    	    memoList.add(memo);
//    	    MemoDAOをインスタンス化
    	    MemoDAO mdao = new MemoDAO();
//    	    addmemoメソッドを利用してDBに追加
    	    mdao.addmemo(date, content, username);
    	
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private void viewMemos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setAttribute("memoList", memoList);
	    request.getRequestDispatcher("viewMemos.jsp").forward(request, response);
	}
}
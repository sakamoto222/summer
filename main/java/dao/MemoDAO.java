package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Memo;

public class MemoDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/memo";
	private final String USER = "postgres";
	private final String PASS = "admin"; 
	
	public void addmemo( Date date, String content,String username) {
		String sql = "INSERT INTO memos(date,content,username)"
				+ "VALUES (?, ?, ?)";
		
		try(Connection conn = DriverManager.getConnection(URL, USER, PASS))
			{
			try(PreparedStatement pStmt = conn.prepareStatement(sql)){
				pStmt.setDate(1, date);
				pStmt.setString(2, content);
				pStmt.setString(3, username);
				// SQLの実行
				pStmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("データをセットできませんでした");
				System.out.println(e);
				conn.rollback();
				System.out.println("ロールバック処理を行いました");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}

	}
	
	public List<Memo> findMemo(String username) {
		List<Memo> memoList = new ArrayList<>();
		try(Connection con = DriverManager.getConnection(URL,USER,PASS))
		{
			//SQL文準備
			String sql = "SELECT * FROM memos WHERE username = ? ORDER BY Date ASC;";
			PreparedStatement pStmt = con.prepareStatement(sql);
			
			pStmt.setString(1, username);
			
			//SELECT実行
			ResultSet rs = pStmt.executeQuery();
			
			
			//SELECT結果をリストに格納
			while (rs.next()) {
				int memo_id = rs.getInt("memo_id");
				Date date = rs.getDate("date");
				String content = rs.getString("content");
//				Memoクラスをインスタンス化してリストに格納
				Memo memo = new Memo(memo_id,date, content);
				memoList.add(memo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return memoList;
	}
	
	public void deletememo(int memoid){
		
	}
	
}

package model;

import java.io.Serializable;
import java.sql.Date;

public class Memo implements Serializable{
	private int memo_id;
	private Date date;
	private String content;
	
	public Memo(int memo_id, Date date, String content) {
		this.memo_id = memo_id;
		this.date = date;
		this.content = content;
	}
	
	public int getMemoId(){
		return memo_id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getContent() {
		return content;
	}
	
}

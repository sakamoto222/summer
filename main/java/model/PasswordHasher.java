package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHasher {
//	パスワードをハッシュ化するクラス
	public static String hashPassword(String password) {
//		passは入力されたパスワード
		MessageDigest md;
		byte[] digest = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			digest = md.digest();

		} catch (NoSuchAlgorithmException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
//		バイト列を文字列に変換
		return Base64.getEncoder().encodeToString(digest);
	}
}

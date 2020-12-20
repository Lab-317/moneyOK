package moneyOK.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public MD5() {

	}

	public static String encrypt(String input) {
		String encryptResult = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] digest = md.digest();
			
			final StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < digest.length; ++i) {     // Âà16¶i¨î;
				final byte b = digest[i];
				final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
				buffer.append(value < 16 ? "0" : "");
				buffer.append(Integer.toHexString(value));
			}
			// System.out.println("sessionid " + test + " md5 version is " +
			// buffer.toString());
			encryptResult = buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptResult;
	}
}

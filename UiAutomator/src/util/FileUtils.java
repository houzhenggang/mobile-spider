package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;





public class FileUtils {

	
	public final static String CHARCODE = "UTF-8";
	
	public static void loadFile(String path, Map<String, byte[]> keymp) {
		
		InputStream is = FileUtils.class.getResourceAsStream(path);
		if (is == null) {
			System.out.println("path " + path + " is not found");
			return;
		}
		
		
		
		try {
			BufferedReader br = new BufferedReader(new UnicodeReader(is, CHARCODE), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					if (!keymp.containsKey(theWord)) {
						keymp.put(theWord, new byte[0]);
					}
				}
			} while (theWord != null);

		} catch (IOException ioe) {
			ioe.printStackTrace();

		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public   void getPublicNumber(){
		Map<String, byte[]> keymp  =new LinkedHashMap<String, byte[]>();
		FileUtils.loadFile("/publicnumber.txt", keymp);
		System.out.println(keymp);
		
	}
}

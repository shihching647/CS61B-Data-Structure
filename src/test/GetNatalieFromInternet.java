package test;

import java.io.*;
import java.net.*;  //使用網路

public class GetNatalieFromInternet {

	public static void main(String[] args) {
		File dir = new File("C:\\Pictures");
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		for (int i = 1; i <= 42; i++) {
			String url = "http://www.space-fox.com/wallpapers/celebs/natalie-portman" + 
					"/natalie_portman_" + i	+ ".jpg";
			String filename = url.substring(url.lastIndexOf("/") + 1);
			File file = new File(dir, filename);
			try {
				URL myURL = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);

				System.out.println("Wallpaper: " + filename + " kick-off!");

				//自訂緩衝
				int length = 0;
				byte[] b = new byte[4096];  //緩衝區大小:4K
				while ((length = is.read(b)) != -1) { //read(byte[] b)方法回傳為b的長度
					// b: 代表要輸出的byte陣列 (資料都放在裡面了)
					// 0: 代表從這個陣列的第一個元素開始輸出 (索引值)
					// length: 代表要輸出的資料量
					fos.write(b, 0, length);
					fos.flush();  //強制把緩衝區的資料強制送出
				}
				
				fos.close();
				is.close();
				System.out.println(filename + " Done!");

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

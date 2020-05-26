package test;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;

public class DownloadFile {
	
	private static ArrayList<String> list = new ArrayList<String>();
	private static final String PATH = "https://people.eecs.berkeley.edu";
	private static final String STORE_PATH = "/Users/ariel647/desktop/123";
	
	public static void main(String[] args) throws Exception{

		for(int i = 1; i < 16; i++) {
			String url = "https://people.eecs.berkeley.edu/~jrs/61b/lab/lab" + i;
			read(url);
			String dir = "/Users/ariel647/desktop/123/lab" + i;
			downloadDir(url, dir, i);
		}
		
	}
	
	//讀取網頁內容藉此取得連結
	private static void read(String url) throws Exception{

		URL myURL = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection)myURL.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		
		while(true) {
			String line = rd.readLine();
			if(line == null) break;
			int indexA = line.indexOf("href=");
			int indexAEnd = line.indexOf(">", indexA);
			if(indexA != -1 && indexAEnd != -1) {
				System.out.println(line.substring(indexA + 6, indexAEnd - 1));
				list.add(line.substring(indexA + 6, indexAEnd - 1)); //從index = 2開始下載
			}
		}
	}
	
	private static void read(String url, ArrayList<String> subList) throws Exception{

		URL myURL = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection)myURL.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		
		while(true) {
			String line = rd.readLine();
			if(line == null) break;
			int indexA = line.indexOf("href=");
			int indexAEnd = line.indexOf(">", indexA);
			if(indexA != -1 && indexAEnd != -1) {
				System.out.println(line.substring(indexA + 6, indexAEnd - 1));
				subList.add(line.substring(indexA + 6, indexAEnd - 1)); //從index = 2開始下載
			}
		}
	}
	
	private static void downloadDir(String url, String dir, int i) throws Exception{
		File fileDir = new File(dir);
		if(!fileDir.exists()) {
			fileDir.mkdir();
		}
		for(int j = 2; j < list.size(); j++) {
			
			if(list.get(j).endsWith("/")) {
				System.out.println("=======SubDir Start======");
				ArrayList<String> subList = new ArrayList<String>();
				String subURL = url + "/" + list.get(j);
				read(subURL.substring(0, subURL.length() -1), subList);
				
				String subDir = dir + "/" + list.get(j);
				File fileSubDir = new File(subDir.substring(0, subDir.length() - 1));
				if(!fileSubDir.exists()) {
					fileSubDir.mkdir();
				}
				
				for(int k = 2; k < subList.size(); k++) {
					String subDownload = subURL + "/" + subList.get(k);
					URL mySubDownload = new URL(subDownload);
					File subFile = new File(subDir, subList.get(k));
					HttpsURLConnection conn = (HttpsURLConnection)mySubDownload.openConnection();
					InputStream input = conn.getInputStream();
					FileOutputStream fos = new FileOutputStream(subFile);
					byte[] b = new byte[4096];  //緩衝區大小:4K
					while (true) { 
						int length = input.read(b);
						if(length == -1) break;
						fos.write(b, 0, length);
						fos.flush();  //強制把緩衝區的資料強制送出
					}
					input.close();
					fos.close();
				}
				subList.clear();
				
			}else {
				String download = url + "/" + list.get(j);
				URL myDownload = new URL(download);
				File file = new File(dir, list.get(j));
				HttpsURLConnection conn = (HttpsURLConnection)myDownload.openConnection();
				InputStream input = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[4096];  //緩衝區大小:4K
				while (true) { 
					int length = input.read(b);
					if(length == -1) break;
					fos.write(b, 0, length);
					fos.flush();  //強制把緩衝區的資料強制送出
				}
				input.close();
				fos.close();
			}
		}
		list.clear();
	}
}

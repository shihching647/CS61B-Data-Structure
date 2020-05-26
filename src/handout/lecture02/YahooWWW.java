package handout.lecture02;

import java.io.*;
import java.net.*;

public class YahooWWW {

    public static void main(String[] args) throws IOException {

        URL url = new URL("https://www.yahoo.com.tw");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-agent", "Mozilla/5.0 (Linux; Android 4.2.1; Nexus 7 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19");
        InputStream ins = con.getInputStream();
        BufferedReader yahoo = new BufferedReader(new InputStreamReader(ins));

        while(true){
            String line = yahoo.readLine();
            if(line == null) break;
            System.out.println(line);
        }
    }
}

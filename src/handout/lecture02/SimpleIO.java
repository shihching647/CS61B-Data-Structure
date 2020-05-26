package handout.lecture02;

import java.io.*;

public class SimpleIO {

    public static void main (String[] args) throws IOException{

        BufferedReader keybd = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(keybd.readLine());
    }
}

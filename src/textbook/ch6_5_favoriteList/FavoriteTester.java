package textbook.ch6_5_favoriteList;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class FavoriteTester {
    public static void main(String[] args) {
        String[] urlArray = {"https://www.yahoo.com.tw", "https://www.google.com", "https://www.tsmc.com.tw", "https://www.ntu.edu.tw", "https://web.ncku.edu.tw"};
//        String[] urlArray = {"https://www.yahoo.com.tw"};
        FavoriteList<String> fList = new FavoriteList<>();
        FavoriteListMTF<String> fListMTF = new FavoriteListMTF<>();
        int times = 50; //access times
        //Simulation
        Random rand = new Random();
        for(int i = 0; i < times; i++) {
            System.out.println("------------------------------------------------------------------------");
            int index = rand.nextInt(urlArray.length);
            String url = urlArray[index];
            System.out.println("Accessing: " + url);
            fList.access(url);
            System.out.println("fList =  " + fList);
            fListMTF.access(url);
            System.out.println("fListMTF =  " + fListMTF);
        }
        int topAccess = urlArray.length / 2;
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Top " + topAccess + " in fList = " + fList.top(topAccess));
        System.out.println("Top " + topAccess + " in fList = " + fListMTF.top(topAccess));

        try{
            String popular = fList.top(1).iterator().next();
            JEditorPane jep = new JEditorPane(popular);
            jep.setEditable(false);
            JFrame frame = new JFrame(popular);
            frame.getContentPane().add(new JScrollPane(jep), BorderLayout.CENTER);
            frame.setSize(640, 480);
            frame.setVisible(true);

        } catch(IOException ex) {

        }
    }
}

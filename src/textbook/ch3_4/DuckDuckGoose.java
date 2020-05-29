package textbook.ch3_4;

import textbook.ch3_2.Node;

import java.util.Random;

public class DuckDuckGoose {
    public static void main(String[] args) {
        CircleList cList = new CircleList();
        int N = 5;
        Node it;
        Node goose;
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        //建立玩家
        String[] players = {"Bob", "Jen", "Pam", "Tom", "Ron", "Vic", "Sue", "Joe"};
        for(int i = 0; i < players.length; i++) {
            cList.add(new Node(players[i], null));
            cList.advance();
        }

        //Play Duck, Duck, Goose N times
        for(int i = 0; i < N; i++) {
            System.out.println("Play Duck, Duck, Goose for " + cList);
            it = cList.remove();
            System.out.println(it.getElement() + " is it.");
            while(rand.nextBoolean() || rand.nextBoolean()) { // 3/4機率為Duck
                cList.advance();
                System.out.println(cList.getCursor().getElement() + " is duck.");
            }
            goose = cList.remove();
            System.out.println(goose.getElement() + " is goose.");

            //各1/2機率贏
            if(rand.nextBoolean()) {
                //goose win
                System.out.println("The goose won!");
                cList.add(goose); //把goose放回原位
                cList.advance();  //cursor往前移動(因為下一回合的it是cursour的下一個)
                cList.add(it);    //下一個it
            }else{
                //it win
                System.out.println("The it won!");
                cList.add(it);
                cList.advance();
                cList.add(goose);
            }

        }
    }
}

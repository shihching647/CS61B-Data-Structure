package handout.lecture06;

public class ContinueExample {

    public static void main(String[] args){

        //會卡在i=5時,無窮迴圈
        int i = 0;
        while(i < 10){
            if(i == 5){
                continue;
            }
            System.out.println(i);
            i++;
        }
        //i=5時不會印出
        for(int j = 0; j < 10; j++){
            if(j == 5){
                continue;
            }
            System.out.println(j);
        }
    }
}

package handout.lecture04;

public class FindMax {

    public static void main(String[] arg){

        int x = 1, y = 2, z = 3;
        int max = 0;

        if(x > y){
            if(x > z){
                max = x;
            }else{
                max = z;
            }
        }else if(y > z){
            max = y;
        }else{
            max = z;
        }

        System.out.println(max);
    }
}

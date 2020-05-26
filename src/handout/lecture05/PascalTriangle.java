package handout.lecture05;

public class PascalTriangle {

    public static void main(String[] args){
        int n = 5;
        int[][] pt = getPascalTriangle(n);
        for(int i = 0; i < pt.length; i++){
            for(int j = 0; j < pt[i].length; j++){
                System.out.print(pt[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[][] getPascalTriangle(int n){
        int[][] pt = new int [n][];
        for(int i = 0; i < n; i++){
            pt[i] = new int[i+1];   //第i行的元素個數是i+1
            pt[i][0] = 1; //每一行最左邊的元素是1
            pt[i][i] = 1; //每一行最右邊的元素是1
            //中間的元素是上一層的前一個+後一個
            for(int j = 1; j < i; j++){
                pt[i][j] = pt[i-1][j-1] + pt[i-1][j];
            }
       }
       return pt;
    }
}

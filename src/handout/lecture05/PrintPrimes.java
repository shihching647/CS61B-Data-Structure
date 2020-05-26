package handout.lecture05;

public class PrintPrimes {

    public static void main(String[] args){

        printPrims(100);
        System.out.println();
        PrintPrimes_Eratosthenes(100);
    }

    private static void printPrims(int n) {
        for(int i = 2; i <= n; i++){
            if(isPrime2(i)){
                System.out.print(" " + i);
            }
        }
    }

    //while迴圈找質數
    private static boolean isPrime(int x){
        int divisor = 2;
        while(divisor < x){
            if(x % divisor == 0){
                return false;
            }
            divisor++;
        }
        return true;
    }

    //for迴圈找質數
    private static boolean isPrime2(int x){
        for(int divisor = 2; divisor < x; divisor++){
            if(x % divisor == 0){
                return false;
            }
        }
        return true;
    }

    //Sieve of Eratosthenes
    private static void PrintPrimes_Eratosthenes(int x) {
        //先列出範圍內的全部正整數,全部標記為true
        boolean[] prime = new boolean[x+1];
        for(int i = 0 ;i < prime.length; i++){
            prime[i] = true;
        }

        //找到一個質數,把她在範圍內的倍數全部標記為false
        for(int divisor = 2; divisor * divisor <= x; divisor++){ //因為一個合數x ，必定有一個小於等於 sqrt(x) 的質因數。
            if(prime[divisor]){
                for(int i = 2 * divisor; i <= x; i = i + divisor){
                    prime[i] = false;
                }
            }
        }
        //印出標記為true的質數
        for(int i = 2; i < prime.length; i++){
            if(prime[i]){
                System.out.print(" " + i);
            }
        }
    }
}

package textbook.fifth_edition.ch3_1;

public class InsertionSort {
    public static void main(String[] args){
        String str = "cadebf";
        char[] array = str.toCharArray();
        System.out.println(array);
        insertionSort(array);
        System.out.println(array);
    }

    public static void insertionSort(char[] a){
        for(int i = 1; i < a.length; i++){
            char cur = a[i];
            int j = i - 1;
            while(j >= 0 && a[j] > cur){
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = cur;
        }
    }
}

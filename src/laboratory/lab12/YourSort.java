package laboratory.lab12;/* YourSort.java */

public class YourSort {

  public static void sort(int[] A) {
    // Place your Part III code here.
    int n = A.length;
    if (n <= 60) {
      Sort.insertionSort(A);
    } else {
      Sort.quicksort(A);
    }
  }
}

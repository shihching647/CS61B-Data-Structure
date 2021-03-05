package textbook.sixth_edition.ch12_sorting_and_selection;

import java.util.Comparator;

public class MyCompatator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 > o2) return 1;
        else if (o1 < o2) return -1;
        else return 0;
    }

}

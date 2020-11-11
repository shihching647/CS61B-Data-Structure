package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

public class CostPerformanceDatabase {

    private SortedMap<Integer,Integer> map = new SortedTableMap<>();

    public CostPerformanceDatabase() {}

    /** Returns the (cost,performance) entry with largest cost not exceeding c.
     * (or null if no entry exist with cost c or less).
     */
    public Entry<Integer,Integer> best(int cost) {
        return map.floorEntry(cost);
    }

    /** Add a new entry with given cost c and performance p. */
    public void add(int cost, int performance) {
        Entry<Integer,Integer> other = map.floorEntry(cost);
        if(other != null && other.getValue() >= performance) {
            return;     // (c,p) is dominated, so ignore
        }
        // else, add (c,p) to database
        map.put(cost, performance);
        // and now remove any entries that are dominated by the new one
        other = map.higherEntry(cost);
        while(other != null && other.getValue() <= performance) {
            map.remove(other.getKey());
            other = map.higherEntry(cost);
        }
    }

    public String toString() {
        return map.toString();
    }

    public static void main(String[] args) {
        CostPerformanceDatabase database = new CostPerformanceDatabase();
        database.add(2000, 40);
        database.add(2500, 50);
        database.add(5000, 120);
        database.add(6000, 140);
        // the pairs will be dominated
        database.add(3500, 70);
        database.add(4000, 75);
        database.add(4500, 95);
        //dominate one
        database.add(3000, 100);
        System.out.println(database);
    }
}

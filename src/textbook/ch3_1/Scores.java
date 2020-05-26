package textbook.ch3_1;

public class Scores {
    public static final int maxEntries = 10;
    protected int numEntries;
    protected GameEntry[] entries;

    public Scores(){
        entries = new GameEntry[maxEntries];
        numEntries = 0;
    }

    public String toString(){
        String s = "[";
        for(int i = 0; i < numEntries; i++){
            if(i > 0) s += ", ";
            s += entries[i];
        }
        return s + "]";
    }

    public void insert(GameEntry e){
        int score = e.getScore();
        if(numEntries == maxEntries){
            if(score < entries[numEntries].getScore()){
                return;
            }
        }else{
            numEntries += 1;
        }

        //Locate the place that the new (high score) entry belongs
        int i = numEntries - 1;

        //寫法1
//        for(; i >= 1 && score > entries[i-1].getScore() ; i--){
//            entries[i] = entries[i-1]; //move entry i one to right
//        }

        //寫法2
        while(i >= 1){
            if(score > entries[i-1].getScore()){
                entries[i] = entries[i-1];
                i--;
            }else{
                break;
            }
        }
        entries[i] = e;
    }

    public GameEntry remove(int i) throws ArrayIndexOutOfBoundsException{
        if(i > numEntries - 1 || i < 0){
            throw new ArrayIndexOutOfBoundsException();
        }
        GameEntry tmp = entries[i];
        for(int j = i; j < numEntries - 1; j++){
            entries[j] = entries[j + 1];
        }
        entries[numEntries - 1] = null; //null out the old last score
        numEntries--;
        return tmp;
    }

    public static void main(String[] args){
        Scores scores = new Scores();
        for(int i = 0; i < 5; i++){
            GameEntry e = new GameEntry("player" + (i+1), (i+1) * 10);
            scores.insert(e);
        }
        System.out.println(scores);
        scores.insert(new GameEntry("Jim", 100));
        System.out.println(scores);
        scores.remove(1);
        System.out.println(scores);
    }
}

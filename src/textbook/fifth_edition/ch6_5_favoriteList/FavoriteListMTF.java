package textbook.fifth_edition.ch6_5_favoriteList;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.*;

public class FavoriteListMTF<E> extends FavoriteList<E>{
    public FavoriteListMTF() {}

    @Override
    protected void moveUp(Position<Entry<E>> pos) {
        fList.addFirst(fList.remove(pos));
    }

    @Override
    public Iterable<E> top(int k) {
        if(k <= 0 || k > size()) {
            throw new IllegalArgumentException("Invalid Argument!");
        }
        PositionList<E> finalList = new NodePositionList<>();

        if(!isEmpty()) {
            //1. Copy entire fList to copyList
            PositionList<Entry<E>> copyList = new NodePositionList<>();
            for(Entry<E> e : fList) {
                copyList.addLast(e);
            }
            //2. scan copyList k times, remove the highest count entry in copyList and add to finalList
            for(int i = 0; i < k; i++) {
                //需紀錄maxPos(移除copyList用), maxEntry(加入finalList用)
                Position<Entry<E>> maxPos = copyList.first();
                Entry<E> maxEntry = maxPos.element();
                for(Position<Entry<E>> p : copyList.positions()) {
                    if(count(maxPos) < count(p)){
                        maxPos = p;
                        maxEntry = p.element();
                    }
                }
                copyList.remove(maxPos);
                finalList.addLast(maxEntry.value());
            }
        }
        return finalList;
    }
}

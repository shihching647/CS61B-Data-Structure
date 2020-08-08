package textbook.ch6_1;

public class ArrayIndexList<E> implements IndexList<E>{
    private E[] list;
    private int capacity = 16;  //initial capacity
    private int size = 0;

    public ArrayIndexList(){
        list = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size());
        return list[i];
    }

    @Override
    public void add(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size() + 1);
        //Array is full, need to be extended
        if(size() >= capacity / 4) {
            //1. allocate new array
            capacity = capacity * 2;
            E[] newList = (E[]) new Object[capacity];
            //2. copy all elements in the old array to the new one
            for(int j = 0; j < size; j++) {
                newList[j] = list[j];
            }
            //3. let list <- newList
            list = newList;
        }
        //4. Insert the new element
        for(int j = size - 1; j >= i; j--) {
            list[j + 1] = list[j];
        }
        list[i] = e;
        size++;
        System.out.print("capacity = " + capacity);
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size());
        //Exercise C-6.2, at ant time, size < capacity / 4 is always true.
        if(size() <= capacity / 8) {
            //1. allocate new array
            capacity = capacity / 2;
            E[] newList = (E[]) new Object[capacity];
            //2. copy all elements in the old array to the new one
            for(int j = 0; j < size; j++) {
                newList[j] = list[j];
            }
            //3. let list <- newList
            list = newList;
        }
        E temp = list[i];
        for(int j = i + 1; j < size; j++) {
            list[j - 1] = list[j];
        }
        size--;
        System.out.print("capacity = " + capacity);
        return temp;
    }

    @Override
    public E set(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size());
        E temp = list[i];
        list[i] = e;
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if(size > 0) sb.append(list[0]);
        if(size > 1) {
            for(int i = 1; i < size; i++) {
                sb.append(", " + list[i]);
            }
        }
        return sb.append("]").toString();
    }

    /*Check if index i is in range[0, n - 1]*/
    private void checkIndex(int i, int n) throws IndexOutOfBoundsException{
        if(i < 0 || i >= n)
            throw new IndexOutOfBoundsException("Index out of bound!");
    }

    public static void main(String[] args) {
        ArrayIndexList<Integer> list = new ArrayIndexList<>();
//        list.add(0, 0);
//        System.out.println(list);
        for(int i = 0; i < 20; i++) {
            list.add(i, i);
            System.out.println("\tsize = " + list.size() + "\t" + list);
        }
        for(int i = 0; i < 20; i++) {
            list.set(i, i * 10);
            System.out.println("\tsize = " + list.size() + "\t" + list);
        }
        System.out.println(list.get(19));
        for(int i = 19; i >= 0; i--) {
            System.out.println(" remove element : " + list.remove(i));
            System.out.println("\tsize = " + list.size() + "\t" + list);
        }
    }
}

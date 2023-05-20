import java.util.Arrays;

public class EndlessArray<T>  {
    private T[] arrStorage;
    private int size;
    public EndlessArray() {
        this(50);
    }
    public EndlessArray(int capacity) {
        arrStorage = (T[]) new Object[capacity];
        size = 0;
    }
    public EndlessArray(T[] arr){
        arrStorage = (T[]) new Object[arr.length * 2];
        size = arr.length;
        System.arraycopy(arr, 0, arrStorage, 0, arr.length);
        //  arr = arrStorage;
    }
    private boolean ensureCapacity() {
        if(size == arrStorage.length) {
            int newCapacity = arrStorage.length * 2;
            T[] newArr = (T[]) new Object[newCapacity];
            System.arraycopy(arrStorage, 0, newArr, 0, arrStorage.length);
            arrStorage = newArr;
            return true;
        }
        return true;
    }
    public void addLast(T e){
        if (ensureCapacity()){
            arrStorage[size] = e;
            size++;
        }
    }
    public void addFirst(T e){
        if (ensureCapacity()){
            for (int i = size - 1; i >= 0 ; i--) {
                arrStorage[i+1] = arrStorage[i];
            }
            arrStorage[0] = e;
            size++;
        }
    }
    public void addAt(T e, int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
        if (ensureCapacity()){
            for (int i = size - 1; i >= index ; i--) {
                arrStorage[i+1] = arrStorage[i];
            }
            arrStorage[index] = e;
            size++;
        }
    }
    public void removeLast() {
        if (size == 0) {
            throw new RuntimeException("EndlessArray is already empty");
        }
        T[] newArr = (T[]) new Object[arrStorage.length-1];
        System.arraycopy(arrStorage, 0, newArr, 0, size-1);
        arrStorage = newArr;
        size--;
    }
    public void removeFirst() {
        if (size == 0) {
            throw new RuntimeException("EndlessArray is already empty");
        }
        T[] newArr = (T[]) new Object[arrStorage.length-1];
        System.arraycopy(arrStorage, 1, newArr, 0, size-1);
        arrStorage = newArr;
        size--;
    }
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        for (int i = index; i < size - 1; i++) {
            arrStorage[i] = arrStorage[i + 1];
        }
        size--;
        T[] newArr = (T[]) new Object[arrStorage.length];
        System.arraycopy(arrStorage, 0, newArr, 0, size);
        arrStorage = newArr;
    }
    public boolean contains(T e) {
        for (int i = 0; i < size; i++) {
            if (arrStorage[i].equals(e)) {
                return true;
            }
        }
        return false;
    }
    public int indexOf(T e) {
        for (int i = 0; i < size; i++) {
            if (arrStorage[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }
    public void remove(T e){
        if(indexOf(e) != -1){
            removeAt(indexOf(e));
        }
    }

    public T getAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
        return arrStorage[index];
    }
    public void clear() {
        arrStorage = (T[]) new Object[arrStorage.length];
        size = 0;
    }
    public T[] toArray() {
        T[] newArray = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = arrStorage[i];
        }
        return newArray;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
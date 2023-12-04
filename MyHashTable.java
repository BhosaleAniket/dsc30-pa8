/*
 * Name: Aniket Bhosale
 * PID: A16884343
 */

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Hash Table Interface Implementation
 * 
 * @author Aniket Bhosale
 * @since 12/03/2023
 */

public class MyHashTable implements KeyedSet {

    /* instance variables */
    private int size; // number of elements stored
    private LinkedList<String>[] table; // data table
    private static final int DEFAULT_CAPACITY = 20;
    private static final int MIN_CAPACITY = 5;
    private int capacity = 0;
    private String statsLog;

    private int hashCount;
    private int collisionCount;

    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        if(capacity < MIN_CAPACITY){
            throw new IllegalArgumentException();
        }
        this.table = new LinkedList[capacity];
        for(int i=0; i < capacity; i++){
            table[i] = new LinkedList<>();
        }
        this.capacity = capacity;
        this.hashCount = 0;
        this.collisionCount = 0;
        this.statsLog = "";
    }

    public boolean insert(String value) {
        if(value == null){
            throw new NullPointerException();
        }

        if(this.lookup(value)){
            return false;
        }

        else{
            LinkedList<String> bucketList = this.table[this.hashString(value)];
            if(!bucketList.isEmpty()){
                this.collisionCount++;
            }
            bucketList.add(value);
            this.size++;
            if(this.loadFactor() > 1){
               this.rehash();
            }


            return true;
        }

    }

    public boolean delete(String value) {
        if(value == null){
            throw new NullPointerException();
        }

        if(this.lookup(value) == false){
            return false;
        }

        else{
            LinkedList<String> bucketList = this.table[this.hashString(value)];
            bucketList.remove(value);

            this.size--;
            return true;
        }

    }

    public boolean lookup(String value) {
        if(value == null){
            throw new NullPointerException();
        }

        if(this.size == 0){
            return false;
        }

        LinkedList<String> bucketList = this.table[hashString(value)];
        boolean itemNode = bucketList.contains(value);
        if (itemNode) {
            return true;
        }
        else {
            return false;
        }
    }

    public String[] returnAll() {
        String[] output = new String[this.size];
        int outputCounter = 0;
        for(int i = 0; i <this.capacity; i++){
            if(!this.table[i].isEmpty()){
                Iterator<String> iter = this.table[i].iterator();
                while (iter.hasNext()){
                    String element = iter.next();
                    output[outputCounter] = element;
                    outputCounter++;
                }
            }
        }

        return output;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

    public String getStatsLog() {
        return this.statsLog;
    }

    /**
     * Utility function provided to help with debugging
     */
    public void printTable() {
        String s = "";
        for (int i = 0; i < table.length; i++) {
            s = s + i + ":";
            if (!table[i].isEmpty()) {
                for (String t : table[i])
                    s = s + " " + t + ",";
                // remove trailing comma
                s = s.substring(0, s.length() - 1);
            }
            s = s + "\n";
        }
        // remove trailing newline
        s = s.substring(0, s.length() - 1);
        System.out.println(s);
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        this.recordStatsLog();
        int oldCap = this.capacity;
        this.capacity = this.capacity * 2;
        LinkedList<String>[] temp = new LinkedList[this.capacity];
        for(int k=0; k< this.capacity; k++){
            temp[k] = new LinkedList<>();
        }
        for(int i = 0; i <oldCap; i++){
            if(!this.table[i].isEmpty()){
                Iterator<String> iter = this.table[i].iterator();
                while (iter.hasNext()){
                    String element = iter.next();
                    if(temp[hashString(element)].isEmpty() == false){
                        this.collisionCount++;
                    }
                    temp[hashString(element)].add(element);
                }
            }
        }

        this.table = temp;
    }

    private int hashString(String value) {
        int hashValue = 0;
        for(int i=0; i < value.length(); i++){
            int leftShiftedValue = hashValue << 5;
            int rightShiftedValue = hashValue >>> 27;

            hashValue = Math.abs((leftShiftedValue | rightShiftedValue) ^ value.charAt(i));
        }
        return hashValue % this.capacity;
    }

    private float loadFactor(){
        return (float) this.size / this.capacity;
    }

    private void recordStatsLog(){
        float loadF = this.loadFactor();
        String load = String.format("%.2f", loadF);
        String output = "Before rehash # " + (this.hashCount+1) +": load factor "+ load;
        output = output + ", " + this.collisionCount + " collision(s).\n";
        this.collisionCount = 0;
        this.hashCount++;
        this.statsLog = this.statsLog + output;
    }
}

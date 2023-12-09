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

    private static final int EXPANDER = 2; // used to rehash table

    private static final int HASHFUNC_LEFT_SHIFT = 5; //const for hash function
    private static final int HASHFUNC_RIGHT_SHIFT = 27; //const for hash func
    private int capacity = 0;
    private String statsLog; // String to hold Stats

    private int hashCount; // dataholder for statsLog
    private int collisionCount; // dataholder for statsLog

    /**
     * Default Constructor for MyHashTable
     */
    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor for MyHashTable
     * @param capacity of HashTable
     * @throws IllegalArgumentException if capacity < 5
     */
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

    /**
     * Inserts a value into MyHashTable
     * @param value String to insert
     * @throws NullPointerException if value is null
     * @return true if the value was inserted, else false
     */
    public boolean insert(String value) {
        if(value == null){
            throw new NullPointerException();
        }

        // checks for duplicates
        if(this.lookup(value)){
            return false;
        }

        // adds elements
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

    /**
     * Deletes a value from MyHashTable
     * @param value to delete
     * @throws NullPointerException if value is null
     * @return true if the value was removed else false
     */
    public boolean delete(String value) {
        if(value == null){
            throw new NullPointerException();
        }

        // if value not in the HashTable
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

    /**
     * Looks up if a value is in MyHashTable
     * @param value to look up
     * @throws NullPointerException if value is null
     * @return true if the value was found, false if the value was not found
     */
    public boolean lookup(String value) {
        if(value == null){
            throw new NullPointerException();
        }

        if(this.size == 0){
            return false;
        }

        // check the elements in the bucket to find the value
        LinkedList<String> bucketList = this.table[hashString(value)];
        boolean itemNode = bucketList.contains(value);
        if (itemNode) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * ReturnsAll elements in MyHashTable
     * @return a string array with all the elements in the HashTable
     */
    public String[] returnAll() {
        String[] output = new String[this.size];
        int outputCounter = 0;
        for(int i = 0; i <this.capacity; i++){
            if(!this.table[i].isEmpty()){
                Iterator<String> iter = this.table[i].iterator();

                // iterate through the LinkedList
                while (iter.hasNext()){
                    String element = iter.next();
                    output[outputCounter] = element;
                    outputCounter++;
                }
            }
        }

        return output;
    }

    /**
     * Finds the number of items in MyHashTable
     * @return int number of elements added to the HashTable
     */
    public int size() {
        return this.size;
    }

    /**
     * Finds the capacity of MyHashTable
     * @return int number representing the capacity of the HashTable
     */
    public int capacity() {
        return this.capacity;
    }

    /**
     * Getter method for StatsLog
     * @return String representing StatsLog
     */
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

    /**
     * Rehashes MyHashTable
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        this.recordStatsLog(); // records stats before rehash
        int oldCap = this.capacity;
        this.capacity = this.capacity * EXPANDER;
        LinkedList<String>[] temp = new LinkedList[this.capacity];
        // Creates new table of twice the original size
        for(int k=0; k< this.capacity; k++){
            temp[k] = new LinkedList<>();
        }

        //adds all the old bucktes to the next table and hashs each element
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

    /**
     * Hash Functions for MyHashTable
     * @param value value to hash
     * @return int key in HashTable
     */
    private int hashString(String value) {
        int hashValue = 0;
        for(int i=0; i < value.length(); i++){
            int leftShiftedValue = hashValue << HASHFUNC_LEFT_SHIFT;
            int rightShiftedValue = hashValue >>> HASHFUNC_RIGHT_SHIFT;

            hashValue = Math.abs((leftShiftedValue | rightShiftedValue) ^ value.charAt(i));
        }
        return hashValue % this.capacity;
    }

    /**
     * Determines the LoadFactor for MyHashTable
     * @return float load factor of the table
     */
    private float loadFactor(){
        return (float) this.size / this.capacity;
    }

    /**
     * Helper function for getStatsLog
     */
    private void recordStatsLog(){
        float loadF = this.loadFactor();
        String load = String.format("%.2f", loadF);
        String output = "Before rehash # " + (this.hashCount+1) +": load factor "+ load;
        output = output + ", " + this.collisionCount + " collision(s).\n";
        // reset collisionCount and update HashCount
        this.collisionCount = 0;
        this.hashCount++;
        this.statsLog = this.statsLog + output;
    }
}

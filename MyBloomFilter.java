/*
 * Name: Aniket Bhosale
 * PID: A16884343
 */

/**
 * Bloom Filter Implementation
 *
 * @author Aniket Bhosale
 * @since A16884343
 */

public class MyBloomFilter implements KeyedSet {

    private static final int DEFAULT_M = (int) 1e7;

    boolean[] bits;

    /**
     * Initialize MyBloomFilter with the default number of bits
     */
    public MyBloomFilter() {
        bits = new boolean[DEFAULT_M];
    }

    /**
     * Insert the string key into the bloom filter.
     *
     * @param key key to insert
     * @throws NullPointerException if value is null
     * @return true if the key was inserted, false if the key was already
     *         present
     */
    public boolean insert(String key) {
        if(key == null){
            throw new NullPointerException();
        }
        int index1 = this.hashFuncA(key);
        int index2 = this.hashFuncB(key);
        int index3 = this.hashFuncC(key);

        if(this.bits[index1] == false){
            this.bits[index1] = true;
        }

        if(this.bits[index2] == false){
            this.bits[index2] = true;
        }

        if(this.bits[index3] == false){
            this.bits[index3] = true;
        }

        return true;

    }

    /**
     * Check if the given key is present in the bloom filter.
     * @param key key to look up
     * @throws NullPointerException if value is null
     * @return true if the key was found, false if the key was not found
     */
    public boolean lookup(String key) {
        if(key == null){
            throw new NullPointerException();
        }
        int index1 = this.hashFuncA(key);
        int index2 = this.hashFuncB(key);
        int index3 = this.hashFuncC(key);

        if(this.bits[index1] && this.bits[index2] && this.bits[index3]){
            return true;
        }

        else{
            return false;
        }
    }

    /**
     * First hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncA(String value) {
        int hashValue = 0;
        for(int i=0; i < value.length(); i++){
            int leftShiftedValue = hashValue << 5;
            int rightShiftedValue = hashValue >>> 27;

            hashValue = Math.abs((leftShiftedValue | rightShiftedValue) ^ value.charAt(i));
        }
        return hashValue % this.bits.length;
    }

    /**
     * Second hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncB(String value) {
        int hashVal = 0;
        for(int j=0; j < value.length(); j++){
            int letter = value.charAt(j);
            hashVal = (hashVal*27 + letter) % this.bits.length;
        }
        return hashVal;
    }

    /**
     * Third hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncC(String value) {
        int hashVal = 0;
        for (int j = 0; j < value.length(); j++) {
            int letter = value.charAt(j);
            hashVal = ((hashVal << 8) + letter) % bits.length;
        }
        return Math.abs(hashVal % bits.length);
    }
}

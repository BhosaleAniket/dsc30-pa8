/*
 * Name: Aniket Bhosale
 * PID: A16884343
 */

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * SpellChecker Implementation
 *
 * @author Aniket Bhosale
 * @since 12/24/2023
 */

public class SpellChecker {

    public KeyedSet dictWords; //base data holder for the spellchecker
    private static final int LETTER_COUNT = 26; // alphabet count
    private static final int FILE_POS = 2; // position of file name in cmd input

    /**
     * Reads the data into the KeyedSet
     * @param reader Reader object for the text to build the checker from
     * @param useHashTable true if we want to use a MyHashTable to store the words
     */
    public void readDictionary(Reader reader, boolean useHashTable) {
        if(useHashTable){
             this.dictWords = new MyHashTable();
        }
        else{
            this.dictWords = new MyBloomFilter();
        }

        // parses words into the dataholder
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().trim();
            word =  word.toLowerCase();
            dictWords.insert(word);

        }
        scanner.close();
    }

    /**
     * Checks for incorrect letter
     * @param word to check
     * @return String linkedlist of sugesstions
     */
    private LinkedList<String> checkWrongLetter(String word) {
        LinkedList<String> output = new LinkedList<>();
        // iterate through all positions and letters and check if the word is in the set
        for(int i =0; i < word.length(); i++){
            for(int letter=0; letter < LETTER_COUNT; letter++){
                String modWord = word.substring(0,i) + (char)('a'+letter) +
                        word.substring(i+1);
                if(this.dictWords.lookup(modWord)){
                    if(!output.contains(modWord)){
                        output.add(modWord);
                    }
                }
            }
        }

        return output;
    }

    /**
     * Checks for Missing letter
     * @param word to check
     * @return String linkedlist of sugesstions
     */
    private LinkedList<String> checkInsertedLetter(String word) {

        // iterate through all positions and letters and check if the word is in the set
        LinkedList<String> output = new LinkedList<>();
        for(int i =0; i < word.length(); i++){
            for(int letter=0; letter < LETTER_COUNT; letter++){
                String modWord = word.substring(0,i) + (char)('a'+letter) +
                        word.substring(i);
                if(this.dictWords.lookup(modWord)){
                    if(!output.contains(modWord)){
                        output.add(modWord);
                    }
                }
            }

            // iterate through all positions and letters and check if the word is in the set (last)
            for(int letter=0; letter < LETTER_COUNT; letter++){
                String modWord = word + (char)('a'+letter);
                if(this.dictWords.lookup(modWord)){
                    if(!output.contains(modWord)){
                        output.add(modWord);
                    }
                }
            }
        }

        return output;
    }

    /**
     * Checks for Extra letter
     * @param word to check
     * @return String linkedlist of sugesstions
     */
    private LinkedList<String> checkDeleted(String word) {
        // iterate through all positions and letters and check if the word is in the set

        LinkedList<String> output = new LinkedList<>();
        for(int i =0; i < word.length(); i++){
            for(int letter=0; letter < LETTER_COUNT; letter++){
                String modWord = word.substring(0,i) + word.substring(i+1);
                if(this.dictWords.lookup(modWord)){
                    if(!output.contains(modWord)){
                        output.add(modWord);
                    }
                }
            }
        }

        return output;
    }

    /**
     * Checks for Swapped letter
     * @param word to check
     * @return String linkedlist of sugesstions
     */
    private LinkedList<String> checkTransposedLetter(String word) {
        // iterate through all positions and letters and check if the word is in the set

        LinkedList<String> output = new LinkedList<>();
        for(int i = 0; i < word.length()-1; i++){
            String modWord = word.substring(0,i) + word.charAt(i+1) + word.charAt(i)
                    + word.substring(i+2);
            if(this.dictWords.lookup(modWord)){
                if(!output.contains(modWord)){
                    output.add(modWord);
                }
            }
        }
        return output;
    }

    /**
     * Checks for Missing Space letter
     * @param word to check
     * @return String linkedlist of sugesstions
     */
    private LinkedList<String> checkInsertSpace(String word) {
        // iterate through all positions and letters and check if the word is in the set

        LinkedList<String> output = new LinkedList<>();
        for(int i =0; i < word.length(); i++){
                String modWord = word.substring(0,i) + ' ' + word.substring(i);
                String word1 = modWord.split(" ")[0];
                String word2 = modWord.split(" ")[1];
                if(this.dictWords.lookup(word1) && this.dictWords.lookup(word2)){
                    if(!output.contains(word1)){
                        output.add(modWord);
                    }

            }
        }

        return output;
    }

    /**
     * Calls all the check functions and generates results
     * @param word to check
     * @return String array of sugesstions
     */
    private String[] checkWord(String word) {
        LinkedList<String> l1 = this.checkWrongLetter(word);
        LinkedList<String> l2 = this.checkInsertedLetter(word);
        LinkedList<String> l3 = this.checkDeleted(word);
        LinkedList<String> l4 = this.checkTransposedLetter(word);
        LinkedList<String> l5 = this.checkInsertSpace(word);


        List<String> combinedList = new ArrayList<>();

        // Add all elements from the linked lists to the combinedList without duplicates
        addWithoutDuplicates(combinedList, l1);
        addWithoutDuplicates(combinedList, l2);
        addWithoutDuplicates(combinedList, l3);
        addWithoutDuplicates(combinedList, l4);
        addWithoutDuplicates(combinedList, l5);

        // Convert the list to an array
        return combinedList.toArray(new String[0]);
    }


    /**
     * Helper method to add elements to the list without duplicates
     * @param list to check
     * @param elements to add
     */
    private void addWithoutDuplicates(List<String> list, LinkedList<String> elements) {
        for (String element : elements) {
            if (!list.contains(element)) {
                list.add(element);
            }
        }
    }


    /**
     * Main method for SpellChecker
     * @param args to configure spellchecker and dict
     */
    public static void main(String[] args) {
        // args[0]: 0 if we should use a MyHashTable and 1 for a MyBloomFilter
        // args[1]: path to dict file
        // args[2]: path to input file

        SpellChecker checker = new SpellChecker();

        File dictionary = new File(args[1]);
        try {
            Reader reader = new FileReader(dictionary);
            if(args[0].equals("0")){
                checker.readDictionary(reader, true);

            }
            else {
                checker.readDictionary(reader, false);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + dictionary);
            System.exit(1);
        }

        File inputFile = new File(args[FILE_POS]);
        try {
            Scanner input = new Scanner(inputFile); // Reads list of words
            while (input.hasNextLine()) {
                String word = input.nextLine().trim();
                word =  word.toLowerCase();
                if(checker.dictWords.lookup(word)){
                    System.out.println(word+": ok");
                }
                else{
                    String[] corrections = checker.checkWord(word);
                    if(corrections == null || corrections.length == 0){
                        // if no corrections found
                        System.out.println(word+": not found");
                    }
                    else{
                        // prints out corrections
                        String output = "";
                        for(int i = 0; i < corrections.length-1; i++){
                            output = output + corrections[i] + ", ";
                        }
                        output = output + corrections[corrections.length-1];

                        System.out.println(word + ": " + output);
                    }
                }
            }
            input.close();

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + inputFile);
            System.exit(1);
        }
    }
}

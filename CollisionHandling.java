/**
 * PA8 Part 1 worksheet (style not required)
 */
public class CollisionHandling {

    /**
     * method that stores the result of Q1 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] linearProbingResult(){
        int[][] output = new int[2][8];
        output[0] = new int[]{0,24,5,42,0,28,0,0};
        output[1] = new int[]{11,24,5,42,77,28,84,98};
        return output;
    }

    /**
     * method that stores the result of Q2 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] quadraticProbingResult(){
        int[][] output = new int[2][16];
        output[0] = new int[]{101,0,0,16,0,26,11,0};
        output[1] = new int[]{201,0,0,16,34,2,0,0,101,0,0,0,9,26,11,0};
        return output;
    }

    /**
     * method that stores the result of Q3 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] doubleHashingResult(){
        int[][] output = new int[2][8];
        output[0] = new int[]{0,107,3,0,12,0,100,0};
        output[1] = new int[]{51,107,3,51,12,39,100,41};
        return output;
    }

}
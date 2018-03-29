
public class SPNCryptoSystem {
    /**
     * Generate the keys from the given key
     * @param key
     *      Given key
     * @param round
     *      Number of round
     * @return the keys 
     */
    public static int[] SPNKeyScheduler(int key, int round) {
        int[] keys = new int[round + 1];
        int mask = 0xffff;
        for(int i = round; i >= 0; i--) {
            keys[i] = mask & key;
            key >>= 4;
        }
        return keys;
    }
    
    /**
     * Substitution Operation (S Box)
     * @param input
     *      Input integer
     * @param pis
     *      The sbox
     * @return the corresponding integer of the input integer in the sbox
     */
    public static int substitution(int input, int[] pis) {
        int[] subs = new int[4];
        int mask = 0xf;
        int result = 0;
        // split into 4
        for(int i = input, k = 3; i > 0 && k >= 0; i >>= 4, k--) {
            subs[k] = mask & i;
            
        }
        
        // add up
        for (int i = 0; i < 4; i++) {
            result <<= 4;
            result += pis[subs[i]];
        }
        return result;
    }
    
    /**
     * Permutate the input
     * @param input
     *      Input int
     * @param pip
     *      Permutation function
     *       
     * @return The permutated of the input int
     */
    public static int permutation(int input, int[] pip) {
        
        int one = 1;
        int result = 0;
        for (int i = 15; i >= 0; i--) {
            //if 1
            if ((input & (one << i)) != 0) {
                result += (one << (16 - (pip[15 - i])));
                
            }
        }
        return result;
    }
    
    /**
     * Encrypt the message using SPN
     * @param plain
     *      plain text
     * @param round
     *      number of round
     * @param display
     *      boolean indicate whether to print out each step result
     * @param sbox
     *      sbox
     * @param perm
     *      permutation function
     * @param key
     *      key
     * @return cipher text
     */
    public static int SPNEncrypt(int plain, int round, boolean display, int[] sbox, int[] perm, int key) {
        int[] keys = SPNKeyScheduler(key, round);

        int result = plain;
        String cipher = toBinaryString(result, 16);
        if (display) {
            System.out.println("w1 = " + toBinaryString(result, 16));
        }
        // round - 1 times
        for (int i = 0; i < round - 1; i++) {
            result ^= keys[i];
            cipher = toBinaryString(result, 16);
            if (display) {
                System.out.println("k" + (i + 1) + " = " + toBinaryString(keys[i], 16));
                System.out.println("u" + (i + 1) + " = " + cipher);
            }
            result = substitution(result, sbox);
            if (display) {
                System.out.println("v" + (i + 1) + " = " + toBinaryString(result, 16));
            }
            result = permutation(result, perm);
            if (display) {
                System.out.println("w" + (i + 1) + " = " + toBinaryString(result, 16));
            }
        }
        // last round
        // k4 is at position 3 in array keys
        result ^= keys[round - 1];
        if (display) {
            System.out.println("k" + (round) + " = " + toBinaryString(keys[round - 1], 16));
            System.out.println("u" + (round) + " = " + toBinaryString(result, 16));
        }
        result = substitution(result, sbox);
        if (display) {
            
            System.out.println("v" + round + " = " + toBinaryString(result, 16));
            System.out.println("k" + (round + 1) + " = " + toBinaryString(keys[round], 16));
        }
        // k5 at keys[4]
        result ^= keys[round];
        if (display) {
            System.out.println("y  = " + toBinaryString(result, 16));
        }
        return result;
    }
    /**
     * Decrypt the message using SPN
     * @param cipher
     *      cipher text
     * @param round
     *      number of round
     * @param display
     *      boolean indicate whether to print out each step result
     * @param sbox
     *      sbox
     * @param perm
     *      permutation function
     * @param key
     *      key
     * @return plain text
     */
    public static int SPNDecrypt(int cipher, int round, boolean display, int[] sbox, int[] perm, int key) {
        int result = cipher;
        int[] keys = SPNKeyScheduler(key, round);
        result ^= keys[round];
        if (display) {
            System.out.println("k" + (round + 1) + " = " + toBinaryString(keys[round - 1], 16));
            System.out.println("v" + (round) + " = " + toBinaryString(result, 16));
        }
        result = substitution(result, sbox);
        if (display) {
            System.out.println("k" + (round) + " = " + toBinaryString(keys[round], 16));
            System.out.println("u" + (round) + " = " + toBinaryString(result, 16));
        }
        // k5 at keys[4]
        result ^= keys[round - 1];
        if (display) {
            System.out.println("w3 = " + toBinaryString(result, 16));
        }
        
     // round - 1 times
        for (int i = round-2; i >=0; i--) {
            result = permutation(result, perm);
            if (display) {
                System.out.println("v" + (i+1) + " = " + toBinaryString(result, 16));
            }
            result = substitution(result, sbox);
            if (display) {
                System.out.println("u" + (i + 1) + " = " + toBinaryString(result, 16));
            }
            result ^= keys[i];
            //cipher = toBinaryString(result);
            if (display) {
                System.out.println("k" + (i + 1) + " = " + toBinaryString(keys[i], 16));
                System.out.println("w" + (i ) + " = " + toBinaryString(result, 16));
            }
            
        }
        
        return result;
    }
    
    /**
     * Change integer to binary and add leading zeros if needed
     * @param number
     *      hexa value to be changed
     * @param length
     *      the desire length of the binary string
     * @return the binary string to the length indicate corresponding to the hexa value
     */
    public static String toBinaryString(int number, int length) {
        String result = Integer.toBinaryString(number);
        while(result.length() < length) {
            result = "0" + result;
        }
        return result;
    }
}

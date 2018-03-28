
public class Problem1 {
    public static int[] SPNKeyScheduler(int key, int round) {
        int[] keys = new int[round + 1];
        int mask = 0xffff;
        for(int i = round; i >= 0; i--) {
            keys[i] = mask & key;
            key >>= 4;
        }
        return keys;
    }
    
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
            
                //System.out.println(toBinaryString(result));

            result += pis[subs[i]];
        }
        return result;
    }
    
    public static int permutation(int input, int[] pip) {
        
        int one = 1;
        int result = 0;
        for (int i = 15; i >= 0; i--) {
            //if 1
            if ((input & (one << i)) != 0) {
                //System.out.println((15 - i) + " one " + toBinaryString(one << i));
                
                //put 1 at corresponding position
                result += (one << (16 - (pip[15 - i])));
                //System.out.println(pip[15 - i]);
                //System.out.println(toBinaryString(one << (16 - pip[15 - i])));
                //System.out.println(toBinaryString(result));
            }
        }
        //return Integer.parseInt(result.toString(),2);
        return result;
    }
    
    public static int SPNEncrypt(int plain, int round, boolean display, int[] sbox, int[] perm, int key) {
        int[] keys = SPNKeyScheduler(key, round);

        int result = plain;
        String cipher = toBinaryString(result);
        if (display) {
            System.out.println("w1 = " + toBinaryString(result));
        }
        // round - 1 times
        for (int i = 0; i < round - 1; i++) {
            result ^= keys[i];
            cipher = toBinaryString(result);
            if (display) {
                System.out.println("k" + (i + 1) + " = " + toBinaryString(keys[i]));
                System.out.println("u" + (i + 1) + " = " + cipher);
            }
            result = substitution(result, sbox);
            if (display) {
                System.out.println("v" + (i + 1) + " = " + toBinaryString(result));
            }
            result = permutation(result, perm);
            if (display) {
                System.out.println("w" + (i + 1) + " = " + toBinaryString(result));
            }
        }
        // last round
        // k4 is at position 3 in array keys
        result ^= keys[round - 1];
        if (display) {
            System.out.println("k" + (round) + " = " + toBinaryString(keys[round - 1]));
            System.out.println("u" + (round) + " = " + toBinaryString(result));
        }
        result = substitution(result, sbox);
        if (display) {
            
            System.out.println("v" + round + " = " + toBinaryString(result));
            System.out.println("k" + (round + 1) + " = " + toBinaryString(keys[round]));
        }
        // k5 at keys[4]
        result ^= keys[round];
        if (display) {
            System.out.println("y  = " + toBinaryString(result));
        }
        return result;
    }
    
    public static int SPNDecrypt(int cipher, int round, boolean display, int[] sbox, int[] perm, int key) {
        int result = cipher;
        int[] keys = SPNKeyScheduler(key, round);
        result ^= keys[round];
        if (display) {
            System.out.println("k" + (round + 1) + " = " + toBinaryString(keys[round - 1]));
            System.out.println("v" + (round) + " = " + toBinaryString(result));
        }
        result = substitution(result, sbox);
        if (display) {
            System.out.println("k" + (round) + " = " + toBinaryString(keys[round]));
            System.out.println("u" + (round) + " = " + toBinaryString(result));
        }
        // k5 at keys[4]
        result ^= keys[round - 1];
        if (display) {
            System.out.println("w3 = " + toBinaryString(result));
        }
        
     // round - 1 times
        for (int i = round-2; i >=0; i--) {
            result = permutation(result, perm);
            if (display) {
                System.out.println("v" + (i+1) + " = " + toBinaryString(result));
            }
            result = substitution(result, sbox);
            if (display) {
                System.out.println("u" + (i + 1) + " = " + toBinaryString(result));
            }
            result ^= keys[i];
            //cipher = toBinaryString(result);
            if (display) {
                System.out.println("k" + (i + 1) + " = " + toBinaryString(keys[i]));
                System.out.println("w" + (i ) + " = " + toBinaryString(result));
            }
            
        }
        
        return result;
    }
    
    public static String toBinaryString(int number) {
        String result = Integer.toBinaryString(number);
        while(result.length() < 16) {
            result = "0" + result;
        }
        return result;
    }
}

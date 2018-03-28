
public class Problem2 {
    public static int[][] table (int[] sbox) {
        int[][] result = new int[16][16];
        for (int a = 0; a < 16; a++) {
            for (int b = 0; b < 16; b++) {
                int count = 0;
                for (int x = 0; x < 16; x++) {
                    if (((a & x) ^ (b & sbox[x])) == 0){
                        count++;
                    }
                }
                result[a][b] = count;
            } // end b
        } // end a
        return result;
    }
    
    public static boolean SPNEncrypt(int plain, int round, boolean display, int[] sbox, int[] perm, int key) {
        int[] keys = Problem1.SPNKeyScheduler(key, round);
        boolean isZero = false;
        int result = plain;
        String cipher = Problem1.toBinaryString(result);
        if (display) {
            System.out.println("w1 = " + Problem1.toBinaryString(result));
        }
        // round - 1 times
        for (int i = 0; i < round - 1; i++) {
            result ^= keys[i];
            cipher = Problem1.toBinaryString(result);
            if (display) {
                System.out.println("k" + (i + 1) + " = " + Problem1.toBinaryString(keys[i]));
                System.out.println("u" + (i + 1) + " = " + cipher);
            }
            result = Problem1.substitution(result, sbox);
            if (display) {
                System.out.println("v" + (i + 1) + " = " + Problem1.toBinaryString(result));
            }
            result = Problem1.permutation(result, perm);
            if (display) {
                System.out.println("w" + (i + 1) + " = " + Problem1.toBinaryString(result));
            }
        }
        // last round
        // k4 is at position 3 in array keys
        result ^= keys[round - 1];
        if (display) {
            System.out.println("k" + (round) + " = " + Problem1.toBinaryString(keys[round - 1]));
            System.out.println("u" + (round) + " = " + Problem1.toBinaryString(result));
        }
        if (((plain & 1) ^ (result & (1 << 15)) ^ (result & (1 << 7))) == 0) {
            isZero = true;
        }
        result = Problem1.substitution(result, sbox);
        if (display) {
            
            System.out.println("v" + round + " = " + Problem1.toBinaryString(result));
            System.out.println("k" + (round + 1) + " = " + Problem1.toBinaryString(keys[round]));
        }
        // k5 at keys[4]
        result ^= keys[round];
        System.out.println("y  = " + Problem1.toBinaryString(result));

        return isZero;
    }
}

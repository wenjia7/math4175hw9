import java.util.Scanner;

public class HW9 {

    public static void main(String[] args) {
        //Scanner scan = new Scanner(System.in);
        //int input = scan.nextInt(2);
       // System.out.println(toBinaryString(0xab));
        // in 3.2
        int[][] pis = {{0xe, 0x4, 0xd, 0x1, 0x2, 0xf, 0xb, 0x8, 0x3, 0xa, 0x6, 0xc, 0x5, 0x9, 0, 0x7},{0xe, 0x3, 0x4, 0x8,0x1,0xc,0xa,0xf,0x7,0xd,0x9,0x6,0xb,0x2,0x0,0x5} };
        int[] pip = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
        int key = 0x3a94d63f;
        SPNEncrypt(0x26b7, 4, true, pis[0], pip, key);
        
        System.out.println(toBinaryString(key));
        //System.out.println(toBinaryString(key ^ 0x26b7));
        System.out.println("Decryption");
        SPNDecrypt(0xbcd6, 4, true, pis[1], pip, key);
        
    }
    
    private static int[] SPNKeyScheduler(int key, int round) {
        int[] keys = new int[round + 1];
        int mask = 0xffff;
        for(int i = round; i >= 0; i--) {
            keys[i] = mask & key;
            key >>= 4;
        }
        return keys;
    }
    
    private static int substitution(int input, int[] pis) {
        int[] subs = new int[4];
        int mask = 0xf;
        int result = 0;
        String[] substitute = new String[4];
        String output = toBinaryString(input);
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
    
    private static int permutation(int input, int[] pip) {
        //String inputStr =toBinaryString(input);
        //char[] output =  new char[inputStr.length()];
        //System.out.println("Input" + inputStr);
        //System.out.println(inputStr.length());
        //for(int i =0; i< inputStr.length();i++){
          //  output[pip[i]-1]= inputStr.charAt(i);
        //}
        //StringBuffer result = new StringBuffer();
        //for(char c : output)
        //{
         //   result.append(c);
        //}
        int one = 1;
        int result = 0;
        for (int i = 15; i >= 0; i--) {
            //if 1
            if ((input & (one << i)) != 0) {
                System.out.println((15 - i) + " one " + toBinaryString(one << i));
                
                //put 1 at corresponding position
               result += (one << (16 - (pip[15 - i])));
                System.out.println(pip[15 - i]);
                System.out.println(toBinaryString(one << (16 - pip[15 - i])));
                System.out.println(toBinaryString(result));
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
            System.out.println("k" + (round - 1) + " = " + toBinaryString(keys[round - 1]));
            System.out.println("u" + (round - 1) + " = " + toBinaryString(result));
        }
        result = substitution(result, sbox);
        if (display) {
            System.out.println("k" + round + " = " + toBinaryString(keys[round]));
            System.out.println("v" + round + " = " + toBinaryString(result));
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
            System.out.println("k" + (round) + " = " + toBinaryString(keys[round - 1]));
            System.out.println("v" + (round -1) + " = " + toBinaryString(result));
        }
        result = substitution(result, sbox);
        if (display) {
            System.out.println("k" + (round-1) + " = " + toBinaryString(keys[round]));
            System.out.println("u" + (round-1) + " = " + toBinaryString(result));
        }
        // k5 at keys[4]
        result ^= keys[round - 1];
        if (display) {
            System.out.println("w3  = " + toBinaryString(result));
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
        if (display) {
            System.out.println("y  = " + toBinaryString(result));
        }
        return result;
    }
    
    private static String toBinaryString(int number) {
        String result = Integer.toBinaryString(number);
        while(result.length() < 16) {
            result = "0" + result;
        }
        return result;
    }
    
    
}

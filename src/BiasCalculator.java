
public class BiasCalculator {

   
    
    public static int favOutcome(int textLength, int  key, int round, int[] sbox, int[] perm)
    {
        int count = 0;
        for(int i =0; i< Math.pow(2,16); i++)
        {
            String u = Problem1.toBinaryString(incompleteEncrypt(i, round, sbox, perm, key),16);
            String plain = Problem1.toBinaryString(i,16);
            if((u.charAt(0)=='0')^(u.charAt(8)=='0')^(plain.charAt(15)=='0'))
            {
                count++;
            }
            
        }
        
       
        
        
        return count;
    }
    
    public static int incompleteEncrypt(int plain, int round, int[] sbox, int[] perm, int key)
    {
        int[] keys = Problem1.SPNKeyScheduler(key, round);
        boolean display = false;
        int cipher = plain;
        if (display) {
            System.out.println("w1 = " + Problem1.toBinaryString(plain,16));
        }
        // round - 1 times
        for (int i = 0; i < round - 1; i++) {
            cipher ^= keys[i];
            if (display) {
                System.out.println("k" + (i + 1) + " = " + Problem1.toBinaryString(keys[i],16));
                System.out.println("u" + (i + 1) + " = " + Problem1.toBinaryString(cipher,16));
            }
            cipher = Problem1.substitution(cipher, sbox);
            if (display) {
                System.out.println("v" + (i + 1) + " = " + Problem1.toBinaryString(cipher,16));
            }
            cipher = Problem1.permutation(cipher, perm);
            if (display) {
                System.out.println("w" + (i + 1) + " = " + Problem1.toBinaryString(cipher,16));
            }
        }
        // last round
        // k4 is at position 3 in array keys
        cipher ^= keys[round - 1];
        if (display) {
            System.out.println("k" + (round) + " = " + Problem1.toBinaryString(keys[round - 1],16));
            System.out.println("u" + (round) + " = " + Problem1.toBinaryString(cipher,16));
        }
        return cipher;
        //if (((plain & 1) ^ (cipher & (1 << 15)) ^ (cipher & (1 << 7))) == 0) {
          //  isZero = true;
        //}
       // result = Problem1.substitution(result, sbox);
        //if (display) {
            
          //  System.out.println("v" + round + " = " + Problem1.toBinaryString(result));
           // System.out.println("k" + (round + 1) + " = " + Problem1.toBinaryString(keys[round]));
        //}
        // k5 at keys[4]
        //result ^= keys[round];
        //System.out.println("y  = " + Problem1.toBinaryString(result));

    }
}

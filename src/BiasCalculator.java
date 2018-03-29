import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BiasCalculator {

   
    /**
     * Encrypt all possible plain-cipher pair and count the number of cases in which T =0
     * @param textLength
     *          Length of plain text
     * @param key
     *          The key
     * @param round
     *          Number of round
     * @param sbox
     *          Sbox
     * @param perm
     *          Permutation
     * @param FileName
     *          Name of file where output will be printed
     * @return the number of cases
     * @throws IOException
     *          If file cannot be opened
     */
    public static int favOutcome(int textLength, int  key, int round, int[] sbox, int[] perm, String FileName) throws IOException
    {
        PrintWriter out = new PrintWriter(new FileWriter(FileName));
        
       
        int count = 0;
        int[] keys = SPNCryptoSystem.SPNKeyScheduler(key, round);//get the keys from K
        for(int i =0; i< Math.pow(2,textLength); i++)
        {
            int uInteger = incompleteEncrypt(i, round, sbox, perm, key);//encrypt up until the last round to get U_4
            String u = SPNCryptoSystem.toBinaryString(uInteger,16);
            String plain = SPNCryptoSystem.toBinaryString(i,16);
            if((u.charAt(0)=='0')^(u.charAt(8)=='0')^(plain.charAt(15)=='0'))//count the number of pair of plain-cipher such that T=0
            {
                count++;
            }
            
            int cipher = SPNCryptoSystem.substitution(uInteger, sbox);//continue the encryption
            cipher ^= keys[round];
            out.printf("Plaintext: %s, Cipher: %s\n", SPNCryptoSystem.toBinaryString(i,16),SPNCryptoSystem.toBinaryString(cipher,16));//print out all possible plain-cipher to a file
            
            
        }
        
        
        out.close();
        
        return count;//return the number of cases
    }
    /**
     * Encrypt using SPN up until the last round
     * @param plain
     *          plain text
     * @param round
     *          number of round
     * @param sbox
     *          sbox
     * @param perm
     *          permutation
     * @param key
     *          key
     * @return U_4
     */
    public static int incompleteEncrypt(int plain, int round, int[] sbox, int[] perm, int key)
    {
        int[] keys = SPNCryptoSystem.SPNKeyScheduler(key, round);
        boolean display = false;
        int cipher = plain;
        if (display) {
            System.out.println("w1 = " + SPNCryptoSystem.toBinaryString(plain,16));
        }
        // round - 1 times
        for (int i = 0; i < round - 1; i++) {
            cipher ^= keys[i];
            if (display) {
                System.out.println("k" + (i + 1) + " = " + SPNCryptoSystem.toBinaryString(keys[i],16));
                System.out.println("u" + (i + 1) + " = " + SPNCryptoSystem.toBinaryString(cipher,16));
            }
            cipher = SPNCryptoSystem.substitution(cipher, sbox);
            if (display) {
                System.out.println("v" + (i + 1) + " = " + SPNCryptoSystem.toBinaryString(cipher,16));
            }
            cipher = SPNCryptoSystem.permutation(cipher, perm);
            if (display) {
                System.out.println("w" + (i + 1) + " = " + SPNCryptoSystem.toBinaryString(cipher,16));
            }
        }
        // last round
        // k4 is at position 3 in array keys
        cipher ^= keys[round - 1];
        if (display) {
            System.out.println("k" + (round) + " = " + SPNCryptoSystem.toBinaryString(keys[round - 1],16));
            System.out.println("u" + (round) + " = " + SPNCryptoSystem.toBinaryString(cipher,16));
        }
        
        
        return cipher;
       
        

    }
    
    /**
     * Calculate the bias
     * @param favOutcome
     *      number of cases of T=0
     * @param possOutcome
     *      number of all possible cases
     * @return the bias
     */
    public static double bias(int favOutcome, int possOutcome)
    {
        return (double)((double)favOutcome/(double)possOutcome -0.5);
    }
}

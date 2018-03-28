
public class Problem3 {
    public static int  babyHorstEncrypt(int key, int plain) {
        int k1 = (key >> 4) & 0xf;
        int k2 = key & 0xf;
        int[] keys = {k1, k2};
        int l = (plain >> 4) & 0xf;
        int r = plain & 0xf;
        for (int i = 0; i < 2; i++) {
            // f(r, k)
            int frk = r & keys[i];
            // f xor l
            int r1 = l ^ frk;
            // update l and r
            l = r;
            r = r1;
        }
        return (l << 4) + r;
    }
    
    public static int  babyHorstDecrypt(int key, int cipher) {
        int k1 = (key >> 4) & 0xf;
        int k2 = key & 0xf;
        //System.out.println("k1 = " + Problem1.toBinaryString(k1, 4));
        //System.out.println("k2 = " + Problem1.toBinaryString(k2, 4));
        int[] keys = {k1, k2};
        int l = (cipher >> 4) & 0xf;
        //System.out.println("l = " + Problem1.toBinaryString(l, 4));
        int r = cipher & 0xf;
        //System.out.println("r = " + Problem1.toBinaryString(r, 4));
        for (int i = 0; i < 2; i++) {
            // f(r, k)
            int frk = l & keys[1 - i];
            //System.out.println("k = " + Problem1.toBinaryString(keys[1-i], 4));
            //System.out.println("frk = " + Problem1.toBinaryString(frk, 4));
            // f xor l
            int l1 = r ^ frk;
            // update l and r
            r = l;
            //System.out.println("r = " + Problem1.toBinaryString(r, 4));
            l = l1;
            //System.out.println("l = " + Problem1.toBinaryString(l, 4));
        }
        return (l << 4) + r;
    }
    
    public static int allPossibleR0(int key, int r1) {
        int k1 = (key >> 4) & 0xf;
        int l = 0;
        int count = 0;
        // from 0000 to 1111
        for (int r = 0; r < 16; r++) {
         // f(r, k)
            int frk = r & k1;
            // f xor l
            int r2 = l ^ frk;
            if (r2 == r1) {
                System.out.println("r0 = " + Problem1.toBinaryString(r, 4));
                count++;
            }
        }
        return count;
    }
    
}

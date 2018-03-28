
public class HW9 {

    public static void main(String[] args) {
        // problem 1
        // in 3.2
        int[][] pis = {{0xe, 0x4, 0xd, 0x1, 0x2, 0xf, 0xb, 0x8, 0x3, 0xa, 0x6, 0xc, 0x5, 0x9, 0, 0x7},{0xe, 0x3, 0x4, 0x8,0x1,0xc,0xa,0xf,0x7,0xd,0x9,0x6,0xb,0x2,0x0,0x5} };
        int[] pip = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
        int key = 0x8fa50743;
        Problem1.SPNEncrypt(0x26b7, 4, true, pis[0], pip, key);
        System.out.println("Decryption");
        Problem1.SPNDecrypt(0xf5b2, 4, true, pis[1], pip, key);
        
        // problem 2
        int k1 = 0x93e026de;
        int k2 = 0xe5d7f82e;
       // int count = 0;
        //int count2 = 0;
        int[] s2 = {8, 4, 2, 1, 0xc, 6, 3, 0xd, 0xa, 5, 0xe, 7, 0xf, 0xb, 9, 0};
        //for (int i = 0; i < Math.pow(2, 16); i++) {
          //  System.out.println("input = " + Problem1.toBinaryString(i));
            //if(Problem2.SPNEncrypt(i, 4, false, s2, pip, k1)) {
             //   count++;
            //}
            //System.out.println("key 1: " + res);
            //if(Problem2.SPNEncrypt(i, 4, false, s2, pip, k2)) {
              //  count2++;
            //}
            //System.out.println("key 1: " + res);
            
        //}
        
        int bias1 = BiasCalculator.favOutcome(16, k1, 4, s2, pip);
        System.out.println("new key 1  test is " + bias1);
        
        int bias2 = BiasCalculator.favOutcome(16, k2, 4, s2, pip);
        System.out.println("key 2: " + bias2);
    }
    
    
    
    
}

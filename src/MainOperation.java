import java.io.IOException;

public class MainOperation {

    public static void main(String[] args) throws IOException {
        // problem 1
        // in 3.2
        int[][] pis = {{0xe, 0x4, 0xd, 0x1, 0x2, 0xf, 0xb, 0x8, 0x3, 0xa, 0x6, 0xc, 0x5, 0x9, 0, 0x7},{0xe, 0x3, 0x4, 0x8,0x1,0xc,0xa,0xf,0x7,0xd,0x9,0x6,0xb,0x2,0x0,0x5} };
        int[] pip = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
        int key = 0x8fa50743;
        SPNCryptoSystem.SPNEncrypt(0x26b7, 4, true, pis[0], pip, key);
        System.out.println("Decryption");
        SPNCryptoSystem.SPNDecrypt(0xf5b2, 4, true, pis[1], pip, key);
        
        // problem 2
        int k1 = 0x93e026de;
        int k2 = 0xe5d7f82e;
        int count=0;
        int[] s2 = {8, 4, 2, 1, 0xc, 6, 3, 0xd, 0xa, 5, 0xe, 7, 0xf, 0xb, 9, 0};

       
        int NumberOfPlainTIsZeroKey1 = BiasCalculator.favOutcome(16, k1, 4, s2, pip, "EcryptionKey1.txt");       
        System.out.println("number of plaintext that T =0 for key 1   " + NumberOfPlainTIsZeroKey1 );
        double biasKey1 = BiasCalculator.bias(NumberOfPlainTIsZeroKey1,(int)Math.pow(2,16));
        System.out.println("bias of key 1 is : " + biasKey1);
        
        int NumberOfPlainTIsZeroKey2 = BiasCalculator.favOutcome(16, k2, 4, s2, pip,"EcryptionKey2.txt");
        System.out.println("number of plaintext that T =0 for key 2   " + NumberOfPlainTIsZeroKey2);
        double biasKey2 = BiasCalculator.bias(NumberOfPlainTIsZeroKey2,(int)Math.pow(2,16));
        System.out.println("bias of key 2 is : " + biasKey2);
        

        //problem 3
       
       BabyHorst encryption = new BabyHorst(2,0x5b);
       System.out.println("the plaint text is " +encryption.decrypt(0x08));
    
    
}
}

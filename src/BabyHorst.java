
public class BabyHorst {

    int round;
    int[] keys;
    int[] L;
    int[] R;
    public BabyHorst(int round, int key)
    {
        this.round = round;
        this.keys = keyScheduler(key);
        this.L = new int[round+1];
        this.R = new int[round+1];
    }
    
    
    public int[] keyScheduler(int key)
    {
        int[] keys = new int[this.round];
        int mask = 0xf;
        for(int i = round-1; i >= 0; i--) {
            keys[i] = mask & key;
            key >>= 4;
        }  
        return keys;
    }
    
    public String decrypt(int cipher)
    {
        divideText(cipher);
        for(int i =round-1; i>=0; i--)
        {
            R[i] = L[i+1];
            int internalRoundFunction = this.keys[i]&R[i];
            L[i] = internalRoundFunction^R[i+1];
            System.out.println(" L_" + i + " : " +L[i]);
            System.out.println(" R_" + i + " : " +R[i]);
            
        }
        
        return L[this.round]+""+R[this.round];
    }
    
    public void divideText(int text)
    {
        int mask = 0xf;
        this.R[this.round] = mask & text;
        text >>= 4;
        this.L[this.round] = mask & text;
     }  
    
 
}
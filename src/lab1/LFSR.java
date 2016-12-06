package lab1;

import java.util.Random;

/**
 * Class realizes pseudo random binary sequence generator based on LFSR
 */
public class LFSR {

    private int bit;
    private int count;


    public LFSR(int bit){
        this.bit = bit;
        this.count = (int)Math.pow(2,this.bit) - 1;
    }


    /**
     * this method simulates diversity LFSR
     *
     * @param polynomial - array of polynomial`s coefficient
     * @param inputArr   - initial binary sequence in the register
     * @return pseudo random binary sequence bitArr
     */
    public byte[] getSequence(byte[] polynomial, byte[] inputArr){

        int inputLength = inputArr.length;
        int count = this.count;
        byte[] bitArr = new byte[count];

        for (int i = 0; i < count; i++) {

            byte out = inputArr[inputLength-1];
            bitArr[i] = out;

            for (int j : polynomial)
                inputArr[j - 1] ^= out;

            inputArr[0] = out;
            inputArr = shiftLeft(inputArr);
        }
        return bitArr;
    }


    /**
     * Realises tabular generator of pseudo random binary sequence
     *
     * @param bit          - size of tabular generator
     * @param inputBitArrs - initial binary sequences in the register
     * @param SELECTION    - length of final sequence
     * @return pseudo random binary sequence
     */
    public static byte[] tabularGenerator(int bit, byte[][] inputBitArrs, int SELECTION){

        byte[][] binarySequences = new byte[bit][];

        try {
            LFSR[] registers = new LFSR[bit];
            for (int i = 0; i < bit; i++) {
                registers[i] = new LFSR(i + 8);
                binarySequences[i] = registers[i].getSequence(Polynomial.getPolynomial(i + 8), inputBitArrs[i]);
            }
        }catch (Exception e){
            System.out.println("invalid polynomial`s size");
        }

        byte[] table = getRandomTable(bit);

        byte[] result = new byte[SELECTION];

        for (int i = 0; i < SELECTION; i++) {
            StringBuffer stringNumber = new StringBuffer();
            for (int j = 0; j < bit; j++)
                stringNumber.append(binarySequences[j][i%binarySequences[j].length]);
            int number = Integer.parseInt(new String(stringNumber), 2);
            result[i] = table[number];
        }
        return result;
    }

    /**
     * @param arr - binary array in register
     * @return left shift displayed binary array
     */
    public static byte[] shiftLeft(byte[] arr){

        System.arraycopy(arr, 0, arr, 1, arr.length-1);
        return arr;
    }

    /**
     * @param bit - size of tabular generator
     * @return random binary array
     */
    public static byte[] getRandomTable(int bit){

        int count = (int)Math.pow(2, bit);
        byte[] table = new byte[count];
        Random random = new Random();

        for (int i = 0; i < count; i++)
            table[i] = (byte)random.nextInt(2);

        return table;
    }

}

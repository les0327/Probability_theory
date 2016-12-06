package lab1;

public class Test {

    public static void test(byte[] sequence, int windowSize){

        System.out.printf("Frequency Test : %f%c",Test.frequencyTest(sequence), '\n');
        System.out.printf("Differential Test : %f%c",Test.differentialTest(sequence), '\n');
        try {
            System.out.printf("Rank test :%c%s%c",'\n', arrToString(Test.rankTest(sequence, windowSize)), '\n');
        } catch (Exception e) {
            System.out.println("windowSize > sequence.length");
        }
        System.out.printf("Linear Complexity: %d%c", linearComplexity(sequence), '\n');
    }

    /**
     * shows frequency of 1 in binary sequence
     * good result is near 0.5
     *
     * @param sequence - byte array of bits
     * @return numeric from 0 to 1
     */
    public static float frequencyTest(byte[] sequence){

        int oneCount = 0;

        for (byte bit : sequence)
            oneCount += bit;

        return (float)oneCount/sequence.length;
    }


    /**
     * differential test
     * good result is near 0.5
     *
     *@param sequence - byte array of bits
     *@return numeric from 0 to 1
     */
    public static float differentialTest(byte[] sequence){

        byte[] buffer = new byte[sequence.length -1];

        for (int i = 1; i < sequence.length; i ++)
            buffer[i-1] = (byte)(sequence[i]^sequence[i-1]);

        return frequencyTest(buffer);
    }


    /**
     * @param sequence   - byte array of bits
     * @param windowSize - number of bits
     * @return integer array of counts of bit repeats
     * @throws Exception
     */
    public static int[] rankTest(byte[] sequence, int windowSize) throws Exception{

        if (windowSize > sequence.length)
            throw new Exception();

        int[] counts = new int[(int)Math.pow(2, windowSize)];

        for(int i = 0; i < sequence.length - windowSize + 1; i++){

            int index = 0;
            for (int j = 0; j < windowSize; j++)
                index += (int)Math.pow(2, windowSize - j - 1)*sequence[i+j];

            counts[index]++;
        }
        return counts;
    }


    /**
     * Berlekamp - massey algorithm that find the shortest linear feedback
     * shift register (LFSR) for a given binary output sequence
     *
     * @param sequence - byte array of bits
     * @return linear complexity of sequence
     */
    public static int linearComplexity(byte[] sequence){

        int L = 0;
        int N = 0;
        int m = -1;
        int d;

        int n = sequence.length;
        byte[] B = new byte[n];
        byte[] T = new byte[n];
        byte[] C = new byte[n];

        B[0] = C[0] = 1;

        while (N < n){
            d = sequence[N];
            for (int i = 1; i <= L; i++)
                d ^= C[i]&sequence[N-i];
            if (d == 1){
                System.arraycopy(C, 0, T, 0, N);
                for (int i = 0; (i + N - m) < n; i++)
                    C[i + N - m] ^= B[i];
                if (2*L < N){
                    L = N + 1 - L;
                    m = N;
                    System.arraycopy(T, 0, B, 0, N);
                }
            }
            N++;
        }
        return L;
    }

    private static String arrToString(int[] arr){
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Integer.toString(i, 2)).append("-").append(arr[i]).append("\n");
        }
        return new String(sb);
    }
}

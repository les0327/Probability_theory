package lab1;

import java.util.Arrays;

public class Main {

    public final static int SELECTION = 15000;

    public static void main(String[] args) {

        byte[][] inputBitArrs = {
                {0, 1, 0, 1, 1, 0, 0, 1},
                {1, 1, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1},
                {1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                {1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0},
                {0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1}
        };

        long start = System.currentTimeMillis();

        byte[] sequence = LFSR.tabularGenerator(10, inputBitArrs, SELECTION);

        System.out.println(Arrays.toString(sequence));

        Test.test(sequence, 4);

        long finish = System.currentTimeMillis();
        System.out.printf("Work time : %d (milliseconds)", (finish-start));
    }

}

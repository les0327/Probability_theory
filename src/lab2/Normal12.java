package lab2;

import java.util.Arrays;

public class Normal12 {

    public static final int SIZE = 5000;

    public static void main(String[] args) {
        test(3, 5);
    }

    public static void test(double m, double sigma){
        double[] arr = normal12(m, sigma);
        double mE = getM(arr);
        double sigmaE = getSigma(arr, mE);

        System.out.println(Arrays.toString(arr));
        System.out.printf("Теоретично: m = %f, sigma = %f%c", m, sigma, '\n');
        System.out.printf("Експерементально: m = %f, sigma = %f%c", mE, sigmaE, '\n');
    }

    private static double[] normal12(double m, double sigma){
        double[] result = new double[SIZE];

        for (int i = 0; i < SIZE; i ++){

            double y = -6;
            for (int j = 1; j <= 12; j++)
                y += Math.random();

            result[i] = sigma * y + m;
        }

        return result;
    }

    private static double getM(double[] arr){
        long S = 0;
        for (double d : arr)
            S += d;
        return (double)S/arr.length;
    }

    private static double getSigma(double[] arr, double m){
        long S = 0;
        for (double d : arr)
            S += Math.pow(d - m, 2);
        return Math.sqrt((double)S/arr.length);
    }

}

package lab2;

import java.util.Arrays;

public class Binomial {

    static final int COUNT = 100;

    public static void main(String[] args) {
        Binomial b = new Binomial();
        b.test(0.2, 10);
    }

    public Binomial() {
        super();
    }

    private static long factorial(int n){
        long result = 1;
        for (int i = 2; i < n + 1; i++)
            result *= i;
        return result;
    }

    private static long combination(int m, int n){
        return factorial(n)/factorial(m)/factorial(n-m);
    }

    private static double bernoulli(double p, int m, int n){
        return Math.pow(p, m) * Math.pow(1 - p, n - m) * combination(m, n);
    }

    public static double[] getTable(double p, int n){
        double[] table = new double[n];

        double buf = 0;
        for (int i = 0; i < n; i++){
            table[i] = bernoulli(p, i, n) + buf;
            buf = table[i];
        }

        return table;
    }

    private int getMinM(double r, double[] table){
        for (int i = 0; i < table.length; i++)
            if (table[i] > r)
                return i;
        return -1;
    }

    private double getM(int[] arr){
        long S = 0;
        for (int i : arr)
            S += i;
        return (double)S/arr.length;
    }

    private double getSigma(int[] arr, double m){
        long S = 0;
        for (int i : arr)
            S += Math.pow(i - m, 2);
        return Math.sqrt((double)S/arr.length);
    }

    private int[] binomial(double p, int n){
        int[] result = new int[COUNT];
        double[] table = getTable(p, n);

        for (int i = 0 ; i < COUNT; i++)
            result[i] = getMinM(Math.random(), table);

        return result;
    }

    /**
     * @param p - probability of successful attempts
     * @param n - number of attempts
     */
    public void test(double p, int n){
        int[] arr = binomial(p, n);
        double m = getM(arr);
        double sigma = getSigma(arr, m);

        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();
        System.out.printf("Теоретично: m = %f, sigma = %f%c", p*n, p*n*(1 - p), '\n');
        System.out.printf("Експерементально: m = %f, sigma = %f%c", m, sigma, '\n');
    }

}

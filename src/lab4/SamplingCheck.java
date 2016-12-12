package lab4;

import lab2.Binomial;

import java.util.*;

/**
 * Created on 04.12.2016
 *
 * Check random sample by χ2 criterion
 *
 * @author Les
 * @version 1.0
 */
public class SamplingCheck {

    private static HashMap<Double, Double> table = new HashMap<Double, Double>(){
        {
            put(0.02, 0.01);
            put(0.04, 0.02);
            put(0.103, 0.05);
            put(0.211, 0.1);
            put(0.446, 0.2);
            put(0.713, 0.3);
            put(1.386, 0.5);
            put(2.41, 0.8);
            put(3.22, 0.8);
            put(4.6, 0.9);
            put(5.99, 0.95);
        }
    };
    private int n;
    private double p;

    public static void main(String[] args) {
        SamplingCheck sc = new SamplingCheck();

        int[] sample = sc.input();
        System.out.println(Arrays.toString(sample));

        sc.getN();

        System.out.println("n = " + sc.n);

        double average = sc.average(sample);
        System.out.println("Average = " + average);

        double sigma = sc.standardDeviation(average, sample);
        System.out.println("Deviation = " + sigma);

        sc.p = average/sc.n;
        System.out.println("p = " + sc.p);

        double lim1 = (average + Arrays.stream(sample).min().getAsInt())/2;
        double lim2 = (Arrays.stream(sample).max().getAsInt() + average)/2;


        System.out.printf("Limit 1 = %f, limit 2 = %f\n", lim1, lim2);

        double x2 = sc.x2(sample, lim1, lim2);

        System.out.println("X2 = " + x2);

        double probability = sc.getProbability(x2);

        System.out.println("Probability = " + probability);
    }


    /**
     * Allows to get int array from inputted string
     *
     * @return arrays of integers from inputted line
     */
    public int[] input() {
        System.out.println("Enter a sample:");

        String str = new Scanner(System.in).nextLine();

       return Arrays.stream(str.trim().split(" ")).mapToInt(Integer::parseInt).sorted().toArray();
    }

    /**
     * Calculate average value of array
     *
     * @param arr -  array of integers
     * @return average of array
     */
    public double average(int[] arr){
        return Arrays.stream(arr).average().getAsDouble();
    }


    /**
     * Calculate standard deviation
     *
     * @param average - average value of arr
     * @param arr     - array of integers
     * @return standard deviation
     */
    public double standardDeviation(double average, int[] arr){
        return Math.sqrt(Arrays.stream(arr).asDoubleStream().map(e -> (e-average)*(e-average)).sum()/arr.length);
    }

    /**
     * Allows to enter n
     */
    public void getN(){
        System.out.println("Enter n: ");
        n = new Scanner(System.in).nextInt();
    }


    /**
     * Divide sample in 3 intervals and calculate probability of each interval
     * @param limit1 - limit of 1 interval
     * @param limit2 - limit of 2 interval
     * @return double array of probabilities
     */
    private double[] intervalProbability(double limit1, double limit2){
        double[] result = new double[3];

        for(int i = 0; i < limit1; i++)
            result[0] += Binomial.bernoulli(p, i, n);

        for(int i = (int)limit1 + 1; i < limit2; i++)
            result[1] += Binomial.bernoulli(p, i, n);

        result[2] = 1 - result[0] - result[1];

        return result;
    }

    /**
     * Calculate count of elements in each interval
     * @param arr     - sorted sample
     * @param limit1  - limit of 1 interval
     * @param limit2  - limit of 2 interval
     * @return array of m
     */
    private int[] getM(int[] arr, double limit1, double limit2){
        int[] m = new int[3];

        for(int i = 0; arr[i++] < limit1; m[0]++);
        for(int i = m[0]; arr[i++] < limit2; m[1]++);
        m[2] = arr.length - m[0] - m[1];

        return m;
    }

    /**
     * Calculate x2
     * @param sample - sorted sample
     * @param l1     - limit of 1 interval
     * @param l2     - limit of 2 interval
     * @return x2
     */
    public double x2(int[] sample, double l1, double l2){
        double x2 = 0;

        double[] probability = intervalProbability(l1, l2);
        int[] m              =        getM(sample, l1, l2);

        System.out.println(Arrays.toString(probability));
        System.out.println(Arrays.toString(m));

        for (int i = 0; i < 3; i++)
            x2 += Math.pow(m[i] - sample.length * probability[i], 2) / sample.length / probability[i];

        return x2;
    }


    /**
     * Calculate probability of our supposition
     * @param x2 - x2
     * @return probability of our supposition
     */
    public double getProbability(double x2){
        double key = 0.02;

        for (Double k : table.keySet())
            if (Math.abs(k - x2) < Math.abs(key - x2))
                key = k;

        return 1 - table.get(key);
    }
}

package lab4;

import lab2.Binomial;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created on 04.12.2016
 *
 * Check random sample by χ2 criterion
 *
 * @author Les
 * @version 1.0
 */
public class SamplingCheck {

    public static void main(String[] args) {
        SamplingCheck sc = new SamplingCheck();

        int[] sample = sc.input();
        System.out.println(Arrays.toString(sample));

        double average = sc.average(sample);
        System.out.println("Average = " + average);

        double sigma = sc.standardDeviation(average, sample);
        System.out.println("Deviation = " + sigma);

        int n = sc.getN(sample);
        System.out.println("n = " + n);

        double p = average/n;
        System.out.println("p = " + p);
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
     * Get N
     * @param arr - array of integers
     * @return return max element from arr
     */
    public int getN(int[] arr){
        return Arrays.stream(arr).max().getAsInt();
    }

    /*
    public double x2(int[] arr, double p, int n){
        double[] table = Binomial.getTable(p, n);
    }
    */
}

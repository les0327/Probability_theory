package lab4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created on 11.12.2016
 *
 * @author Les
 * @version 1.0
 */
public class Simpson {

    private static HashMap<Double, Double> x2Table = new HashMap<Double, Double>(){
        {
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
    private double a;
    private double[] sample;
    private double average;

    public static void main(String[] args) {
        Simpson s = new Simpson();

        s.init();
        System.out.println(Arrays.toString(s.sample));

        s.getA();
        System.out.println("a = " + s.a);

        s.getAverage();
        System.out.println("average = " + s.average);

        s.valid();

        double lim1 = (s.average + Arrays.stream(s.sample).min().getAsDouble())/2;
        double lim2 = (Arrays.stream(s.sample).max().getAsDouble() + s.average)/2;
        System.out.printf("Limit 1 = %f, limit 2 = %f\n", lim1, lim2);

        double[] p = s.intervalProbability(lim1, lim2);
        System.out.println(Arrays.toString(p));
        //System.out.println(p[0] + p[1] + p[2]);

        int[] m = s.getM(lim1, lim2);
        System.out.println(Arrays.toString(m));

        double x2 = s.x2(p, m);
        System.out.println("X2 = " + x2);

        double probability = s.getProbability(x2);
        System.out.println("Probability = " + probability);
    }

    public void init(){
        System.out.println("Enter a sample:");

        Scanner in = new Scanner(System.in);

        String[] strs = in.nextLine().trim().split(" ");

        sample = new double[strs.length];

        for (int i = 0; i < sample.length; i++)
            sample[i] = Double.parseDouble(strs[i]);

        Arrays.sort(sample);
    }

    public void getA(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a:");
        a = Double.parseDouble(in.nextLine().trim());
    }

    public void getAverage(){
        average = 0;

        for(double d : sample)
            average += d;

        average /= sample.length;
    }

    private void valid(){
        if (average - a > sample[0]) {
            System.out.println("Probability = 0");
            System.exit(-1);
        }
        if (average + a < sample[sample.length - 1]){
            System.out.println("Probability = 0");
            System.exit(-1);
        }

    }

    public double[] intervalProbability(double limit1, double limit2){
        double[] p = new double[3];

        p[0] = 0.5 * Math.pow((limit1 - average + a)/a, 2);
        p[2] = 0.5 * Math.pow((average + a - limit2)/a, 2);
        p[1] = 1 - p[0] - p[2];

        return p;
    }

    public int[] getM(double limit1, double limit2){
        int[] m = new int[3];

        for (int i = 0; sample[i] < limit1; i++)
            m[0]++;

        for(int i = m[0]; sample[i] <= limit2; i++)
            m[1]++;

        m[2] = sample.length - m[0] - m[1];

        return m;
    }

    public double x2(double[] p, int[] m){
        double x2 = 0;

        for (int i = 0; i < 3; i++)
            x2 += Math.pow(m[i] - sample.length * p[i], 2) / sample.length / p[i];

        return x2;
    }

    public double getProbability(double x2){
        double key = 0.02;

        for (Double k : x2Table.keySet())
            if (Math.abs(k - x2) < Math.abs(key - x2))
                key = k;

        x2Table.put(0.02, 0.01);

        return 1 - x2Table.get(key);
    }
}

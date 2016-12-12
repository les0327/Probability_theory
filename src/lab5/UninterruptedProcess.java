package lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created on 12.12.2016
 *
 * @author Les
 * @version 1.0
 */
public class UninterruptedProcess {

    private double[][] matrix;
    private int nodeCount;
    private static final double TIME = 10000;

    public static void main(String[] args) {
        UninterruptedProcess up = new UninterruptedProcess();

        up.init();

        System.out.println(Arrays.toString(up.calculateP()));
        System.out.println(Arrays.toString(up.model()));
    }

    /**
     * Initialize graph
     */
    public void init() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Enter node count:");
            nodeCount = Integer.parseInt(in.readLine().trim());

            matrix = new double[nodeCount][nodeCount];

            System.out.println("Enter matrix: ");
            for (int i = 0; i < nodeCount; i++)
                matrix[i] = Arrays.stream(in.readLine().trim().split(" ")).mapToDouble(Double::parseDouble).toArray();

            print(matrix);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create system of linear equations and solve it
     * @return static probabilities
     */
    public double[] calculateP() {

        double[][] m = new double[nodeCount][nodeCount + 1];

        for (int i = 0; i < m.length + 1; i++)
            m[0][i] = 1;

        for (int i = 1; i < nodeCount; i++){

            for (int j = 0; j < m.length; j++){
                m[i][j] = -matrix[j][i - 1];
            }
            m[i][i - 1] = Arrays.stream(matrix[i-1]).sum();
        }


        for (int i = 0 ; i < m.length - 1; i++){

            for (int j = i + 1; j < m.length; j++){

                double t = m[j][i] / m[i][i];

                for (int k = 0; k < m[i].length; k++) {
                    m[j][k] -= t * m[i][k];
                }

            }
        }

        double[] x = new double[nodeCount];

        for (int i = m.length - 1; i >= 0; i--){

            x[i] = m[i][m.length] / m[i][i];

            if (i != m.length - 1)
                for (int j = i + 1; j < m.length; j++)
                    x[i] -= m[i][j] * x[j] / m[i][i];
        }

        return x;
    }

    /**
     * Modelling process and calculate static probabilities
     * @return modeled static probabilities
     */
    public double[] model(){
        double[] t = new double[nodeCount];
        double T = 0;

        int node = 0;
        int nextNode = 0;


        while (T < TIME){

            double[] buf = new double[nodeCount];

            for (int i = 0; i < matrix.length; i++){
                if (matrix[node][i] != 0)
                    buf[i] = -1 / matrix[node][i] * Math.log(Math.random());
            }

            double min = Double.POSITIVE_INFINITY;

            for(int j = 0; j < buf.length; j++)
                if (buf[j] != 0 && buf[j] < min){
                    min = buf[j];
                    nextNode = j;
                }
            T += min;
            t[node] += min;
            node = nextNode;
        }

        for (int i = 0; i < t.length; i++)
            t[i] /= T;

        return t;
    }

    /**
     * Print matrix
     * @param matrix - matrix to print
     */
    private static void print(double[][] matrix) {
        for (double[] arr : matrix) {
            for (double d : arr)
                System.out.print(d + " ");
            System.out.println();
        }
        System.out.println();
    }
}

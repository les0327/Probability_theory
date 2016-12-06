package lab3;

import java.util.Random;

/**
 * Created on 31.10.2016
 *
 * Class describe distribution of 2 values.
 *
 * @author Les
 * @version 1.0
 */
public class Distribution {

    private Pair[] pairs;
    static final int COUNT = 1000;

    public static void main(String[] args) {
        Distribution d = new Distribution();

        long start = System.currentTimeMillis();

        d.init(false);

        Pair expectation = d.expectation();
        Pair dispersion  = d.dispersion(expectation);
        double cov = d.covariance(expectation);

        long finish = System.currentTimeMillis();

        System.out.printf("Mathematical expectation: %s.\n", expectation);

        System.out.printf("Dispersion: %s.\n", dispersion);

        System.out.printf("Covariance: %f.\n", cov);

        System.out.printf("Spent time: %f s.", (double)(finish - start)/1000);

    }

    private void init(boolean print){
        pairs = new Pair[COUNT];

        Random r = new Random();

        double x = 0;
        double y = 0;

        for (int i = 0; i < COUNT; i++){
            double ri = r.nextDouble();

            if (ri < 0.5)
                x = 1 - Math.sqrt(1 - ri/0.5);
            else
                x = 1 + Math.sqrt(ri/0.5 - 1);

            y = r.nextDouble();

            pairs[i] = new Pair(x, y);
        }

        if (print)
            for (Pair p : pairs)
                System.out.println(p);
    }

    private Pair expectation(){
        double mx = 0;
        double my = 0;

        for (Pair p : pairs){
            mx += p.getX();
            my += p.getY();
        }

        return new Pair(mx/COUNT, my/COUNT);
    }

    private Pair dispersion(Pair expectation){
        double dx = 0;
        double dy = 0;
        double mx = expectation.getX();
        double my = expectation.getY();

        for (Pair p : pairs) {
            dx +=(p.getX() - mx)*(p.getX() - mx);
            dy +=(p.getY() - my)*(p.getY() - my);
        }

        return new Pair(dx/COUNT, dy/COUNT);
    }

    private double covariance(Pair expectation){

        double cov = 0;
        double mx = expectation.getX();
        double my = expectation.getY();

        for (Pair p : pairs){
            cov += (p.getX() - mx)*(p.getY() - my);
        }

        return cov/COUNT;
    }

    private class Pair{

        private double x;
        private double y;

        Pair(double x, double y){
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}

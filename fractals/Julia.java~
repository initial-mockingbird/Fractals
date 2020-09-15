package fractals;
import fractals.*;

/**
* Mandelbrot class that graphs an approximation
* of the Mandelbrot set.
*/
public class Mandelbrot extends Fractal {

    /**
    * Creates a Mandelbrot object with parameters:
    * @param low         lower-left coordinates.
    * @param high        upper-right coordinate.
    * @param nrows       pixel counting - how many y pixels
    * @param ncols       pixel counting - how many pixels x pixels.
    * @param maxIters    how many iterations to consider for a set inclusion.
    * @param escapeVals  cached answers for each point's iteration to escape.
    * @param c           c value of the iteration function
    * @return A mandelbrot object.
    */
    public Mandelbrot(Complex low, Complex high, int nrows, int ncols, int maxIters){
        this.low = low;
        this.high = high;
        this.nrows = nrows;
        this.ncols = ncols;
        this.maxIters = maxIters;
        this.c = new Complex(0.0, 0.0);
    }


    /**
    * {@inheritdoc}
    */
    public int escapeCount(Complex p){

        /* Mandelbrots formula is of the form: z_n+1 = z_n +c*/
        Complex z = new Complex(0.0,0.0);
        Complex c = p.copy();
        int i;

        /* 
        * If the squared absolute value of p is greater than
        * 4 then we escape.
        */
        for (i=0; i<this.maxIters;i++){
            if (Complex.abs(z)>4){
                return i;
            }

            z = Complex.add(Complex.mul(z,z),c);
        }

        return maxIters;
        
    }

    public static void main (String[] args){

        Complex low  = new Complex(Double.parseDouble(args[0]),
                                   Double.parseDouble(args[2]));
        
        Complex high = new Complex(Double.parseDouble(args[1]),
                                   Double.parseDouble(args[3]));
        
        int nrows       = Integer.parseInt(args[4]);
        int ncols       = Integer.parseInt(args[5]);
        int maxIters    = Integer.parseInt(args[6]);
        String filename = args[7];

        
        Mandelbrot m = new Mandelbrot (low, high, nrows, ncols,
                                       maxIters);

        try {
        m.write(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
                            

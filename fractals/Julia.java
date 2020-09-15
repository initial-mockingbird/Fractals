package fractals;
import fractals.*;

/**
* Julia class that graphs an approximation
* of the Julia set.
*/
public class Julia extends Fractal {

    /**
    * Creates a Julia object with parameters:
    * @param low         lower-left coordinates.
    * @param high        upper-right coordinate.
    * @param nrows       pixel counting - how many y pixels
    * @param ncols       pixel counting - how many pixels x pixels.
    * @param maxIters    how many iterations to consider for a set inclusion.
    * @param escapeVals  cached answers for each point's iteration to escape.
    * @param c           c value of the iteration function
    * @return A Julia object.
    */
    public Julia(Complex low, Complex high, int nrows, int ncols, int maxIters, Complex c){
        this.low = low;
        this.high = high;
        this.nrows = nrows;
        this.ncols = ncols;
        this.maxIters = maxIters;
        this.c = c.copy();
        this.escapeVals = this.escapes();
    }


    /**
    * {@inheritdoc}
    */
    public int escapeCount(Complex p){

        /* Julias formula is of the form: z_n+1 = z_n +c*/
        Complex z = p.copy();
        Complex c = this.c.copy();
        int i;

        /* 
        * If the squared absolute value of p is greater than
        * 2 then we escape.
        */
        for (i=0; i<this.maxIters;i++){
            if (Complex.abs(z)>2){
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

        Complex c = new Complex(Double.parseDouble(args[7]),
                                Double.parseDouble(args[8]));
        
        String filename = args[9];

        
        Julia m = new Julia (low, high, nrows, ncols,
                             maxIters, c);

        System.out.println(m.toString());
        try {
        m.write(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
                            

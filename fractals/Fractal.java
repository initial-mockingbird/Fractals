package fractals;
import java.io.*;
import java.lang.Math;
import fractals.Complex;

/**
* Abstract class that serves as a superclass for our fractals.
*/
public abstract class Fractal {

    /**
    * low,high:    lower-left and upper-right coordinates.
    * nrows,ncols: pixel counting - how many pixels in each direction.
    * maxIters:    how many iterations to consider for a set inclusion.
    * escapeVals:  cached answers for each point's iteration to escape.
    * c:           c value of the iteration function
    */
    protected Complex low, high;
    protected int nrows, ncols;
    protected int maxIters;
    protected int[][] escapeVals;
    protected Complex c;

    /**
    * Given one point p, determines the number of iterations
    * up to maxIters before it escapes.
    * @param p Any point.
    * @return Number of iterations before p escapes.
    */
    public abstract int escapeCount(Complex p);

    /**
    * Calculate escape counts for each point indicated by current instance
    * variables.
    * @return An array of escape counts.
    */
    public int[][] escapes(){
        int [][] escps = new int[this.nrows][this.ncols];
        /*
        * Let x = |low.r-high.r| and y = |low.i - high.i|, 
        * Then note that the following relation
        *
        * escape[i][j] = escape[0][0] + (i)*(x/nrows) - (j)*(y/ncols) (1)
        * escape[0][0] = low.r + high.i i
        *
        * Holds (the proof is trivial), therefore we declare:
        *
        * diff = x + yi
        * stepRow = x/nrows
        * stepCol = y/ncolsin order to take advantage of it.
        *
        * in order to implement (1)
        */
        Complex diff    = new Complex (Math.abs(this.low.r-this.high.r), Math.abs(this.low.i-this.high.i));
        Complex stepCol = new Complex (diff.i/(this.ncols -1),0.0);
        Complex stepRow = new Complex (0.0, diff.r/(this.nrows -1));
        Complex p       = new Complex (this.low.r, this.high.i);

        /*
        * This is just the implementation of (1)
        */
        for (int i=0; i< this.nrows;i++){
            for (int j=0; j<this.ncols;j++){
                escps[i][j] = this.escapeCount(p);
                p = Complex.add(p,stepCol);
            }
            p = Complex.sub(p,stepRow);
            p = new Complex(this.low.r,p.i);
        }

        
        return escps;
    }

    /**
    * Given a filename, calculate all the escape counts as needed, 
    * and create a file which has the following format:
    * nrows ncols maxIters
    * lowRealVal highRealVal lowImaginaryVal highImaginaryVal
    * realC imaginaryC
    * <blank line>
    * escapeVals as a matrix
    * @param filename name of the file.
    */
    public void write (String filename) throws IOException{
        // TODO: refactor write in terms of toString
        // TODO: consider the exceptions.
        /*
        * Since we want to pretty print the matrix into the file
        * we use spacing to keep track of how many spaces we need 
        * to pad
        */
        String counts;
        int spacing;
        this.escapeVals = this.escapes();

        /*
        * maxSpacing is the maximum number of spaces a number can have
        * it's bounded by maxIters and it takes into account how
        * many digits a number has (taking the log10 of a number and ceiling it).
        */
        int maxSpacing;
        if (this.maxIters > 1){
            maxSpacing = (int)Math.floor(Math.log10(this.maxIters));
        }
        else {
            maxSpacing = 0;
        }
        
        try {
        FileWriter f = new FileWriter(filename);

        /* Writing nrows ncols maxIter */
        f.write(String.valueOf(this.nrows) + " " + String.valueOf(this.ncols) +
                 " " +String.valueOf(this.maxIters) + '\n');

        /* Writing lowRealVal highRealVal lowImaginaryVal highImaginaryVal */
        f.write(String.valueOf(this.low.r) + " " + String.valueOf(this.high.r) +
                " " + String.valueOf(this.low.i) + " " +String.valueOf(this.high.i) + '\n');

        /* Writing realC imaginaryC */
        f.write(String.valueOf(this.c.r) + " " + String.valueOf(this.c.i) + '\n');

        /* Writing <blank line> */
        f.write('\n');

        /* 
        * Writing the matrix, note that the number of spaces
        * needed in between each number is: |digits of p - maxSpacing| + 3
        */
        for (int i=0; i<this.nrows;i++){
            for (int j=0; j<this.ncols; j++){
                int p = this.escapeVals[i][j];
                if (p >1){
                    spacing = maxSpacing - (int)Math.floor(Math.log10(p));                    
                }
                else {
                    spacing = maxSpacing;
                }

                if (j==0) {
                    counts = " ".repeat(spacing) + String.valueOf(p);
                } else {
                    counts =  " ".repeat(spacing + 3 ) + String.valueOf(p);
                }
                f.write(counts);
            }
            f.write('\n');
        }
        f.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
    * Given a Factor, stay centered on the same spot but scale it
    * by a factor so that each dimesion sees 1/factor of its 
    * previous range.
    * @param factor Zooming factor.
    */
    public void zoom(double factor){
        Complex a = new Complex(1.0/factor,0.0);
        /* It should suffice to update the dimensions. */
        this.updateDimensions(Complex.mul(a,this.low),
                              Complex.mul(a,this.high));
    }

    /**
    * Straight-up reset low and high, and recalculate other
    * stale state (escape counts) as necessary.
    * @param low  lower left coordinate.
    * @param high upper right coordinate
    */
    public void updateDimensions(Complex low, Complex high){
        this.low = low;
        this.high = high;
        this.escapeVals = this.escapes();
    }
    
}

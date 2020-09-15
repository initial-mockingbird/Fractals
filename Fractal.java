package Fractals;
import java.io.*;
import java.lang.Math;


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
        * escape[i][j] = escape[0][0] + (i)*(x/nrows) + (j)*(y/ncols) (1)
        *
        * Holds (the proof is trivial), therefore we declare:
        *
        * diff = x + yi
        * stepRow = x/nrows
        * stepCol = y/ncolsin order to take advantage of it.
        *
        * in order to implement (1)
        */
        Complex diff    = new Complex (Math.abs(low.r-high.r), Math.abs(low.i-high.i));
        Complex stepCol = new Complex (0.0, diff.i/this.ncols);
        Complex stepRow = new Complex (diff.r/this.nrows, 0.0);
        Complex p       = new Complex (0.0, 0.0);

        /*
        * This is just the implementation of (1)
        */
        for (int i=0; i< this.nrows.r;i++){
            p = p.add(stepRow);
            for (int j=0; j<this.ncols;j++){
                p = p.add(stepCol);
                escps[i,j] = this.escapeCount(low.add(p));
            }
            p = new Complex(p.r,0.0);
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
    public void write(String filename){
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
        

        FileWriter f = new FileWriter(filename);

        /* Writing nrows ncols maxIter */
        f.write(String.valueOf(this.nrows) + String.valueOf(this.ncols) +
                String.valueOf(this.maxIters) + '\n');

        /* Writing lowRealVal highRealVal lowImaginaryVal highImaginaryVal */
        f.write(String.valueOf(this.low.r) + String.valueOf(this.high.r) +
                String.valueOf(this.low.i) + String.valueOf(this.high.i) + '\n');

        /* Writing realC imaginaryC */
        f.write(String.valueOf(this.c.r) + String.valueOf(this.c.i) + '\n');

        /* Writing <blank line> */
        f.write('\n');

        /* 
        * Writing the matrix, note that the number of spaces
        * need in between each number is: |digits of p - maxSpacing| + 3
        */
        for (int i=0; i<this.nrows;i++){
            for (int j=0; j<this.ncols; j++){
                int p = this.escapeVals[i][j];
                if (p >1){
                    spacing = maxSpacing - (int)Math.floor(Math.log10(p));                    
                }
                else {
                    spacing = 0
                }

                counts = ' '.repeat(spacing) + String.valueOf(p) + ' '.repeat(3);
                f.write(counts);
            }
            f.write('\n');
        }
        
        f.close();
    }

    public void zoom(double factor){
        // TODO
        return
    }

    public void updateDimensions(Complex low, Complex high){
        this.low = low;
        this.high = high;
        this.escapeVals = this.escapes();
    }
    
}

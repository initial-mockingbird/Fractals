package fractals;
import java.lang.Math;

/**
* Creates complex numbers of the form a+bi, and provides
* basic functionality such as addition, and multiplication 
*/
public class Complex {
    /**
    * r: real part of the complex number.
    * i: imaginary part of the complex number
    */
    public double r;
    public double i;

    /**
    * Creates a Complex object from two real numbers.
    *
    * @param r   The real part of the complex number.
    * @param i   The imaginary part of the complex number.
    */
    public Complex(double r, double i){
        this.r = r;
        this.i = i;
    }

    /**
    * Addition of two complex numbers.
    *
    * @param a   First member of the addition: a+bi.
    * @param b   Second member of the addittion: c+di.
    * @return    The trivial addition: (a+c) + (b+d)i
    */
    public static Complex add(Complex a, Complex b){
        return new Complex( a.r+b.r, a.i+b.i);
    }

    /**
    * Substraction of two complex numbers.
    *
    * @param a   First member of the substraction: a+bi.
    * @param b   Second member of the substraction: c+di.
    * @return    The trivial substraction: (a-c) + (b-d)i
    */
    public static Complex sub(Complex a, Complex b){
        return new Complex( a.r- b.r, a.i-b.i);
    }

    /**
    * Multiplication of two complex numbers.
    *
    * @param a   First member of the multiplication: a+bi.
    * @param b   Second member of the multiplication: c+di.
    * @return    The trivial multiplication: (a+bi) * (c+di)
    */
    public static Complex mul(Complex a, Complex b){
        return new Complex(a.r*b.r - a.i*b.i, a.i*b.r + a.r*b.i);
    }

    /**
    * Absolute value of a complex number
    *
    * @param c   Complex number which we are going to take the absolute value
    * @return    The absolute value of c, defined as: \( \sqrt{a^2 + b^2} \) where \(a\) and
    *            \(b\) are the real and imaginary part of c.
    */
    public static double abs(Complex c){
        return Math.sqrt( Math.pow(c.r,2) + Math.pow(c.i,2)) ;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String toString(){
        return Double.toString(this.r) + " + " + Double.toString(this.i) + "i";
    }

    /**
    * Creates a new Complex number that shares the same values as the parameter.
    * @return   A copy of the complex number.
    */
    public Complex copy(){
        return new Complex(this.r,this.i);
    }

    /**
    * Test class
    */
    public static void main(String args[]){
        Complex a = new Complex (2,3);
        Complex b = new Complex (-1,2);
        System.out.println("a is: " +a.toString());
        System.out.println("b is: " +b.toString());
        System.out.println("a+b is: " + Complex.add(a,b).toString());
        System.out.println("a-b is: " + Complex.sub(a,b).toString());
        System.out.println("a*b is: " + Complex.mul(a,b).toString());
        System.out.println("|a| is: " + Double.toString(Complex.abs(a)));
    }
}

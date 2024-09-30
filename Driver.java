import java.io.IOException;
public class Driver {
    public static void main(String [] args) {
        
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6, 5, -2};
        int [] ex1 = {1, 3, 2};
        Polynomial p1 = new Polynomial(c1, ex1);
        double [] c2 = {3, -4, 2};
        int [] ex2 = {2, 1, 0};
        Polynomial p2 = new Polynomial(c2, ex2);
        Polynomial s = p1.add(p2);

        // The output should be 2.0x^(1) + 5.0x^(3) + 1.0x^(2) + 2.0x^(0) +
        // or 2x + 5x^3 + x^2 + 2  
        for (int i = 0; i < s.coefficients.length; i++){
            System.out.print(s.coefficients[i] + "x^(" + s.exponents[i] + ") + ");
        }
        
        // Test the multiply method
        double[] c3 = {6.0, -2.0, 4.0, -2.0};
        int[] ex3 = {0, 1, 3, 5};
        Polynomial p3 = new Polynomial(c3, ex3);
        
        double[] c4 = {4, -4, 3};
        int[] ex4 = {2, 3, 1};
        Polynomial p4 = new Polynomial(c4, ex4);

        // The output should be 18.0x^(2) + -32.0x^(3) + 18.0x^(1) + 20.0x^(4) + 16.0x^(5) + -22.0x^(6) + -8.0x^(7) + 8.0x^(8) +
        Polynomial product = p3.multiply(p4);
        System.out.println();
        for (int i = 0; i < product.coefficients.length; i++){
            System.out.print(product.coefficients[i] + "x^(" + product.exponents[i] + ") + ");
        }
        System.out.println();
        // Check for redudant exponents
        for (int i = 0; i < product.exponents.length; i++){
            System.out.print(product.exponents[i]);
        }

        // Example polynomial: 5 - 3x^2 + 7x^8
        double[] coeffs = {5, -3, 7};
        int[] expos = {0, 2, 8};
        p = new Polynomial(coeffs, expos);

        // Save the polynomial to a file
        try{
            p.saveToFile("polynomial.txt");
        } catch (IOException e) {   
        }
    }   
}
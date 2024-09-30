// NTS: The exponents array is based on the coefficients so they 
// should be the same length.

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    // Two fields represent the non-zero coefficients and the corresponding 
    // exponents
    double[] coefficients;
    int[] exponents;

    // The polynomial is set to zero when the arrays are empty
    public Polynomial()  {
        coefficients = new double[0];
        exponents = new int[0];
    }

    // Sets the arrays accordingly to given arguments
    public Polynomial(double[] coeffs, int[] expos) {
        coefficients = new double[coeffs.length];
        exponents = new int[expos.length];
        
        for (int i = 0; i < coeffs.length; i++) {
            coefficients[i] = coeffs[i];
            exponents[i] = expos[i];  
        }
    } 

    public Polynomial(File file){
        Scanner input = new Scanner(file);
        String polynomial = input.nextLine();
        input.close();

        polynomial = polynomial.replace(" ", "");
        ArrayList<Double> coeffsList = new ArrayList<>();
        ArrayList<Integer> exposList = new ArrayList<>();

        String[] terms = polynomial.split("(?=[+-])");

        for (String term: terms){
            double coeff;
            int expo;
            
            if (term.contains("x")){
                String[] parts = term.split("x\\^?");
                
                if (parts[0].equals("") || parts[0].equals("+")) {
                    coeff = 1.0;
                } else if (parts[0].equals("-")) {
                    coeff = -1.0;
                } else{
                    coeff = Double.parseDouble(parts[0]);
                }

                if (parts.length > 1 && !parts[1].isEmpty()) {
                    expo = Integer.parseInt(parts[1]);
                } else {
                    expo = 1;  
                }
            }
            else {
                // If there's no "x", it's a constant term with exponent 0
                coeff = Double.parseDouble(term);
                expo = 0;
            }
            coeffsList.add(coeff);
            exposList.add(expo);
        }
        coefficients = new double[coeffsList.size()];
        exponents = new int[exposList.size()];
        for (int i = 0; i < coeffsList.size(); i++) {
            coefficients[i] = coeffsList.get(i);
            exponents[i] = exposList.get(i);
        }
    }

    public void saveToFile(String fileName) throws IOException {
        FileWriter output = new FileWriter(fileName);
        
        String polynomialStr = "";
        
        for (int i = 0; i < coefficients.length; i++) {
            double coeff = coefficients[i];
            int expo = exponents[i];

            // Only add non-zero coefficients
            if (coeff != 0){

                // Add "+" for positive coefficients, except for the first term
                if (i >= 1 && coeff > 0){
                    polynomialStr += "+";
                }
                if (expo == 0) {
                    // Constant term
                    polynomialStr += coeff;
                } else if (coeff == 1) {
                    // Coefficient is 1, so just add the x
                    polynomialStr += "x";
                } else if (coeff == -1) {
                    // Coefficient is -1, so just add the x with a minus sign
                    polynomialStr += "-x";
                }
                else {
                    // Non-constant terms
                    polynomialStr += coeff + "x";
                }
                // Add the exponent when it's not 1
                if (expo > 1) {
                    polynomialStr += expo;
                }
            }
        }
        output.write(polynomialStr);
        output.close();
    }

    /// Example:
    /// 6 - 2x + 4x^3 - 2x^5 [6, -2, 4, -2] :: [0, 1, 3, 5]
    /// 3x + 4x^2 - 4x^3 [3, 4, -4] :: [1, 2, 3]
    /// 6 + x + 4x^2 - 2x^5 [6, 1, 4, -2] :: [0, 1, 2, 5]
    public Polynomial add(Polynomial other) {
        ArrayList<Double> sum_coeffs = new ArrayList<>();
        ArrayList<Integer> sum_expos = new ArrayList<>();
        
        // Add from the current polynomial's fields
        for (int i = 0; i < exponents.length; i++) {
            sum_coeffs.add(coefficients[i]);
            sum_expos.add(exponents[i]);
        }
        
        for (int i = 0; i < other.exponents.length; i++) {
            int index = sum_expos.indexOf(other.exponents[i]);

            if (index != -1) {
                // If the exponents are the same, add the coefficients
                double tempCoeff = sum_coeffs.get(index) + other.coefficients[i];
                if (tempCoeff != 0) {
                    sum_coeffs.set(index, tempCoeff);
                } else {
                    sum_coeffs.remove(index);
                    sum_expos.remove(index);
                }
                } else {
                // If the exponent doesn't exist, add it as a new term
                if (other.coefficients[i] != 0) {
                    sum_coeffs.add(other.coefficients[i]);
                    sum_expos.add(other.exponents[i]);
                }
            }
        }
            // Convert arraylists back to arrays:
            double[] result_coeffs = new double[sum_coeffs.size()];
            int[] result_expos = new int[sum_expos.size()];

            for (int i = 0; i < sum_coeffs.size(); i++){
                result_coeffs[i] = sum_coeffs.get(i);
                result_expos[i] = sum_expos.get(i);
            }
        return new Polynomial(result_coeffs, result_expos);
    }
    
    public Polynomial multiply(Polynomial other){
        ArrayList<Double> product_coeffs = new ArrayList<>();
        ArrayList<Integer> product_expos = new ArrayList<>();
        
        // Multiply each term of one polynomial by each term of the other polynomial
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double tempCoeff = coefficients[i] * other.coefficients[j];
                int tempExpo = exponents[i] + other.exponents[j];

                int index = product_expos.indexOf(tempExpo);
                if (index != -1) {
                    double newCoeff = product_coeffs.get(index) + tempCoeff;
                    // If the exponent already exists, add the coefficients
                    if (newCoeff != 0) {
                        product_coeffs.set(index, newCoeff);
                    }
                    else {
                        product_coeffs.remove(index);
                        product_expos.remove(index);
                    }
                } else {
                    // If the exponent doesn't exist, add the new term
                    if (tempCoeff != 0) {
                        product_coeffs.add(tempCoeff);
                        product_expos.add(tempExpo);
                    }
                }
            }
        }
        // Convert arraylists back to arrays:
        double[] result_coeffs = new double[product_coeffs.size()];
        int[] result_expos = new int[product_expos.size()];

        for (int i = 0; i < product_coeffs.size(); i++){
            result_coeffs[i] = product_coeffs.get(i);
            result_expos[i] = product_expos.get(i);
        }

        return new Polynomial(result_coeffs, result_expos);
    }

    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double arg) {
        return evaluate(arg) == 0;
    }
}
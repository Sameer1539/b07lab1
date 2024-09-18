public class Polynomial {
    //i.
    double[] coefficients;

    //ii.
    public Polynomial() {
        coefficients = new double[]{0};
    }

    //iii.
    public Polynomial(double[] args) {
        // Don't set coefficients to args to prevent aliasing
        coefficients = new double[args.length];
        for (int i = 0; i < args.length; i++) {
            coefficients[i] = args[i];  
        }
    } 

    //iv.
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(coefficients.length, other.coefficients.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            if (i < coefficients.length) {
                result[i] += coefficients[i];
            }
            if (i < other.coefficients.length) {
                result[i] += other.coefficients[i];
            }
        }
        return new Polynomial(result);
    }

    //v.
    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    //vi.
    public boolean hasRoot(double arg) {
        return evaluate(arg) == 0;
    }
}

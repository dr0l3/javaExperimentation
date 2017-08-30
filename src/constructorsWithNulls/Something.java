package constructorsWithNulls;

/**
 * Created by g50848 on 28/06/2017.
 */
public class Something {
    private String a;
    private double b;
    private Double c;

    public Something() {
    }

    public Something(String a, double b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Something something = (Something) o;

        if (Double.compare(something.b, b) != 0) return false;
        if (a != null ? !a.equals(something.a) : something.a != null) return false;
        return c != null ? c.equals(something.c) : something.c == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = a != null ? a.hashCode() : 0;
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Something{" +
                "a='" + a + '\'' +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}

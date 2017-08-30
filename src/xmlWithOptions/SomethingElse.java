package xmlWithOptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by g50848 on 28/06/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SomethingElse {
    private String str;
    private double dbl;

    public SomethingElse() {
    }

    public SomethingElse(String str, double dbl) {
        this.str = str;
        this.dbl = dbl;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public double getDbl() {
        return dbl;
    }

    public void setDbl(double dbl) {
        this.dbl = dbl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SomethingElse that = (SomethingElse) o;

        if (Double.compare(that.dbl, dbl) != 0) return false;
        return str != null ? str.equals(that.str) : that.str == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = str != null ? str.hashCode() : 0;
        temp = Double.doubleToLongBits(dbl);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SomethingElse{" +
                "str='" + str + '\'' +
                ", dbl=" + dbl +
                '}';
    }
}

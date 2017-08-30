package xmlWithOptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Optional;

/**
 * Created by g50848 on 28/06/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Something{
    @XmlJavaTypeAdapter(OptionalAdapter.class)
    @XmlElement(nillable = true)
    private Optional<String> str;
    @XmlJavaTypeAdapter(OptionalAdapter.class)
    private Optional<Double> dbl;

    public Something(Optional<String> str, Optional<Double> dbl) {
        this.str = str;
        this.dbl = dbl;
    }

    public Something() {
    }

    public Optional<String> getStr() {
        return str;
    }

    public void setStr(Optional<String> str) {
        this.str = str;
    }

    public Optional<Double> getDbl() {
        return dbl;
    }

    public void setDbl(Optional<Double> dbl) {
        this.dbl = dbl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Something something = (Something) o;

        if (str != null ? !str.equals(something.str) : something.str != null) return false;
        return dbl != null ? dbl.equals(something.dbl) : something.dbl == null;
    }

    @Override
    public int hashCode() {
        int result = str != null ? str.hashCode() : 0;
        result = 31 * result + (dbl != null ? dbl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Something{" +
                "str=" + str +
                ", dbl=" + dbl +
                '}';
    }
}

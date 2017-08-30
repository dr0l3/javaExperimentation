package xmlWithOptions;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Optional;

/**
 * Created by g50848 on 28/06/2017.
 */
public class OptionalAdapter<A> extends XmlAdapter<String, Optional<A>> {

    @Override
    public Optional<A> unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(Optional<A> v) throws Exception {
        return v.map(Object::toString).orElse(null);
    }
}

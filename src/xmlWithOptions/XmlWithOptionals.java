package xmlWithOptions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.Optional;

/**
 * Created by g50848 on 28/06/2017.
 */
public class XmlWithOptionals {
    public static void main(String[] args) throws JAXBException {
        Something s1 = new Something();
        s1.setDbl(Optional.empty());
        s1.setStr(Optional.empty());

        Something s2 = new Something();
        s2.setStr(Optional.of("heheh"));
        s2.setDbl(Optional.of(2.0));

        System.out.println(s1);
        System.out.println(s2);

        JAXBContext jaxbContext = JAXBContext.newInstance(Something.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        jaxbMarshaller.marshal(s1, System.out);
        System.out.println();
        jaxbMarshaller.marshal(s2, System.out);


        System.out.println("--------------------------------------");

        SomethingElse s3 = new SomethingElse();
        System.out.println(s3.getDbl());
//
//        JAXBContext jaxbContext2 = JAXBContext.newInstance(SomethingElse.class);
//        Marshaller jaxbMarshaller2 = jaxbContext.createMarshaller();
//        jaxbMarshaller2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G50848 on 06/10/2016.
 */
public class LambdaExploration {

    public static void main(String[] args) {
        SomeClass ourTestObject = new SomeClass();
        List<SomeSubClass> aList = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            aList.add(new SomeSubClass(i, "hello"));
        }

        SomeClass ourActualTestObject = aList.stream()
                .collect(SomeClass::new, SomeClass::addOne, SomeClass::AllMany);
        System.out.println(ourActualTestObject.toString());
    }

    private static class SomeClass{
        private List<SomeSubClass> listOfSubItems;

        public SomeClass() {
            this.listOfSubItems = new ArrayList<>();
        }

        public void addOne(SomeSubClass item){
            this.listOfSubItems.add(item);
        }

        public void AllMany(SomeClass items){
            this.listOfSubItems.addAll(items.getListOfSubItems());
        }

        public List<SomeSubClass> getListOfSubItems() {
            return listOfSubItems;
        }

        @Override
        public String toString() {
            return "SomeClass{" +
                    "listOfSubItems=" + listOfSubItems +
                    '}';
        }
    }

    private static class SomeSubClass{
        private int someProperty;
        private String someOtherProperty;

        public SomeSubClass(int someProperty, String someOtherProperty) {
            this.someProperty = someProperty;
            this.someOtherProperty = someOtherProperty;
        }

        public int getSomeProperty() {
            return someProperty;
        }

        public String getSomeOtherProperty() {
            return someOtherProperty;
        }

        @Override
        public String toString() {
            return "SomeSubClass{" +
                    "someProperty=" + someProperty +
                    ", someOtherProperty='" + someOtherProperty + '\'' +
                    '}';
        }
    }
}

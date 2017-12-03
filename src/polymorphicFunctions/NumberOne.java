package polymorphicFunctions;

public class NumberOne implements SomeInterface{
    private Integer someValue;

    public NumberOne(Integer someValue) {
        this.someValue = someValue;
    }

    public Integer getInt() {
        return someValue;
    }
}

package constructorsWithNulls;

/**
 * Created by g50848 on 28/06/2017.
 */
public class ContructorsWithNulls {

    //if a field is of a primitive type you cant assign the value to null
    //you can however make a constructor that does not assign anything to the field
    //so you really dont have any guarantees. You are just unable to explicitly create null references yourself...
    //if you use a boxed value like Double then null values are accepter but assigned to a default "null value"
    //in the case of Double that is 0.0
    public static void main(String[] args) {
        Something a = new Something();
        System.out.println(a);
        Something b = new Something("hehe", 2.0, null);
        System.out.println(b);
        Something c = new Something(null, 1.0, null);
        System.out.println(c);

    }
}

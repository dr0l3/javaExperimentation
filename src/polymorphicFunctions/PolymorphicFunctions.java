package polymorphicFunctions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PolymorphicFunctions {
    /**
     * All this mess is an effect of Java bolting generics on
     *
     * The following compiles and executes as one would expect
     *
     *

     trait SomeTrait {
     def getInt: Int
     }
     case class One(v: Int) extends SomeTrait {
     override def getInt: Int = v
     }

     def hello(a: List[SomeTrait]) = a.filter(v =>v.getInt > 0).length

     val s: List[SomeTrait] = List(One(1), One(2))
     val d: List[One] = List(One(1), One(2))

     println(hello(s)) //prints 2
     println(hello(d)) //prints 2
     *
     * But in java A extends B does not imply that List[A] extends List[B]
     * In fact it seems that List[A] in invariant to List[B]
     * This can be remedied by List[? extends B].
     *
     * But then we cant return List[B]. We can return List[? extends B].
     * This however cant be passed to a function expecting List[B]
     * Again explicit casting can then be used. But by then any compiletime verification stops working.
     * And what happens if someone casts to the wrong type? there wont be any squiggles...
     *
     * So this mess comes from "Is a" type inheritance instead of "has a"
     */

    public static void main(String[] args) {
        List<NumberOne> start1= Arrays.asList(new NumberOne(1), new NumberOne(2));
        List<SomeInterface> start2 = Arrays.asList(new NumberOne(1), new NumberOne(2));
//        System.out.println(size(start1));
        System.out.println(size(start2));
        System.out.println(size2(start1));
        System.out.println(size2(start2));
        System.out.println(size3(start1).getClass());
        List<? extends SomeInterface> heheh = size3(start2);
//        size(heheh)
        List<SomeInterface> heheh3 = (List<SomeInterface>) size3(start2);
//        List<NumberOne> heheh2 = size3(start1);
        NumberOne zero = new NumberOne(0);
        SomeInterface a = new NumberOne(0);
        isEvent(zero);
        isEvent(a);
//        List<NumberOne> list2 = filterEven(start2);
//        List<NumberOne> list3 = filterEven(start1);
    }

    public static long size(List<SomeInterface> list){
        return list.stream().filter(v -> v.getInt() > 0).count();
    }

    //The ? means runtime casting
    public static long size2(List<? extends SomeInterface> list){
        return list.stream().filter(v -> v.getInt() > 0).count();
    }

    //The ? means runtime casting
    public static List<? extends SomeInterface> size3(List<? extends SomeInterface> list){
        return list;
    }

    public static boolean isEvent(SomeInterface val){
        return val.getInt() % 2 == 0;
    }

    public static List<SomeInterface> filterEven(List<SomeInterface> list){
        return list.stream().filter(v -> v.getInt() % 2 == 0).collect(Collectors.toList());
    }
}

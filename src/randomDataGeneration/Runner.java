package randomDataGeneration;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Runner {
    private Random random = new Random();

    public static void main(String[] args) throws Exception {
        Runner runner = new Runner();
        Stuff a = runner.createAndFill(Stuff.class);
        System.out.println(a.toString());
    }


    public <T>  T createAndFill(Class<T> clazz) throws Exception {
        T instance = clazz.newInstance();
        for(Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = getRandomValueForField(field);
            field.set(instance, value);
            field.setAccessible(false);
        }
        return instance;
    }

    private Object getRandomValueForField(Field field) throws Exception {
        Class<?> type = field.getType();

        // Note that we must handle the different types here! This is just an
        // example, so this list is not complete! Adapt this to your needs!
        if(type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            return enumValues[random.nextInt(enumValues.length)];
        } else if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if(type.equals(Long.TYPE) || type.equals(Long.class)) {
            return random.nextLong();
        } else if(type.equals(Double.TYPE) || type.equals(Double.class)) {
            return random.nextDouble();
        } else if(type.equals(Float.TYPE) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)){
            return random.nextBoolean();
        } else if(type.equals(String.class)) {
            return java.util.UUID.randomUUID().toString();
        } else if(type.equals(BigInteger.class)){
            return BigInteger.valueOf(random.nextInt());
        }
        return createAndFill(type);
    }
}

import java.io.File;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Created by g50848 on 13/06/2017.
 */
public class DirPrettyPrinter {
    public static void main(String[] args) {
        String dir = ".";
        int initialIndent = 0;
        printDir(dir,initialIndent);
    }

    public static void printDir(String path, int indent){
        File file = new File(path);
        Stream<File> files = Stream.of(file.listFiles()).filter(File::isFile);
        Stream<File> dirs = Stream.of(file.listFiles()).filter(File::isDirectory);
        System.out.println(repeat(indent,"-")+ file.getName());
        files.forEach(f -> System.out.println(repeat(indent+2,"-")+ f.getName()));
        dirs.forEach(d -> printDir(d.getAbsolutePath(),indent +2));
    }

    public static String repeat(int n, String str){
        return String.join("", Collections.nCopies(n,str));
    }
}

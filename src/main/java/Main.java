import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

/**
 * Created by icetsuk on 09.01.17.
 */
public class Main {

    public static void main(String[] args) {

        String code =
                "if(bool success = ! (person.setSalary((5 * 6) / 3) & true)){print('success')};";


        System.out.println(code);
    }
}

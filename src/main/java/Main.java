import java.util.HashMap;

/**
 * Created by icetsuk on 09.01.17.
 */
public class Main {

    private static class OperandInfo {
        public OperandInfo(TypeEnum type) {
            this.type = type;
        }

        public enum TypeEnum {
            UNARY, BINARY, TRINARY
        }

        public TypeEnum type;
    }

    public static class Node {
        public String text;
        public Node child1;
        public Node child2;
    }

    static  HashMap<String,OperandInfo> operands = new HashMap<>();

    static {
        operands.put("!", new OperandInfo(OperandInfo.TypeEnum.UNARY));
        operands.put("=", new OperandInfo(OperandInfo.TypeEnum.BINARY));
        operands.put("*", new OperandInfo(OperandInfo.TypeEnum.BINARY));
        operands.put("/", new OperandInfo(OperandInfo.TypeEnum.BINARY));
        operands.put("&", new OperandInfo(OperandInfo.TypeEnum.BINARY));
        operands.put(".", new OperandInfo(OperandInfo.TypeEnum.BINARY));
    }


    public static Node parse(String code) {

        StringBuilder sb = new StringBuilder();

        String operator = null;
        String arg1 = null;
        String arg2 = null;

        for(int i=0; i<code.length();i++) {

            char c = code.charAt(i);

            if(Character.isAlphabetic(c) || Character.isDigit(c)) {
                sb.append(c);
                continue;
            } else {
                // do something with buffer word


            }

            if(operands.keySet().contains(c+"")) {
                System.out.println(sb);
                sb = new StringBuilder();
                System.out.println("operator: "+ c);
            }

            if(c==' ' || i == code.length()-1) {
                System.out.println(sb);
                sb = new StringBuilder();
            }

        }
    }


    public static void main(String[] args) {

        String code = "bool success=!(person.setSalary((5*6)/3)&true)";

        Node root = parse(code);


    }
}

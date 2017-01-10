import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by icetsuk on 09.01.17.
 */
public class Main {

    private static class OperandInfo {
        public String token;

        public OperandInfo(TypeEnum type, String token) {
            this.type = type;
            this.token = token;
        }

        public enum TypeEnum {
            UNARY, BINARY, OPEN_BRACKET, CLOSE_BRAKET, TYPE, VARIABLE, LITERAL
        }

        public TypeEnum type;

        public boolean isOperator() {
            return (type != TypeEnum.LITERAL && type != TypeEnum.VARIABLE);
        }
    }

    public static class Node {
        public boolean operator;
        public String text;
        public Node child1;
        public Node child2;
        public OperandInfo info;

        public Node(OperandInfo info, String text) {
            this.info = info;
            this.text = text;
        }
    }

    static  HashMap<String,OperandInfo> operands = new HashMap<>();

    static HashSet<String> types = new HashSet<>();

    static {
        operands.put("!", new OperandInfo(OperandInfo.TypeEnum.UNARY,"!"));
        operands.put("=", new OperandInfo(OperandInfo.TypeEnum.BINARY,"="));
        operands.put("*", new OperandInfo(OperandInfo.TypeEnum.BINARY,"*"));
        operands.put("/", new OperandInfo(OperandInfo.TypeEnum.BINARY,"/"));
        operands.put("+", new OperandInfo(OperandInfo.TypeEnum.BINARY,"+"));
        operands.put("-", new OperandInfo(OperandInfo.TypeEnum.BINARY,"-"));
        operands.put("&", new OperandInfo(OperandInfo.TypeEnum.BINARY,"&"));
        operands.put(".", new OperandInfo(OperandInfo.TypeEnum.BINARY,"."));
        operands.put("(", new OperandInfo(OperandInfo.TypeEnum.OPEN_BRACKET,"("));
        operands.put(")", new OperandInfo(OperandInfo.TypeEnum.CLOSE_BRAKET,")"));
        operands.put("{", new OperandInfo(OperandInfo.TypeEnum.OPEN_BRACKET,"{"));
        operands.put("}", new OperandInfo(OperandInfo.TypeEnum.CLOSE_BRAKET,"}"));

        operands.put("bool", new OperandInfo(OperandInfo.TypeEnum.TYPE,"bool"));
        operands.put("int", new OperandInfo(OperandInfo.TypeEnum.TYPE,"int"));
        operands.put("string", new OperandInfo(OperandInfo.TypeEnum.TYPE,"string"));

    }


    public static LinkedList<Node> parse(String code) {

        LinkedList<Node> nlist = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        int index = -1;
        for(int i=0; i < code.length(); i++) {

            char c = code.charAt(i);

            if(Character.isAlphabetic(c) || Character.isDigit(c) || c=='"') {
                sb.append(c);
                if(i == code.length()-1) {
                    // last token
                    if(operands.containsKey(sb.toString())) {
                        nlist.add(new Node(operands.get(sb.toString()), sb.toString()));
                    } else {
                        nlist.add(new Node(new OperandInfo(OperandInfo.TypeEnum.LITERAL, sb.toString()), sb.toString()));
                    }
                }
                continue;
            }

            if(sb.length()>0) {
                if(operands.containsKey(sb.toString())) {
                    nlist.add(new Node(operands.get(sb.toString()), sb.toString()));
                } else {
                    nlist.add(new Node(new OperandInfo(OperandInfo.TypeEnum.LITERAL, sb.toString()), sb.toString()));
                }

                sb = new StringBuilder();
                index = i;
            }

            if (operands.containsKey(c + "")) {
                nlist.add(new Node(operands.get(c + ""), c + ""));
            } else {
                if (c != ' ') {
                    throw new RuntimeException("Unknown symbol: " + c);
                }
            }
        }

        for(Node n: nlist) {
            System.out.println(n.info.token + ":" + n.info.type);
        }


        return nlist;
    }


    public static List<Node> extractArgument(int pos, List<Node> list) {

        if(list.get(pos).text.equals("(")) {

        }

        return null;
    }

    public static void main(String[] args) {

        String code = "bool success=!(person.setSalary((5*6)/3)&true)+some";
        String code2 = "int x = person.address.index + (3*4*3+1)";

        LinkedList<Node> list = parse(code);

        //test {
        StringBuilder sb = new StringBuilder();
        for(Node n: list) {
            sb.append(n.text);
        }
        System.out.println(sb.toString());
        System.out.println(code);
        // }

        int pos = 0;
        int watchdog = 0;

        while(watchdog < 1000) {


            Node n = list.get(pos);

            // operand operator
            if(n.info.isOperator() && n.info.type == OperandInfo.TypeEnum.UNARY) {

            }


            watchdog++;
        }


    }
}

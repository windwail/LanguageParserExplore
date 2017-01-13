
import java.util.*;

/**
 * Created by icetsuk on 09.01.17.
 */
public class Main {

    public static final String NONE = "Ёж";

    public enum TypeEnum {
        UNARY,
        BINARY,
        OPEN_BRACKET,
        CLOSE_BRAKET,
        TYPE,
        VARIABLE,
        LITERAL,
        STRING,
        NUMBER,
        IF,
        FOR,
        WHILE,
        EOL, // конец строки
        BLANK,
        SUBTOKEN, // в скобках
        COMMA // запятая
    }

    public static class Token {
        public TypeEnum type;
        public String text;

        public Token(String text, TypeEnum type) {
            this.type = type;
            this.text = text;
        }
        public boolean isOperator() {
            return (type != TypeEnum.LITERAL && type != TypeEnum.VARIABLE);
        }
    }

    static HashMap<String, TypeEnum> operands = new HashMap<>();

    static {
        operands.put("!", TypeEnum.UNARY);
        operands.put("=", TypeEnum.BINARY);
        operands.put("==", TypeEnum.BINARY);
        operands.put("!=", TypeEnum.BINARY);
        operands.put(">=", TypeEnum.BINARY);
        operands.put("<=", TypeEnum.BINARY);
        operands.put("*", TypeEnum.BINARY);
        operands.put("/", TypeEnum.BINARY);
        operands.put("+", TypeEnum.BINARY);
        operands.put("-", TypeEnum.BINARY);
        operands.put("&", TypeEnum.BINARY);
        operands.put(".", TypeEnum.BINARY);
        operands.put("(", TypeEnum.OPEN_BRACKET);
        operands.put(")", TypeEnum.CLOSE_BRAKET);
        operands.put("{", TypeEnum.OPEN_BRACKET);
        operands.put("}", TypeEnum.CLOSE_BRAKET);
        operands.put(";", TypeEnum.EOL);
        operands.put(" ", TypeEnum.BLANK);
        operands.put(",", TypeEnum.COMMA);

        operands.put("bool", TypeEnum.TYPE);
        operands.put("int", TypeEnum.TYPE);
        operands.put("string", TypeEnum.TYPE);
    }


    public static class NJNode {

        private final String input;
        private List<NJNode> children;
        private NJNode parent;

        public NJNode(String input) {
            this.input = input;
        }

        private Integer pointer = -1;

        private int watchdog = 1000;

        public void watchdog() {
            if (--watchdog <= 0) throw new RuntimeException("Watchdog error!");
        }

        /**
         *  Выризаем строку до следующего символа с продвижением указателя.
         */
        public String findTillSymbolWithShift(char wantedChar) {
            StringBuilder sb = new StringBuilder();
            char c = input.charAt(pointer);
            pointer++;
            sb.append(c);

            while(pointer<input.length()) {
                c = input.charAt(pointer);
                sb.append(c);
                if(c == wantedChar) {
                    return sb.toString();
                }
                pointer++;
            }

            throw new RuntimeException("Cant find symbol while parsing: "+wantedChar);
        }

        /**
         *  Выризаем строку до следующего символа с продвижением указателя.
         */
        public String findTillNextBracket(String brackets) {
            LinkedList<Character> stack = new LinkedList<>();

            char opening = brackets.charAt(0);
            char closing = brackets.charAt(1);

            StringBuilder sb = new StringBuilder();
            char c = input.charAt(pointer);

            pointer++;
            //sb.append(c);
            stack.add(c);

            while(!stack.isEmpty() && pointer < input.length()) {
                c = input.charAt(pointer);
                if(c == opening) {
                    stack.push(c);
                }
                if(c == closing) {
                    stack.pop();
                }

                // Add only if it is not last closing bracket.
                if(!stack.isEmpty()) {
                    sb.append(c);
                    pointer++;
                }

            }

            return sb.toString();

        }



        /**
         *  Получаем следующий по пути следования токен.
         */
        public Token nextToken() {

            StringBuilder sb = new StringBuilder();

            boolean alphabetic = false;
            boolean numeric = false;

            while (true) {
                watchdog();

                pointer++;

                // End of line.
                char c;
                if(pointer >= input.length()) {
                    c = ';';
                } else {
                    c = input.charAt(pointer);
                }

                // Строка - считываем все до конца и выходим.
                if(c == '"') {
                    return new Token(findTillSymbolWithShift('"'), TypeEnum.STRING);
                }

                // Строка - считываем все до конца и выходим.
                if(c == '(') {
                    String sub = findTillNextBracket("()");
                    //System.out.println("find sub:"+sub);
                    return new Token(sub, TypeEnum.SUBTOKEN);
                }

                // Если символ или цифра - значит читаем дальше.
                if (Character.isAlphabetic(c)) {
                    sb.append(c);
                    continue;
                }

                // Если символ или цифра - значит читаем дальше.
                if (Character.isDigit(c) || (c=='.' && numeric)) {
                    sb.append(c);
                    numeric = true;
                    continue;
                }

                // Если попали сюда - значит получили что-то странно (не строку, не число, не переменную)
                if (sb.length() >= 1) {

                    // Символ оказался терминальным для переменной
                    // Считаем его еще раз.
                    if(c != ' ') {
                        pointer--;
                    }

                    // Является ли то, что мы получили ключевым словом\оператором
                    if (operands.containsKey(sb.toString())) {
                        return new Token(sb.toString(), operands.get(sb.toString()));
                    } else {
                        // Если нет, то значит это число либо строка.
                        if(numeric) {
                            return new Token(sb.toString(), TypeEnum.NUMBER);
                        } else {
                            return new Token(sb.toString(), TypeEnum.STRING);
                        }
                    }
                }

                //Проверяем является ли он двусимволным оператором
                if(pointer+2 <= input.length()) {
                    String op = input.substring(pointer,pointer+2);

                    if(operands.containsKey(op)) {
                        //System.out.println("found op: " + op);
                        pointer ++;
                        return new Token(op, operands.get(op));
                    }
                }

                // Если попали сюда - получается какой-то не алфабетны символ - значит оператор.
                if (operands.containsKey(c + "")) {
                    return new Token(c + "", operands.get(c + ""));
                }

                throw new RuntimeException("Unknown symbol: "+c);
            }
        }

        public void parseBuild() {

            /*
                Общие правила:
                - если начинается с числа/переменной - ищем следующий за ним оператор,
                    действуем в соответствии с оператором (бъем на ноды)
                - если начинается с унарной операции смотрим аргумент операции.
                - если начинается с символьной строки и далее "(" - вызов функции.

                1. Если это терминальное выражение, то это:
                    а) [- | +] число
                    б) переменная
                2. В начале каждого выражения должно идти:
                    a) some - символьное выражение
                        *:
                            someFunc(arg,arg,arg);
                            some = 3;
            */
        }
    }


    public static void test() {
        NJNode n;

        String ops = "a==b<=c>=d";
        n = new NJNode(ops);
        assert (n.nextToken().text.equals("a"));
        assert (n.nextToken().text.equals("=="));
        assert (n.nextToken().text.equals("b"));
        assert (n.nextToken().text.equals("<="));
        assert (n.nextToken().text.equals("c"));
        assert (n.nextToken().text.equals(">="));
        assert (n.nextToken().text.equals("d"));
        assert (n.nextToken().type == TypeEnum.EOL);

        String brackets = "(((1)(2))((3)(4)))";
        n = new NJNode(brackets);
        assert (n.nextToken().text.equals("((1)(2))((3)(4))"));


        String code = "x=(\"comon\")";
        n = new NJNode(code);
        assert (n.nextToken().text.equals("x"));
        assert (n.nextToken().text.equals("="));
        assert (n.nextToken().text.equals("\"comon\""));
        assert (n.nextToken().type == TypeEnum.EOL);

        String code2 = "int x=person.address.index+(3*4*3+1)";
        n = new NJNode(code2);
        assert (n.nextToken().text.equals("int"));
        assert (n.nextToken().text.equals("x"));
        assert (n.nextToken().text.equals("="));
        assert (n.nextToken().text.equals("person"));
        assert (n.nextToken().text.equals("."));
        assert (n.nextToken().text.equals("address"));
        assert (n.nextToken().text.equals("."));
        assert (n.nextToken().text.equals("index"));
        assert (n.nextToken().text.equals("+"));
        assert (n.nextToken().text.equals("3*4*3+1"));
        assert (n.nextToken().type == TypeEnum.EOL);

        String code3 = "(o),(o),()";
        n = new NJNode(code3);
        assert (n.nextToken().text.equals("o"));
        assert (n.nextToken().text.equals(","));
        assert (n.nextToken().text.equals("o"));
        assert (n.nextToken().text.equals(","));
        assert (n.nextToken().text.equals(""));
        assert (n.nextToken().type == TypeEnum.EOL);
    }


    public static void main(String[] args) {
        test();

    }
}


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
        public String findTilSymbolWithShift(char wantedChar) {
            StringBuilder sb = new StringBuilder();
            char c = input.charAt(pointer);
            pointer++;
            sb.append(c);

            while(pointer<input.length()) {
                c = input.charAt(pointer);

                sb.append(c);

                if(c == wantedChar) {
                    return sb.toString();
                } else {
                    pointer++;
                }

            }

            throw new RuntimeException("Cant find symbol while parsing: "+wantedChar);
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
                    return new Token(findTilSymbolWithShift('"'), TypeEnum.STRING);
                }

                // Строка - считываем все до конца и выходим.
                if(c == '(') {
                    return new Token(findTilSymbolWithShift(')'), TypeEnum.SUBTOKEN);
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
                if (sb.length() > 0) {

                    // Пробел нас не интересует в следующих итерациях.
                    if(c == ' ') {
                        pointer++;
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



    public static void main(String[] args) {

        String code = "x=(\"comon\")";
        String code2 = "int x=person.address.index+(3*4*3+1)";
        String code3 = "(o),(o),";

        NJNode n = new NJNode(code);
        Token tk;

        for(int i=0; i < 10; i++) {
            System.out.println(n.nextToken().text);
        }

        //while( true) {
           // n.watchdog();
            tk = n.nextToken();
           // if(tk.type == TypeEnum.EOL) { break; };
        //}

    }
}

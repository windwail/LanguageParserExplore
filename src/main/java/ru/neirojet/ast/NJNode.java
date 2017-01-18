package ru.neirojet.ast;

import ru.neirojet.operators.Operator;
import ru.neirojet.operators.OperatorService;
import ru.neirojet.operators.RightToLeftOperator;
import ru.neirojet.variables.Variable;
import ru.neirojet.variables.VariableType;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.util.LinkedList;


public class NJNode {

    private Environment env = Environment.instance();

    static OperatorService operatorService = OperatorService.instance();
    private String input;
    private LinkedList<NJNode> children = new LinkedList<>();
    private NJNode parent;
    private Integer pointer = -1;
    private int watchdog = 1000;
    private Variable value;
    private Operator operator;



    LinkedList<Token> tokens = new LinkedList<>();

    public NJNode(String input) {
        this.input = input;
    }


    public NJNode(Token t, NJNode parent) {
        this.parent = parent;
        this.parent.children.addLast(this);
        this.tokens.addLast(t);

        if (tokens.size() == 1) {
            this.input = tokens.get(0).text;
            forceCollectTokens();
        }
    }


    public NJNode(LinkedList<Token> tokens, NJNode parent) {
        this.tokens = tokens;
        this.parent = parent;
        this.parent.children.addLast(this);

        if (tokens.size() == 1) {
            this.input = tokens.get(0).text;
            forceCollectTokens();
        }
    }

    public TokenType getType() {
        if (tokens != null && !tokens.isEmpty()) {
            return tokens.getLast().getType();
        }

        return TokenType.NONE;
    }


    public void mutate(Token input, Operator operator) {
        this.tokens.clear();
        this.input = input.getText();
        this.operator = operator;
        this.tokens.addLast(input);
    }

    public void mutate(LinkedList<Token> input, Operator operator) {
        this.tokens = input;
        this.operator = operator;
        this.input = null;
    }

    public NJNode(String input, NJNode parent) {
        this.input = input;
        this.parent = parent;
        this.parent.children.addLast(this);
    }

    public void watchdog() {
        if (--watchdog <= 0) throw new RuntimeException("Watchdog error!");
    }

    /**
     * Выризаем строку до следующего символа с продвижением указателя.
     */
    public String findTillSymbolWithShift(char wantedChar) {
        StringBuilder sb = new StringBuilder();
        char c = input.charAt(pointer);
        pointer++;
        sb.append(c);

        while (pointer < input.length()) {
            c = input.charAt(pointer);
            sb.append(c);
            if (c == wantedChar) {
                return sb.toString();
            }
            pointer++;
        }

        throw new RuntimeException("Cant find symbol while parsing: " + wantedChar);
    }

    /**
     * Выризаем строку до следующего символа с продвижением указателя.
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

        while (!stack.isEmpty() && pointer < input.length()) {
            c = input.charAt(pointer);
            if (c == opening) {
                stack.push(c);
            }
            if (c == closing) {
                stack.pop();
            }

            // Add only if it is not last closing bracket.
            if (!stack.isEmpty()) {
                sb.append(c);
                pointer++;
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return tokens.toString();
    }

    /**
     * Получаем следующий по пути следования токен.
     */
    public Token nextToken() {

        StringBuilder sb = new StringBuilder();

        boolean alphabetic = false;
        boolean numeric = false;

        while (true) {
            watchdog();

            pointer++;

            if(input == null) {

                System.out.println("Input in null");
            }

            // End of line.
            char c;
            if (pointer >= input.length()) {
                c = ';';
            } else {
                c = input.charAt(pointer);
            }

            // Строка - считываем все до конца и выходим.
            if (c == '"') {
                return new Token(findTillSymbolWithShift('"'), TokenType.STRING);
            }

            // Строка - считываем все до конца и выходим.
            if (c == '\'') {
                return new Token(findTillSymbolWithShift('\''), TokenType.STRING);
            }

            // Если символ или цифра - значит читаем дальше.
            if (Character.isAlphabetic(c) || (alphabetic && Character.isDigit(c))) {
                sb.append(c);
                alphabetic = true;
                continue;
            }

            // Если символ или цифра - значит читаем дальше.
            if (Character.isDigit(c) || (c == '.' && numeric)) {
                sb.append(c);
                numeric = true;
                continue;
            }

            // Если попали сюда - значит получили что-то странно (не строку, не число, не переменную)
            if (sb.length() >= 1) {

                // Символ оказался терминальным для переменной
                // Считаем его еще раз.
                if (c != ' ') {
                    pointer--;
                }

                // Является ли то, что мы получили ключевым словом\оператором
                if (operatorService.isOperator(sb.toString())) {
                    return new Token(operatorService.getOperator(sb.toString()));
                } else {
                    // Если нет, то значит это число либо строка.
                    if (numeric) {
                        return new Token(sb.toString(), TokenType.NUMBER);
                    } else {
                        return new Token(sb.toString(), TokenType.SYMBOLIC);
                    }
                }
            }

            // !!! Все проверки должны идти дальше. Т.к. порядок имеет значение!
            // ========================================================================


            // Строка - считываем все до конца и выходим.
            if (c == '(') {
                String sub = findTillNextBracket("()");
                //System.out.println("find sub:"+sub);

                if (!tokens.isEmpty() && tokens.getLast().type == TokenType.SYMBOLIC) {
                    tokens.getLast().type = TokenType.CALL;
                    return new Token(sub, TokenType.CALLARGUMENTS);
                } else {
                    return new Token(sub, TokenType.SUBTOKEN);
                }

            }

            // До этого ничего не было. Просто пропускаем пробель.
            if (sb.length() == 0 && c == ' ') {
                continue;
            }

            //Проверяем является ли он двусимволным оператором
            if (pointer + 2 <= input.length()) {
                String op = input.substring(pointer, pointer + 2);

                if (operatorService.isOperator(op)) {
                    //System.out.println("found op: " + op);
                    pointer++;
                    return new Token(operatorService.getOperator(op));
                }
            }

            // Если попали сюда - получается какой-то не алфабетны символ - значит оператор.
            if (operatorService.isOperator(c + "")) {
                return new Token(operatorService.getOperator(c + ""));
            }

            throw new RuntimeException("Unknown symbol: " + c);
        }
    }

    public void removeBrackets() {
        pointer = -1;
        String sub = findTillNextBracket("()");
        //System.out.println(pointer);
    }

    public void forceCollectTokens() {
        this.tokens.clear();
        collectTokens();
    }

    public void collectTokens() {

        pointer = -1;

        if (!tokens.isEmpty()) {
            //printTokens("Tokens are NOT empty!");
            return;
        }

        Token t = nextToken();
        do {
            tokens.add(t);
            t = nextToken();
        } while (t.type != TokenType.EOL);

        //printTokens(input);
    }

    public void calculateValue() {

        // Ясень пень сначала делаем это у самых нижних.
        for (NJNode ch : children) {
            ch.calculateValue();
        }


        if (operator != null) {
            value = operator.calculateValue(this);

            if(value == null) {
                System.out.println("Cant calculate value for Node. Operator = "+operator);
                printTokens("Arghhhh!");
                printNodes();
            }
        } else {

            assert (tokens.size() == 1);

            Token t = tokens.get(0);

            switch (t.getType()) {
                case STRING:
                    value = new Variable<>(VariableType.STRING, t.getText(), null);
                    break;
                case NUMBER:
                    System.out.println("NUMBER"+t.getText());
                    value = new Variable<>(VariableType.INTEGER, new BigDecimal(t.getText()), null);
                    break;
                case SYMBOLIC:
                    value = env.getVariable(t.getText());
                    break;
                default:
                    throw new RuntimeException("Wrong type for value node:"+t.getType()+":{"+t.getText()+"}");
            }
        }
    }

    public void printNodes() {
        printNodes(this, 1);
    }

    public static void printNodes(NJNode n, int ident) {
        for (int i = 1; i <= ident; i++) {
            System.out.print("-");
        }


        n.printTokens("");
        for (NJNode node : n.children) {
            printNodes(node, ident + 1);
        }
    }

    public void printTokens(String message) {
        System.out.print(message);
        for (Token tk : tokens) {
            System.out.print("{"+tk.text + "}:" + tk.type + "; ");
        }
        if (operator != null) {
            System.out.print("{" + operator.getClass().getName() + "}");
        }
        System.out.print("\n");
    }

    public void splitTokensByLevel() {

        collectTokens();

        if (tokens.size() == 1) {
            return;
        }

        LinkedList<NJNode> toParse = new LinkedList<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);

            int level = operatorService.detectOpLevel(tokens);

            if (operatorService.inLevel(level, t, tokens)) {
                // !!! Only BINARY operators
                //System.out.println("================");
                //System.out.println("detected leve:"+level);
                //System.out.println(t);

                Operator op = operatorService.getOperator(t, tokens);
                //System.out.println(op.getType());

                if(op instanceof RightToLeftOperator) {
                    // Отматываем все вправо по тому же уровню операторов.
                    for(int j=i+1; j<tokens.size(); j++) {
                        t = tokens.get(j);
                        if(operatorService.inLevel(level, t, tokens)) {
                            i = j;
                        }
                    }

                }

                LinkedList<NJNode> toSubSplit = op.split(i, tokens, this);
                toParse.addAll(toSubSplit);

                break;
            }
            ;
        }

        // run parse in sub noded
        for (NJNode n : toParse) {
            n.splitTokensByLevel();
        }
    }

    public int detectOpLevel() {
        return operatorService.detectOpLevel(tokens);
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public LinkedList<NJNode> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<NJNode> children) {
        this.children = children;
    }

    public NJNode getParent() {
        return parent;
    }

    public void setParent(NJNode parent) {
        this.parent = parent;
    }

    public Integer getPointer() {
        return pointer;
    }

    public void setPointer(Integer pointer) {
        this.pointer = pointer;
    }

    public Variable getValue() {
        return value;
    }

    public void setValue(Variable value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }
}


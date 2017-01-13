/**
 * Created by icetsuk on 13.01.17.
 */
public enum TokenType {
    UNARY,
    BINARY,
    OPEN_BRACKET,
    CLOSE_BRAKET,
    TYPE,
    VARIABLE,
    LITERAL,
    STRING,
    SYMBOLIC,
    NUMBER,
    IF,
    FOR,
    WHILE,
    EOL, // конец строки
    BLANK,
    SUBTOKEN, // в скобках
    COMMA, // запятая
    CALL,
    CALLARGUMENTS
}

package swt.calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum class contains math operations that could be used in SWTCalculator
 * 
 * @author Igor Khlaponin
 */
public enum Operations {
    MULTIPLICATION("*"), 
    DIVISION("/"), 
    SUM("+"), 
    SUBSTRACTION("-");

    private final String literal;

    private static final Map<String, Operations> literals = new HashMap<>();

    static {
        for (Operations typeType : Operations.values()) {
            literals.put(typeType.getLiteral(), typeType);
        }
    }

    private Operations(final String literal) {
        this.literal = literal;
    }

    /**
     * Returns <code>Operations</code> String value
     * 
     * @return literal - operations String value
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns <code>Operations</code> by its literal
     * 
     * @param literal - String value of the Operations
     * @return Operations value
     */
    public static Operations get(String literal) {
        return literals.get(literal);
    }
}

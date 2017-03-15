package swt.calculator;

import java.util.HashMap;
import java.util.Map;

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

    public String getLiteral() {
        return literal;
    }

    public static Operations get(String literal) {
        return literals.get(literal);
    }
}

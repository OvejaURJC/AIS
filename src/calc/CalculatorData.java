package calc;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorData {
	
	// +
	public static final String REG_DIG = "([0-9]+(\\.[0-9]+)?)";
	// + / -
	public static final String REG_DIG_NEG = "((\\-)?[0-9]+(\\.[0-9]+)?)";
	// + / (+) / (-)
	public static final String REG_DIG_PAREN = "(" + REG_DIG + "|" + "\\(" + REG_DIG_NEG + "\\))";
	
	public static final String REG_PARENTESIS =
		"\\("
			+ REG_DIG_PAREN
			+ "([\\+\\-\\*\\/]" + REG_DIG_PAREN + ")+"
		+ "\\)";
	
	public static final String OP_TODO_PRIVATE =
		REG_DIG_PAREN
		+ "(([\\+\\-\\*\\/]" + REG_DIG_PAREN + ")+)";
	
	public static final String OP_MULT = REG_DIG_PAREN + "[\\*]" + REG_DIG_PAREN;
	public static final String OP_DIV = REG_DIG_PAREN +"[\\/]" + REG_DIG_PAREN;
	public static final String OP_RESTA = REG_DIG_PAREN + "[\\-]" + REG_DIG_PAREN;
	public static final String OP_SUMA = REG_DIG_PAREN + "[\\+]" + REG_DIG_PAREN;
	
	
	// Tiene en cuenta parentesis Ej. (-1)
	// Solo opera
	public double op(String str) throws SyntaxException, DivisionByZeroException {
		
		if(str.length() == 0) {
			throw new SyntaxException("Parentesis vacíos");
		} else {
			Pattern p = Pattern.compile(OP_TODO_PRIVATE, Pattern.DOTALL);
			Matcher matcher = p.matcher(str);
			
			// Por si numero solo
			Pattern p2 = Pattern.compile(REG_DIG_NEG + "|" + REG_DIG_PAREN, Pattern.DOTALL);
			Matcher matcher2 = p2.matcher(str);
			
			if(matcher.find() && matcher.group().equals(str)) {
				
				// Multiplicaciones y divisiones
				p = Pattern.compile(OP_MULT + "|" + OP_DIV, Pattern.DOTALL);
				matcher = p.matcher(str);
				while(matcher.find()) {
					String operation = matcher.group();
					double aux = 0;
					if(operation.contains("*")) {
						String[] operandos = operation.replace("(", "").replace(")","").split("\\*");
						
						aux = Double.parseDouble(operandos[0]) * Double.parseDouble(operandos[1]);
					} else if(operation.contains("/")) {
						String[] operandos = operation.replace("(", "").replace(")","").split("\\/");
						
						// Division by zero
						if(Double.parseDouble(operandos[1]) == 0) {
							throw new DivisionByZeroException("Division por cero");
						}
						
						aux = Double.parseDouble(operandos[0]) / Double.parseDouble(operandos[1]);
					}
					
					// Sustituimos
					str = str.replaceFirst(Pattern.quote(operation), String.valueOf(aux));
					matcher = p.matcher(str);
				}
				
				// Suma y resta
				p = Pattern.compile(OP_SUMA + "|" + OP_RESTA, Pattern.DOTALL);
				matcher = p.matcher(str);
				while(matcher.find()) {
					String operation = matcher.group();
					double aux= 0;
					if(operation.contains("+")) {
						String[] operandos = operation.replace("(", "").replace(")","").split("\\+");
						
						aux = Double.parseDouble(operandos[0]) + Double.parseDouble(operandos[1]);
					} else if(operation.contains("-")) {
						String[] operandos = operation.replace("(", "").replace(")","").split("\\-");
						
						aux = Double.parseDouble(operandos[0]) - Double.parseDouble(operandos[1]);
					}
					
					// Sustituimos
					str = str.replaceFirst(Pattern.quote(operation), String.valueOf(aux));
					matcher = p.matcher(str);
				}
				
				return Double.parseDouble(str);
			} else if(matcher2.find() && matcher2.group().equals(str)) {
				// Ej. -1 / 1 / (-1)
				return Double.parseDouble(str.replace("(", "").replace(")",""));
			} else {
				throw new SyntaxException("Error sintactico");
			}
		}
	}
	
	public boolean existenParentesis(String str) {
		return str.contains("(") || str.contains(")"); 
	}
	
	public boolean parentesisBalanceados(String str) {
		int countA = str.length() - str.replace("(", "").length();
		int countB = str.length() - str.replace(")", "").length();
		return (countA == countB);
	}
	
	public boolean existenParentesisAtomicos(String str) {
		Pattern p = Pattern.compile(REG_PARENTESIS, Pattern.DOTALL);
		Matcher matcher = p.matcher(str);
		return matcher.find();
	}
	
	public List<String> cogerParentesisAtomicos(String str) {
		List<String> paren = new ArrayList<String>();
		
		Pattern p = Pattern.compile(REG_PARENTESIS, Pattern.DOTALL);
		Matcher matcher = p.matcher(str);
		while(matcher.find()) {
			paren.add(matcher.group());
		}
		
		return paren;
	}
	
	// Función principal que ejecuta todo
	public double execute(String str) throws SyntaxException, DivisionByZeroException {
		// Quitar espacios
		str = str.replace(" ", "");
		
		// Validacion
		if((str.length() == 0) || !parentesisBalanceados(str)) {
			throw new SyntaxException("Error de sintaxis");
		}
		
		// Buscar parentesis atómicos
		while(existenParentesisAtomicos(str)) {
			List<String> paren = cogerParentesisAtomicos(str);
			List<Double> resParen = new ArrayList<Double>();
			
			// Operar todos los parentesis
			for(int i = 0; i < paren.size(); i++) {
				// Quitar parentesis
				String sinParentesis = paren.get(i).substring(1, paren.get(i).length() - 1);					
				resParen.add(op(sinParentesis));
			}
			
			// Sustituir las soluciones en los prentesis atomicos
			for(int i = 0; i < resParen.size(); i++) {
				// Fix: negative numbers
				if(resParen.get(i) < 0) {
					str = str.replaceFirst(Pattern.quote(paren.get(i)), "(" + String.valueOf(resParen.get(i)) + ")");
				} else {
					str = str.replaceFirst(Pattern.quote(paren.get(i)), String.valueOf(resParen.get(i)));
				}
			}
		}
		
		// Opera lo que queda
		return op(str);
	}
	
}

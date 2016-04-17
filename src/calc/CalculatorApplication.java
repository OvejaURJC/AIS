package calc;

public class CalculatorApplication {

	public CalculatorApplication() {
		// Arrancar Interfaz
		new CalculatorInterface(new CalculatorData());
	}

	public static void main(String[] args) {
		new CalculatorApplication();
	}

}

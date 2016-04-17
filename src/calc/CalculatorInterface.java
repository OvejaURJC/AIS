package calc;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CalculatorInterface extends JFrame {

	private static final long serialVersionUID = 1L;

	public static String APP_TITLE = "Calculadora";
	public static int APP_WIDTH_DEFAULT = 400;
	public static int APP_HEIGHT_DEFAULT = 400;

	// Data
	public CalculatorData data;

	// Interface components
	public Container containerAll;
	public JTextField textField;
	public JLabel labelMessage;
	public HashMap<String, JButton> buttons;

	public CalculatorInterface(CalculatorData data) {

		// Use OS design
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// e.printStackTrace();
		}

		// Set
		this.buttons = new HashMap<String, JButton>();
		this.data = data;

		// Container for all
		containerAll = this.getContentPane();
		containerAll.setLayout(new GridBagLayout());

		// Text Field
		textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setPreferredSize(new Dimension(APP_WIDTH_DEFAULT, 50));
		textField.setText("");
		textField.requestFocus();
		textField.setFont(new Font("SansSerif", Font.PLAIN, 40));
		textField.setEditable(false);
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				pressButton(e.getKeyChar());
				e.consume();
			}
		});

		// GridBag constraints
		GridBagConstraints constraints = new GridBagConstraints();

		// Space between buttons
		constraints.insets = new Insets(3, 3, 3, 3);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(textField, constraints);

		// Add Label Message
		labelMessage = new JLabel("");
		labelMessage.setHorizontalAlignment(JLabel.RIGHT);
		// Set max label size
		FontMetrics fm = labelMessage.getFontMetrics(labelMessage.getFont());
		int w = fm.stringWidth("Sintaxis de la operación no permitida");
		int h = fm.getHeight();
		Dimension size = new Dimension(w, h);
		labelMessage.setMinimumSize(size);
		labelMessage.setPreferredSize(size);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(labelMessage, constraints);

		// Insert buttons
		insertButtons(constraints);

		// Set Listeners
		setButtonListeners();

		// Open app
		this.setTitle(APP_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(APP_WIDTH_DEFAULT, APP_HEIGHT_DEFAULT);
		this.setMinimumSize(new Dimension(APP_WIDTH_DEFAULT, APP_HEIGHT_DEFAULT));
		this.setVisible(true);
	}

	/**
	 * Insert buttons
	 */
	public void insertButtons(GridBagConstraints constraints) {
		Font fontButtons = new Font("SansSerif", Font.BOLD, 18);

		// Reset
		buttons.put("BUTTON_TOOL_RESET", new JButton("C"));
		buttons.get("BUTTON_TOOL_RESET").setFont(fontButtons);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_TOOL_RESET"), constraints);
		
		// Parentesis abierto
		buttons.put("BUTTON_TOOL_PAREN_ABIERTO", new JButton("("));
		buttons.get("BUTTON_TOOL_PAREN_ABIERTO").setFont(fontButtons);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_TOOL_PAREN_ABIERTO"), constraints);
		
		// Parentesis cerrado
		buttons.put("BUTTON_TOOL_PAREN_CERRADO", new JButton(")"));
		buttons.get("BUTTON_TOOL_PAREN_CERRADO").setFont(fontButtons);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_TOOL_PAREN_CERRADO"), constraints);

		// Operation Division
		buttons.put("BUTTON_OP_DIVISION", new JButton("/"));
		buttons.get("BUTTON_OP_DIVISION").setFont(fontButtons);
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_OP_DIVISION"), constraints);

		// Operation Multiply
		buttons.put("BUTTON_OP_MULTIPLY", new JButton("*"));
		buttons.get("BUTTON_OP_MULTIPLY").setFont(fontButtons);
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_OP_MULTIPLY"), constraints);

		// Operation Subtract
		buttons.put("BUTTON_OP_SUBTRACT", new JButton("-"));
		buttons.get("BUTTON_OP_SUBTRACT").setFont(fontButtons);
		constraints.gridx = 3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_OP_SUBTRACT"), constraints);

		// Numbers 7 to 9
		for (int i = 7; i <= 9; i++) {
			buttons.put("BUTTON_" + i, new JButton(String.valueOf(i)));
			buttons.get("BUTTON_" + i).setFont(fontButtons);
			constraints.gridx = i - 7;
			constraints.gridy = 3;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.fill = GridBagConstraints.BOTH;
			containerAll.add(buttons.get("BUTTON_" + i), constraints);
		}

		// Numbers 4 to 6
		for (int i = 4; i <= 6; i++) {
			buttons.put("BUTTON_" + i, new JButton(String.valueOf(i)));
			buttons.get("BUTTON_" + i).setFont(fontButtons);
			constraints.gridx = i - 4;
			constraints.gridy = 4;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.fill = GridBagConstraints.BOTH;
			containerAll.add(buttons.get("BUTTON_" + i), constraints);
		}

		// Operation Sum
		buttons.put("BUTTON_OP_SUM", new JButton("+"));
		buttons.get("BUTTON_OP_SUM").setFont(fontButtons);
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_OP_SUM"), constraints);

		// Numbers 1 to 3
		for (int i = 1; i <= 3; i++) {
			buttons.put("BUTTON_" + i, new JButton(String.valueOf(i)));
			buttons.get("BUTTON_" + i).setFont(fontButtons);
			constraints.gridx = i - 1;
			constraints.gridy = 5;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.fill = GridBagConstraints.BOTH;
			containerAll.add(buttons.get("BUTTON_" + i), constraints);
		}

		// Number 0
		buttons.put("BUTTON_0", new JButton("0"));
		buttons.get("BUTTON_0").setFont(fontButtons);
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_0"), constraints);

		// Dot
		buttons.put("BUTTON_TOOL_DOT", new JButton("."));
		buttons.get("BUTTON_TOOL_DOT").setFont(fontButtons);
		constraints.gridx = 2;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_TOOL_DOT"), constraints);

		// Operation Equals
		buttons.put("BUTTON_TOOL_EQUAL", new JButton("="));
		buttons.get("BUTTON_TOOL_EQUAL").setFont(fontButtons);
		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		containerAll.add(buttons.get("BUTTON_TOOL_EQUAL"), constraints);

		// Remove focus
		for (JButton buttonLocal : buttons.values()) {
			buttonLocal.setFocusable(false);
		}
	}

	/**
	 * Action after click
	 */
	public void setButtonListeners() {
		// Numbers
		for (int i = 0; i <= 9; i++) {
			final int indexLocal = i;
			buttons.get("BUTTON_" + indexLocal).addActionListener(new ActionListener() {
				public int index = indexLocal;

				public void actionPerformed(ActionEvent e) {
					// Reset label
					labelMessage.setText("");
					// Input
					char charInput = buttons.get("BUTTON_" + index).getText().charAt(0);
					// Add to input
					textField.setText(textField.getText() + charInput);
				}
			});
		}

		// Operations
		final String[] opKeys = { "BUTTON_OP_SUM", "BUTTON_OP_SUBTRACT", "BUTTON_OP_MULTIPLY", "BUTTON_OP_DIVISION" };
		for (int i = 0; i <= 3; i++) {
			final int indexLocal = i;
			buttons.get(opKeys[indexLocal]).addActionListener(new ActionListener() {
				public String key = opKeys[indexLocal];

				public void actionPerformed(ActionEvent e) {
					// Reset label
					labelMessage.setText("");
					// Input
					char charInput = buttons.get(key).getText().charAt(0);
					// Add to input
					textField.setText(textField.getText() + charInput);
				}
			});
		}

		// Dot
		buttons.get("BUTTON_TOOL_DOT").addActionListener(new ActionListener() {
			public String key = "BUTTON_TOOL_DOT";

			public void actionPerformed(ActionEvent e) {
				// Input
				char charInput = buttons.get(key).getText().charAt(0);
				// Add to input
				textField.setText(textField.getText() + charInput);
			}
		});

		// Parentesis
		final String[] opParen = { "BUTTON_TOOL_PAREN_ABIERTO", "BUTTON_TOOL_PAREN_CERRADO", };
		for (int i = 0; i <= 1; i++) {
			final int indexLocal = i;
			buttons.get(opParen[indexLocal]).addActionListener(new ActionListener() {
				public String key = opParen[indexLocal];

				public void actionPerformed(ActionEvent e) {
					// Reset label
					labelMessage.setText("");
					// Input
					char charInput = buttons.get(key).getText().charAt(0);
					// Add to input
					textField.setText(textField.getText() + charInput);
				}
			});
		}
		
		// C
		buttons.get("BUTTON_TOOL_RESET").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset label
				labelMessage.setText("");
				// Reset input
				textField.setText("");
			}
		});

		// Equal
		buttons.get("BUTTON_TOOL_EQUAL").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset label
				labelMessage.setText("");
				// Finish operation
				try {
					double result = CalculatorInterface.this.data.execute(textField.getText());
					DecimalFormat df = new DecimalFormat("#.####");
					df.setRoundingMode(RoundingMode.CEILING);
					textField.setText(String.valueOf(df.format(result)));
				} catch(Exception ex) {
					labelMessage.setText("ERROR: " + ex.getMessage());
				}
			}
		});

	}

	/**
	 * Press virtually a button
	 */
	public void pressButton(char buttonChar) {
		if (CalculatorInterface.isNumber(buttonChar)) {
			buttons.get("BUTTON_" + buttonChar).doClick();
		} else if (CalculatorInterface.isOperation(buttonChar)) {
			if (buttonChar == '+') {
				buttons.get("BUTTON_OP_SUM").doClick();
			} else if (buttonChar == '-') {
				buttons.get("BUTTON_OP_SUBTRACT").doClick();
			} else if (buttonChar == '*') {
				buttons.get("BUTTON_OP_MULTIPLY").doClick();
			} else if (buttonChar == '/') {
				buttons.get("BUTTON_OP_DIVISION").doClick();
			}
		} else if (CalculatorInterface.isTool(buttonChar)) {
			if (buttonChar == '.') {
				buttons.get("BUTTON_TOOL_DOT").doClick();
			} else if (buttonChar == 'c' || buttonChar == 'C') {
				buttons.get("BUTTON_TOOL_RESET").doClick();
			} else if (buttonChar == '=') {
				buttons.get("BUTTON_TOOL_EQUAL").doClick();
			} else if(buttonChar == '(') {
				buttons.get("BUTTON_TOOL_PAREN_ABIERTO").doClick();
			} else if(buttonChar == ')') {
				buttons.get("BUTTON_TOOL_PAREN_CERRADO").doClick();
			}
		} else if (CalculatorInterface.isBackSpace(buttonChar)) {
			// Reset label
			labelMessage.setText("");
			if (textField.getText().length() > 0) {
				textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
			}
		} else if (CalculatorInterface.isEnter(buttonChar)) {
			// Same as click "equal"
			buttons.get("BUTTON_TOOL_EQUAL").doClick();
		}

		// Debug
		// System.out.println("Keyboard: " + buttonChar);
	}
	
	public static boolean isNumber(char charInput) {
		return ((charInput >= '0') && (charInput <= '9'));
	}

	public static boolean isOperation(char charInput) {
		return ((charInput == '+') || (charInput == '-') || (charInput == '*') || (charInput == '/'));
	}

	public static boolean isTool(char charInput) {
		return (
				(charInput == '.') || (charInput == 'C') || (charInput == 'c') || (charInput == '=')
				|| (charInput == '(') || (charInput == ')'));
	}

	public static boolean isBackSpace(char charInput) {
		return (charInput == '\b');
	}

	public static boolean isEnter(char charInput) {
		return (charInput == '\n');
	}

}

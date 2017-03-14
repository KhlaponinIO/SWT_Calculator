package swt.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class SWTCalculator {

	private Shell shell;
	private double resultValue;
	private Text result;
	private List history;

	public SWTCalculator(Display display) {
		initUI(display);
	}

	private void initUI(Display display) {
		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.setText("SWT Calculator");
		shell.setMinimumSize(300, 400);

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);

		makeCalculatorTab(tabFolder);
		makeHistoryTab(tabFolder);

		tabFolder.pack();
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void makeCalculatorTab(TabFolder tabFolder) {

		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("Calculator");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		Text textField1 = new Text(composite, SWT.SINGLE | SWT.BORDER);
		textField1.setLayoutData(new GridData());
		textField1.addListener(SWT.Verify, event -> Verifier.verifyDigits(event));

		Combo dropDownList = new Combo(composite, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		dropDownList.add("+");
		dropDownList.add("-");
		dropDownList.add("/");
		dropDownList.add("*");
		dropDownList.select(0);

		Text textField2 = new Text(composite, SWT.SINGLE | SWT.BORDER);
		textField2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		textField2.addListener(SWT.Verify, event -> Verifier.verifyDigits(event));

		item1.setControl(composite);

		Button checkButton = new Button(composite, SWT.CHECK);
		checkButton.setText("Calculate on the fly");
		checkButton.setSelection(false);

		GridData gridData1 = new GridData(SWT.LEFT, SWT.TOP, true, false);
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.horizontalSpan = 2;
		checkButton.setLayoutData(gridData1);

		Button calculateButton = new Button(composite, SWT.PUSH);
		calculateButton.setText("Calculate");

		calculateButton.addListener(SWT.Selection, event -> calculate(textField1, textField2, dropDownList));
		checkButton.addListener(SWT.Selection,
				event -> checkButtonSelected(checkButton, calculateButton, dropDownList, textField1, textField2));

		setResultLabel(composite);
	}

	private void setResultLabel(Composite composite) {
		Label label = new Label(composite, SWT.LEFT);
		label.setText("Result: ");

		result = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		GridData resultGridData = new GridData();
		resultGridData.horizontalAlignment = GridData.FILL;
		resultGridData.horizontalSpan = 2;
		result.setLayoutData(resultGridData);
	}

	private void makeHistoryTab(TabFolder tabFolder) {
		TabItem item2 = new TabItem(tabFolder, SWT.NONE);
		item2.setText("History");
		item2.setToolTipText("contains the list of previous calculations");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 5;
		formLayout.marginWidth = 5;
		formLayout.spacing = 5;
		composite.setLayout(formLayout);

		item2.setControl(composite);

		history = new List(composite, SWT.BORDER);

		FormData listData = new FormData();
		listData.width = 250;
		listData.height = 250;
		listData.left = new FormAttachment(composite, 0);
		listData.top = new FormAttachment(composite, 0);
		listData.right = new FormAttachment(100, 0);
		history.setLayoutData(listData);
	}

	private void calculate(Text textField1, Text textField2, Combo dropDownList) {

		if (Verifier.isDigit(textField1.getText()) && Verifier.isDigit(textField2.getText())) {
			double number1 = Double.parseDouble(textField1.getText());
			double number2 = Double.parseDouble(textField2.getText());
			switch (dropDownList.getText()) {
			case "+": {
				resultValue = number1 + number2;
				printHistory(number1, number2, resultValue, "+");
				break;
			}
			case "-": {
				resultValue = number1 - number2;
				printHistory(number1, number2, resultValue, "-");
				break;
			}
			case "/": {
				resultValue = number1 / number2;
				printHistory(number1, number2, resultValue, "/");
				break;
			}
			case "*": {
				resultValue = number1 * number2;
				printHistory(number1, number2, resultValue, "*");
				break;
			}
			default: {
				System.out.println("No such operation!");
			}
			}

		}
	}

	private void printHistory(double number1, double number2, double resultValue, String operator) {
		result.setText(String.valueOf(resultValue));
		String report = number1 + " " + operator + " " + number2 + " = " + String.format("%.3f", resultValue);
		history.add(report);
	}

	private void checkButtonSelected(Button checkButton, Button calculateButton, Combo dropDownList, Text textField1,
			Text textField2) {
		if (checkButton.getSelection()) {
			calculateButton.setEnabled(false);
			dropDownList.addListener(SWT.Selection, event -> calculate(textField1, textField2, dropDownList));
		} else {
			calculateButton.setEnabled(true);
			// remove on the fly calculation
			if (dropDownList.isListening(SWT.Selection)) {
				dropDownList.removeListener(SWT.Selection, dropDownList.getListeners(SWT.Selection)[0]);
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		SWTCalculator calculator = new SWTCalculator(display);
		display.dispose();
	}

}

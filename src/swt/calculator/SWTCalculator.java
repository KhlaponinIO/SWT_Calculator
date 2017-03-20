package swt.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * This class contains the main logic of the SWTCalculator
 * 
 * @author Igor Khlaponin
 */
public class SWTCalculator {

    private Shell _shell;
    private double _resultValue;
    private Text _result;
    private History _history;
    private Composite _composite;
    private TabFolder _tabFolder;
    private BigDecimal _bigResult;
    private Button _bigDecimalsSwitcher;

    /**
     * Constructs the new instance of this class using <code>Shell</code> parent
     * 
     * @param shell - instance of the <code>Shell</code>
     */
    public SWTCalculator(Shell shell) {
        initUI(shell);
    }

    private void initUI(Shell shell) {
        _shell = shell;
        _shell.setText("SWT Calculator");
        _shell.setMinimumSize(310, 350);

        _shell.addListener(SWT.Resize, event -> resizeTab(event));

        _tabFolder = new TabFolder(_shell, SWT.NONE);

        makeCalculatorTab(_tabFolder);
        _history = new History(_tabFolder);

        _tabFolder.pack();
    }

    private void resizeTab(Event event) {
        _tabFolder.setSize(_shell.getSize().x - 15, _shell.getSize().y - 15);
    }

    private void makeCalculatorTab(TabFolder tabFolder) {

        TabItem calculatorItemTab = new TabItem(tabFolder, SWT.NONE);
        calculatorItemTab.setText("Calculator");

        _composite = new Composite(tabFolder, SWT.NONE);
        GridLayout grid = new GridLayout(3, false);
        _composite.setLayout(grid);

        Text textField1 = new Text(_composite, SWT.SINGLE | SWT.BORDER);
        textField1.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        textField1.addListener(SWT.Verify, event -> Verifier.verifyDigits(event));

        Combo dropDownList = new Combo(_composite, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
        for (Operations operations : Operations.values()) {
            dropDownList.add(operations.getLiteral());
        }
        dropDownList.select(0);
        dropDownList.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

        Text textField2 = new Text(_composite, SWT.SINGLE | SWT.BORDER);
        textField2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        textField2.addListener(SWT.Verify, event -> Verifier.verifyDigits(event));

        calculatorItemTab.setControl(_composite);

        Button checkButton = new Button(_composite, SWT.CHECK);
        checkButton.setText("Calculate on the fly");
        checkButton.setSelection(false);

        GridData gridData1 = new GridData(SWT.LEFT, SWT.TOP, true, false);
        gridData1.horizontalAlignment = GridData.FILL;
        gridData1.horizontalSpan = 2;
        checkButton.setLayoutData(gridData1);

        Button calculateButton = new Button(_composite, SWT.PUSH);
        calculateButton.setText("Calculate");
        calculateButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

        calculateButton.addListener(SWT.Selection, event -> calculate(textField1, textField2, dropDownList));
        checkButton.addListener(SWT.Selection,
                event -> checkButtonSelected(checkButton, calculateButton, dropDownList, textField1, textField2));

        _bigDecimalsSwitcher = new Button(_composite, SWT.CHECK);
        _bigDecimalsSwitcher.setText("Calculate big decimals");
        _bigDecimalsSwitcher.setSelection(false);
        GridData gridData2 = new GridData(SWT.LEFT, SWT.TOP, true, false);
        gridData2.horizontalAlignment = GridData.FILL;
        gridData2.horizontalSpan = 3;
        _bigDecimalsSwitcher.setLayoutData(gridData2);

        setResultLabel(_composite);
    }

    private void setResultLabel(Composite composite) {
        Label label = new Label(composite, SWT.LEFT);
        label.setText("Result: ");

        _result = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
        GridData resultGridData = new GridData();
        resultGridData.horizontalAlignment = GridData.FILL;
        resultGridData.horizontalSpan = 2;
        _result.setLayoutData(resultGridData);
    }

    private void calculate(Text textField1, Text textField2, Combo dropDownList) {

        if (Verifier.isDigit(textField1.getText()) && Verifier.isDigit(textField2.getText())) {
            double number1 = Double.parseDouble(textField1.getText());
            double number2 = Double.parseDouble(textField2.getText());

            if (Verifier.numLenght(number1) > 10 | Verifier.numLenght(number2) > 10
                    | _bigDecimalsSwitcher.getSelection()) {
                calculateBigDecimals(textField1, textField2, dropDownList);
                return;
            }

            switch (Operations.get(dropDownList.getText())) {
            case SUM: {
                _resultValue = number1 + number2;
                setResult(number1, number2, _resultValue, Operations.SUM);
                break;
            }
            case SUBSTRACTION: {
                _resultValue = number1 - number2;
                setResult(number1, number2, _resultValue, Operations.SUBSTRACTION);
                break;
            }
            case DIVISION: {
                _resultValue = number1 / number2;
                setResult(number1, number2, _resultValue, Operations.DIVISION);
                break;
            }
            case MULTIPLICATION: {
                _resultValue = number1 * number2;
                setResult(number1, number2, _resultValue, Operations.MULTIPLICATION);
                break;
            }
            default: {
                System.out.println("No such operation!");
            }
            }

        }
    }

    private void setResult(double number1, double number2, double _resultValue, Operations operator) {
        _result.setText(String.valueOf(_resultValue));
        _history.printHistory(number1, number2, _resultValue, operator);
    }

    private void calculateBigDecimals(Text number1, Text number2, Combo dropDownList) {

        BigDecimal bd1 = new BigDecimal(number1.getText());
        BigDecimal bd2 = new BigDecimal(number2.getText());

        switch (Operations.get(dropDownList.getText())) {
        case SUM: {
            _bigResult = bd1.add(bd2);
            setResult(bd1.doubleValue(), bd2.doubleValue(), _bigResult.doubleValue(), Operations.SUM);
            break;
        }
        case SUBSTRACTION: {
            _bigResult = bd1.subtract(bd2);
            setResult(bd1.doubleValue(), bd2.doubleValue(), _bigResult.doubleValue(), Operations.SUBSTRACTION);
            break;
        }
        case DIVISION: {
            if (bd2.equals(BigDecimal.ZERO)) {
                setResult(bd1.doubleValue(), bd2.doubleValue(),
                        (bd1.signum() == -1) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY,
                        Operations.DIVISION);
                break;
            }
            _bigResult = bd1.divide(bd2, 3, RoundingMode.HALF_UP);
            setResult(bd1.doubleValue(), bd2.doubleValue(), _bigResult.doubleValue(), Operations.DIVISION);
            break;
        }
        case MULTIPLICATION: {
            _bigResult = bd1.multiply(bd2);
            setResult(bd1.doubleValue(), bd2.doubleValue(), _bigResult.doubleValue(), Operations.MULTIPLICATION);
            break;
        }
        default: {
            System.out.println("No such operation!");
        }
        }
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

}

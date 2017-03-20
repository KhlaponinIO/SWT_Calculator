package swt.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * This class is used for creating History tab for <code>SWTCalculator</code> and storing the history of calculations
 * 
 * @author Igor Khlaponin
 */
public class History {

    private List history;

    /**
     * Creates new instance of this class using <code>TabFolder</code> instance It creates new tab in
     * <code>SWTCalculator</code> for showing history of calculations
     * 
     * @param tabFolder - parent TabFolder
     */
    public History(TabFolder tabFolder) {
        makeHistoryTab(tabFolder);
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

        history = new List(composite, SWT.BORDER | SWT.V_SCROLL);

        FormData listData = new FormData();
        listData.width = 250;
        listData.height = 250;
        listData.left = new FormAttachment(composite, 0);
        listData.top = new FormAttachment(composite, 0);
        listData.right = new FormAttachment(100, 0);
        listData.bottom = new FormAttachment(100, -25);
        history.setLayoutData(listData);

        item2.setControl(composite);
    }

    /**
     * This method formats calculation report and adds it to the history list
     * 
     * @param number1 - first number
     * @param number2 - second number
     * @param resultValue - result of calculation
     * @param operator - describes witch calculation have been done
     */
    public void printHistory(double number1, double number2, double resultValue, Operations operator) {
        String report = number1 + " " + operator.getLiteral() + " " + number2 + " = "
                + String.format("%.3f", resultValue);
        history.add(report);
    }
}

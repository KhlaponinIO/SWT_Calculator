package swt.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class History {
	
	private List history;
	
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

		item2.setControl(composite);

		history = new List(composite, SWT.BORDER | SWT.V_SCROLL);

		FormData listData = new FormData();
		listData.width = 250;
		listData.height = 250;
		listData.left = new FormAttachment(composite, 0);
		listData.top = new FormAttachment(composite, 0);
		listData.right = new FormAttachment(100, 0);
		history.setLayoutData(listData);
	}
	
	public void printHistory(double number1, double number2, double resultValue, Operations operator) {
		String report = number1 + " " + operator.getLiteral() + " " + number2 + " = " + String.format("%.3f", resultValue);
		history.add(report);
	}
}

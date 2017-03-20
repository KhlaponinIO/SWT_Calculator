package swt.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Application {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

        SWTCalculator calculator = new SWTCalculator(shell);

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}

package ru.electrictower.orf.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import ru.electrictower.orf.Controller;
import ru.electrictower.orf.beans.Article;

/**
 * @author Aliaksei Boole
 */
public class View
{
    private final Display display = new Display();
    private Controller controller;

    public View(Controller controller)
    {
        this.controller = controller;
    }

    public void start()
    {
        LoginWindow loginWindow = new LoginWindow(display, controller);

        final Shell generalShell = loginWindow.getShell();
        TrayMenu trayMenu = new TrayMenu(generalShell, controller);
        trayMenu.initTray();
        loginWindow.show();
        while (!generalShell.isDisposed())
        {
            if (controller.canShowArticle())
            {
                display.syncExec(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Article hotArticle = controller.getHotArticle();
                        controller.beep();
                        ArticleWindow articleWindow = new ArticleWindow(generalShell, controller);
                        articleWindow.show(hotArticle);
                    }
                });
            }
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        display.dispose();
    }

    public void wake()
    {
        display.wake();
    }
}

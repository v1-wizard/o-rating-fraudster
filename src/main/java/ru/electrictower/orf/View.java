package ru.electrictower.orf;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.ui.ArticleWindow;
import ru.electrictower.orf.ui.LoginWindow;
import ru.electrictower.orf.ui.TrayMenu;

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
        LoginWindow loginWindow = new LoginWindow(display);

        final Shell generalShell = loginWindow.getShell();
        TrayMenu trayMenu = new TrayMenu(generalShell, controller);
        trayMenu.initTray();
        generalShell.pack();
        while (!generalShell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
            if (controller.hasHotArticle())
            {
                Article hotArticle = controller.getHotArticle();
                ArticleWindow articleWindow = new ArticleWindow(generalShell, controller);
                articleWindow.show(hotArticle);
            }
        }
        display.dispose();
    }

}

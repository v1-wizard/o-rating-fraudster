package ru.electrictower.orf.view;

import static org.eclipse.swt.SWT.*;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.model.Model;

import static ru.electrictower.orf.view.DataDictionary.*;

/**
 * @author Aliaksei Boole
 */
public class View
{
    private final Display display = new Display();

    private Model model;

    public View(Model model)
    {
        this.model = model;
    }

    public void start()
    {
        Image icon = createImage(ICON_FILE);
        final Shell generalShell = initGeneralShell(icon);
        initTray(generalShell);
        generalShell.pack();
        while (!generalShell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
            if (model.hasHotArticle())
            {
                Article hotArticle = model.getHotArticle();
                ArticleWindow articleWindow = new ArticleWindow(generalShell);
                articleWindow.show(hotArticle);
            }
        }
        display.dispose();
    }

    private Shell initGeneralShell(final Image icon)
    {
        final Shell shell = new Shell(display);
        shell.setImage(icon);
        return shell;
    }

    private Image createImage(final String imageName)
    {
        return new Image(display, Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(imageName));
    }

    private void initTray(final Shell shell)
    {
        final Tray tray;
        TrayItem item;
        tray = display.getSystemTray();
        if (tray != null)
        {
            item = new TrayItem(tray, NONE);
            item.setToolTipText(PROGRAM_NAME);
            final Menu menu = new Menu(shell, POP_UP);
            MenuItem startRssMenuItem = new MenuItem(menu,PUSH);
            startRssMenuItem.setText(START_RSS);
            startRssMenuItem.addListener(Selection, new Listener()
            {
                @Override
                public void handleEvent(Event event)
                {
                    model.startRssThread();
                }
            });
            MenuItem stopRssMenuItem = new MenuItem(menu, PUSH);
            stopRssMenuItem.setText(STOP_RSS);
            stopRssMenuItem.addListener(Selection, new Listener()
            {
                public void handleEvent(Event event)
                {
                    model.stopRssThread();
                }
            });
            MenuItem exitMenuItem = new MenuItem(menu, PUSH);
            exitMenuItem.setText(TRAY_MENU_EXIT);
            exitMenuItem.addListener(Selection, new Listener()
            {
                public void handleEvent(Event event)
                {
                    model.stopRssThread();
                    tray.dispose();
                    display.dispose();
                }
            });
            item.addListener(MenuDetect, new Listener()
            {
                public void handleEvent(Event event)
                {
                    menu.setVisible(true);
                }
            });
            item.setImage(shell.getImage());
        }
    }


}

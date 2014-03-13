package ru.electrictower.orf.mvc;

import static org.eclipse.swt.SWT.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author Aliaksei Boole
 */
public class View
{
        private final static String PROGRAM_NAME = "Onliner Rating fraudster";
        private final static String ICON_FILE = "icon.png";
        private final static String TRAY_MENU_EXIT = "Exit";
        private final static String TRAY_MENU_OPEN = "Open";

        private final static int X_WINDOW_SIZE = 400;
        private final static int Y_WINDOW_SIZE = 400;

        public void start()
        {
                Display display = new Display();
                Image icon = createImage(display, ICON_FILE);
                final Shell shell = createShell(display, icon);
                shell.pack();
                while (!shell.isDisposed())
                {
                        if (!display.readAndDispatch())
                        {
                                display.sleep();
                        }
                }
                display.dispose();
        }


        private Shell createShell(final Display display, final Image icon)
        {
                Shell shell = new Shell(display);
                shell.setText(PROGRAM_NAME);
                shell.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
                shell.setImage(icon);
                shell.setLayout(createGridLayout());
                initTray(display, shell);
                return shell;
        }

        private GridLayout createGridLayout()
        {
                GridLayout gridLayout = new GridLayout();
                gridLayout.numColumns = 2;
                return gridLayout;
        }

        private Image createImage(final Display display, final String imageName)
        {
                return new Image(display, Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(imageName));
        }

        private void initTray(final Display display, final Shell shell)
        {
                final Tray tray;
                TrayItem item;
                tray = display.getSystemTray();
                if (tray != null)
                {
                        item = new TrayItem(tray, NONE);
                        item.setToolTipText(PROGRAM_NAME);
                        final Menu menu = new Menu(shell, POP_UP);
                        MenuItem openMenuItem = new MenuItem(menu, PUSH);
                        openMenuItem.setText(TRAY_MENU_OPEN);
                        openMenuItem.addListener(Selection, new Listener()
                        {
                                public void handleEvent(Event event)
                                {
                                        shell.open();
                                }
                        });
                        MenuItem exitMenuItem = new MenuItem(menu, PUSH);
                        exitMenuItem.setText(TRAY_MENU_EXIT);
                        exitMenuItem.addListener(SWT.Selection, new Listener()
                        {
                                public void handleEvent(Event event)
                                {
                                        tray.dispose();
                                        display.dispose();
                                }
                        });

                        item.addListener(SWT.MenuDetect, new Listener()
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

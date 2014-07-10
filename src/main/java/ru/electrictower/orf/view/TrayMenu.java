/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package ru.electrictower.orf.view;

import org.eclipse.swt.widgets.*;
import ru.electrictower.orf.Controller;

import static org.eclipse.swt.SWT.*;
import static ru.electrictower.orf.view.DataDictionary.*;

/**
 * @author Aliaksei Boole
 */
public class TrayMenu
{
    private final Shell shell;
    private final Controller controller;

    public TrayMenu(Shell shell, Controller controller)
    {
        this.shell = shell;
        this.controller = controller;
    }

    public void initTray()
    {
        final Tray tray;
        TrayItem item;
        tray = shell.getDisplay().getSystemTray();
        if (tray != null)
        {
            item = new TrayItem(tray, NONE);
            item.setToolTipText(PROGRAM_NAME);
            final Menu menu = new Menu(shell, POP_UP);
            MenuItem startRssMenuItem = new MenuItem(menu, PUSH);
            startRssMenuItem.setText(START_RSS);
            startRssMenuItem.addListener(Selection, new Listener()
            {
                @Override
                public void handleEvent(Event event)
                {
                    controller.startRssThread();
                }
            });
            MenuItem stopRssMenuItem = new MenuItem(menu, PUSH);
            stopRssMenuItem.setText(STOP_RSS);
            stopRssMenuItem.addListener(Selection, new Listener()
            {
                public void handleEvent(Event event)
                {
                    controller.stopRssThread();
                }
            });
            MenuItem exitMenuItem = new MenuItem(menu, PUSH);
            exitMenuItem.setText(TRAY_MENU_EXIT);
            exitMenuItem.addListener(Selection, new Listener()
            {
                public void handleEvent(Event event)
                {
                    controller.stopRssThread();
                    tray.dispose();
                    shell.getDisplay().dispose();
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

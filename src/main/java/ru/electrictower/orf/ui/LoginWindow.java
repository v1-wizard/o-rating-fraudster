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
package ru.electrictower.orf.ui;

import lombok.Getter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import static ru.electrictower.orf.ui.DataDictionary.ICON_FILE;

/**
 * @author Aliaksei Boole
 */
public class LoginWindow
{
    @Getter
    private Shell shell;

    private Label loginLabel;
    private Label passwordLabel;
    private Text login;
    private Text password;
    private Button loginButton;

    private Label errorLabel;


    public LoginWindow(Display display)
    {
        initGeneralShell(display);
    }

    public void show()
    {

    }

    private void initViewElements()
    {
       loginLabel = new Label(shell, 1);
    }

    private void initGeneralShell(Display display)
    {
        shell = new Shell(display);
        Image image = new Image(display, Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(ICON_FILE));
        shell.setImage(image);
    }

}

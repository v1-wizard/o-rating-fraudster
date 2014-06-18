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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import static org.eclipse.swt.SWT.*;
import static ru.electrictower.orf.ui.DataDictionary.*;

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
        initGeneralLayout();
        initViewElements();
    }

    public void show()
    {
        loginLabel.setText(LOGIN_TEXT);
        passwordLabel.setText(PASSWORD_TEXT);
        loginButton.setText(LOGIN_BTN_TEXT);
        shell.pack();
        shell.open();
        shell.forceActive();
    }

    private void initViewElements()
    {

        loginLabel = new Label(shell, NONE);
        login = new Text(shell, BORDER);
        passwordLabel = new Label(shell, NONE);
        password = new Text(shell, PASSWORD | BORDER);
        loginButton = new Button(shell, PUSH);
        loginButton.setLayoutData(rowGridData());
    }

    private GridData rowGridData()
    {
        GridData gridData = new GridData();
        gridData.horizontalSpan = 3;
        gridData.horizontalAlignment = CENTER;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = FILL;
        gridData.grabExcessVerticalSpace = true;
        return gridData;
    }

    private void initGeneralShell(Display display)
    {
        shell = new Shell(display);
        Image image = new Image(display, Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(ICON_FILE));
        shell.setImage(image);
    }

    private void initGeneralLayout()
    {
        GridLayout gridLayout = new GridLayout(2, false);
        shell.setLayout(gridLayout);
    }

}

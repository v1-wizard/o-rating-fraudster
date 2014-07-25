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

import lombok.Getter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.electrictower.orf.Controller;

import static org.eclipse.swt.SWT.*;
import static org.eclipse.swt.layout.GridData.FILL_BOTH;
import static ru.electrictower.orf.view.DataDictionary.*;

/**
 * @author Aliaksei Boole
 */
public class LoginWindow
{
    @Getter
    private Shell shell;
    private Controller controller;

    private Label loginLabel;
    private Label passwordLabel;
    private Text login;
    private Text password;
    private Button loginButton;

    private Label errorLabel;


    public LoginWindow(Display display, Controller controller)
    {
        this.controller = controller;
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
        login = new Text(shell, BORDER | FILL);
        login.setLayoutData(layoutForTextField());
        passwordLabel = new Label(shell, NONE);
        password = new Text(shell, PASSWORD | BORDER | FILL);
        password.setLayoutData(layoutForTextField());
        loginButton = new Button(shell, PUSH);
        loginButton.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                login.setEnabled(false);
                password.setEnabled(false);
                loginButton.setEnabled(false);
                errorLabel.setText(WAIT_MESSAGE);
                shell.layout();
                controller.login(login.getText(), password.getText());
                if (controller.isLogin())
                {
                    shell.setVisible(false);
                    controller.startRssThread();
                }
                else
                {
                    if (login.getText().isEmpty() && password.getText().isEmpty())
                    {
                        errorLabel.setText("Введите логин и пароль.");
                    }
                    else
                    {
                        errorLabel.setText(controller.getLoginError());
                    }
                    login.setEnabled(true);
                    password.setEnabled(true);
                    loginButton.setEnabled(true);
                    shell.layout();
                }

            }
        });
        loginButton.setLayoutData(rowGridData());
        errorLabel = new Label(shell, NONE);
        errorLabel.setLayoutData(rowGridData());
    }

    private GridData rowGridData()
    {
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        gridData.widthHint = 170;
        gridData.horizontalAlignment = CENTER;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = FILL;
        gridData.grabExcessVerticalSpace = true;
        return gridData;
    }

    private GridData layoutForTextField()
    {
        GridData gridData = new GridData(FILL_BOTH);
        gridData.widthHint = 130;
        gridData.horizontalAlignment = FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = FILL;
        gridData.grabExcessVerticalSpace = true;
        return gridData;
    }

    private void initGeneralShell(Display display)
    {
        shell = new Shell(display, TITLE | CLOSE & (~RESIZE));
        shell.setText(PROGRAM_NAME);
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

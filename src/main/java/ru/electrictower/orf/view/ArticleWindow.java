package ru.electrictower.orf.view;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import ru.electrictower.orf.Controller;
import ru.electrictower.orf.beans.Article;

import java.net.MalformedURLException;
import java.net.URL;

import static org.eclipse.swt.SWT.*;
import static org.eclipse.swt.layout.GridData.FILL_BOTH;
import static ru.electrictower.orf.view.DataDictionary.*;

/**
 * @author Aliaksei Boole
 */
public class ArticleWindow
{
    private Shell shell;
    private Controller controller;
    private Article article;

    private Label titleLabel;
    private Label sectionLabel;
    private Label dateLabel;
    private Label descriptionLabel;
    private Label articleImage;
    private Text commentText;
    private Button closeWindowButton;
    private Button sendMessageButton;
    private Label statusLabel;


    public ArticleWindow(Shell parentShell, Controller controller)
    {
        this.controller = controller;
        initShell(parentShell);
        initViewElements();
        initGeneralLayout();
    }

    public void show(Article article)
    {
        titleLabel.setText(article.getTitle());
        dateLabel.setText(article.getPubDate().toString());
        sectionLabel.setText(article.getSection());
        descriptionLabel.setText(article.getDescription());
        try
        {
            articleImage.setImage(ImageDescriptor.createFromURL(new URL(article.getImageUrl())).createImage());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        this.article = article;
        shell.pack();
        shell.open();
        shell.forceActive();
        shell.forceFocus();
    }

    private void initViewElements()
    {
        titleLabel = new Label(shell, NONE);
        titleLabel.setLayoutData(rowGridData());
        sectionLabel = new Label(shell, NONE);
        dateLabel = new Label(shell, NONE);
        dateLabel.setLayoutData(alignmentRightGridData());
        articleImage = new Label(shell, BORDER);
        descriptionLabel = new Label(shell, LEFT | WRAP);
        descriptionLabel.setLayoutData(alignmentRightGridData());
        commentText = new Text(shell, MULTI | BORDER | WRAP | V_SCROLL);
        commentText.setLayoutData(layoutForTextField());
        commentText.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent keyEvent)
            {
                if (keyEvent.character == '\r' && keyEvent.stateMask == SWT.CTRL)
                {
                    sendMSG();
                }
            }
        });
        sendMessageButton = new Button(shell, PUSH);
        sendMessageButton.setLayoutData(new GridData(FILL));
        sendMessageButton.setText(SEND_BUTTON);
        sendMessageButton.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                sendMSG();
            }
        });
        closeWindowButton = new Button(shell, PUSH);
        closeWindowButton.setText(CLOSE_BUTTON);
        closeWindowButton.setLayoutData(new GridData(FILL));
        closeWindowButton.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                shell.dispose();
            }
        });
        statusLabel = new Label(shell, NONE);
        statusLabel.setLayoutData(rowGridData());
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

    private GridData alignmentRightGridData()
    {
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        gridData.horizontalAlignment = RIGHT;
        gridData.widthHint = 300;
        return gridData;
    }

    private GridData layoutForTextField()
    {
        GridData gridData = new GridData(FILL_BOTH);
        gridData.horizontalSpan = 2;
        gridData.verticalSpan = 2;
        gridData.horizontalAlignment = FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = FILL;
        gridData.grabExcessVerticalSpace = true;
        return gridData;
    }

    private void initGeneralLayout()
    {
        GridLayout gridLayout = new GridLayout(3, false);
        shell.setLayout(gridLayout);
    }

    private void initShell(Shell parentShell)
    {
        shell = new Shell(parentShell, SHELL_TRIM & (~RESIZE) & (~MIN) & (~MAX));
        shell.setText(PROGRAM_NAME);
        shell.setImage(parentShell.getImage());
    }

    private void sendMSG()
    {
        sendMessageButton.setEnabled(false);
        commentText.setEnabled(false);
        controller.sendComment(commentText.getText(), article);
        statusLabel.setText(STATUS_SEND);
        shell.layout();
    }

}

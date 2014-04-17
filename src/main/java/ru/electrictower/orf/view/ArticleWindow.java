package ru.electrictower.orf.view;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import ru.electrictower.orf.beans.Article;

import java.net.MalformedURLException;
import java.net.URL;

import static org.eclipse.swt.SWT.*;
import static ru.electrictower.orf.view.DataDictionary.*;

/**
 * @author Aliaksei Boole
 */
public class ArticleWindow
{
    private Shell shell;

    private Label titleLabel;
    private Label sectionLabel;
    private Label dateLabel;
    private Label descriptionLabel;
    private Label articleImage;
    private Text commentText;
    private Button closeWindowButton;
    private Button sendMessageButton;


    public ArticleWindow(Shell parentShell)
    {
        initShell(parentShell);
        initViewElements();
        initGeneralLayout();
    }

    private void initViewElements()
    {
        titleLabel = new Label(shell, NONE);
        titleLabel.setLayoutData(rowGridData());
        sectionLabel = new Label(shell, NONE);
        dateLabel = new Label(shell, NONE);
        dateLabel.setLayoutData(alignmentRightGridData());
        articleImage = new Label(shell, BORDER);
        descriptionLabel = new Label(shell, NONE);
        descriptionLabel.setLayoutData(alignmentRightGridData());
        commentText = new Text(shell, MULTI & BORDER);
        commentText.setLayoutData(layoutForTextField());
        sendMessageButton = new Button(shell, PUSH);
        sendMessageButton.setText(SEND_BUTTON);
        closeWindowButton = new Button(shell, PUSH);
        closeWindowButton.setText(CLOSE_BUTTON);
        closeWindowButton.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                shell.dispose();
            }
        });
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
        shell.pack();
        shell.open();
        shell.forceActive();
    }

    public void close()
    {
        shell.dispose();
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
        return gridData;
    }

    private GridData layoutForTextField()
    {
        GridData gridData = new GridData();
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

}

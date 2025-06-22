package fos.view;

// awt imports
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// swing imports
import javax.swing.*;

// package imports
import fos.data.UserDataHandler;
import fos.service.User;
import fos.service.ValidationUtils;


public class SettingsMenu extends JPanel
{
    // text fields
    private JTextField passScoreField, maxScoreField, maxCredits;


    // object to use the user data
    public static User CURRENT_USER;
    public static final UserDataHandler dataHandler = new UserDataHandler();


    // constructor
    public SettingsMenu()
    {
        CURRENT_USER = dataHandler.getUser(2704, this);
        initializePanel();
    }


    // method to initialize the subject panel
    private void initializePanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // create the panel with the text boxes
        JPanel inputPanel = WindowComponent.setPanel(WindowComponent.BUTTON_BACKGROUND,
                                                        (this.getWidth()/2) - (200/2),
                                                        (this.getHeight()-270)/2,
                                                        200,
                                                        240);

        // create the back button
        JButton backButton = WindowComponent.setButton("Back",
                                                        inputPanel.getX()/4,
                                                        161,
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(backButton,
                                    () ->
                                    {
                                        // refresh the subjects on the subject box
                                        SubjectMenu.dataHandler.loadSubjects();
                                        // switch to the subject menu
                                        WindowComponent.switchPanel(this, Main.subjectMenu);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // title of the pass score text field
        JLabel passTitle = WindowComponent.setText("Passing score",
                                                (inputPanel.getWidth()-130)/2,
                                                20,
                                                130,
                                                22);
        WindowComponent.configureText(passTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(passTitle));

        // create the text field of the passing score
        passScoreField = WindowComponent.setTextField(passTitle.getX(),
                                                    WindowComponent.yNegative(passTitle, 4),
                                                    130,
                                                    30);
        WindowComponent.configureText(passScoreField,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);
        setPassText(String.valueOf(SettingsMenu.CURRENT_USER.getPassScore()));
        passScoreField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double newPass = Double.parseDouble(passScoreField.getText().trim());
                    if (ValidationUtils.isNegative(newPass))
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Passing score cannot be negative.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- negative number -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newPass, CURRENT_USER.getMaxScore()))
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Passing score cannot be higher than max score.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- limit error -----");
                    }
                    else
                    {
                        // set the new passing score in the database
                        dataHandler.updatePassScore(CURRENT_USER.getId(), newPass, inputPanel);
                        // set the new passing score to the user object
                        CURRENT_USER.setPassScore(newPass);
                    }
                }
                catch (NumberFormatException ex)
                {
                    ex.getStackTrace();
                    // set the failed score to the default value
                    setPassText(String.valueOf(CURRENT_USER.getPassScore()));
                }
                super.keyReleased(e);
            }
        });


        // title of the max score text field
        JLabel maxTitle = WindowComponent.setText("Max score",
                                                    (inputPanel.getWidth()-130)/2,
                                                    WindowComponent.yNegative(passScoreField,10),
                                                    130,
                                                    22);
        WindowComponent.configureText(maxTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(maxTitle));

        // create the text box of the subject name
        maxScoreField = WindowComponent.setTextField(passTitle.getX(),
                                                    WindowComponent.yNegative(maxTitle, 4),
                                                    130,
                                                    30);
        WindowComponent.configureText(maxScoreField,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);
        setMaxText(String.valueOf(SettingsMenu.CURRENT_USER.getMaxScore()));
        maxScoreField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double newMax = Double.parseDouble(maxScoreField.getText().trim());
                    if (ValidationUtils.isNegative(newMax))
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max score cannot be negative.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- negative number -----");
                    }
                    else if (newMax < CURRENT_USER.getPassScore())
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max score cannot be lower than passing score.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- limit error -----");
                    }
                    else
                    {
                        // set the new max score in the database
                        dataHandler.updateMaxScore(CURRENT_USER.getId(), newMax, inputPanel);
                        // set the new max to the user object
                        CURRENT_USER.setMaxScore(newMax);
                    }
                }
                catch (NumberFormatException ex)
                {
                    ex.getStackTrace();
                    // set the failed score to the default value
                    setMaxText(String.valueOf(CURRENT_USER.getMaxScore()));
                }
                super.keyReleased(e);
            }
        });

        // title of the max credits text field
        JLabel creditsTitle = WindowComponent.setText("Max credits",
                                                    (inputPanel.getWidth()-130)/2,
                                                    WindowComponent.yNegative(maxScoreField,10),
                                                    130,
                                                    22);
        WindowComponent.configureText(creditsTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(creditsTitle));

        // create the text box of the max credits
        maxCredits = WindowComponent.setTextField(passTitle.getX(),
                WindowComponent.yNegative(creditsTitle, 4),
                130,
                30);
        WindowComponent.configureText(maxCredits,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);
        setCreditsText(String.valueOf(CURRENT_USER.getMaxCredits()));
        maxCredits.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    int newCredits = Integer.parseInt(maxScoreField.getText().trim());
                    if (ValidationUtils.isNegative(newCredits))
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max credits cannot be negative.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- negative number -----");
                    }
                    else
                    {
                        // set the new credits in the database
                        dataHandler.updateMaxCredits(CURRENT_USER.getId(), newCredits, inputPanel);
                        // set the new score to the grade
                        CURRENT_USER.setMaxCredits(newCredits);
                    }
                }
                catch (NumberFormatException ex)
                {
                    ex.getStackTrace();
                    // set the failed score to the default value
                    setCreditsText(String.valueOf(CURRENT_USER.getMaxCredits()));
                }
                super.keyReleased(e);
            }
        });


        // add the components to the container
        add(inputPanel);
        add(backButton);


        // add the components to the panel
        inputPanel.add(passTitle);
        inputPanel.add(passScoreField);
        inputPanel.add(maxTitle);
        inputPanel.add(maxScoreField);
        inputPanel.add(creditsTitle);
        inputPanel.add(maxCredits);
    }


    // setters and getters
    public void setPassText(String score)
    {
        this.passScoreField.setText(score);
    }
    public String getPassText()
    {
        return passScoreField.getText().trim();
    }

    public void setMaxText(String percentage)
    {
        maxScoreField.setText(percentage);
    }
    public String getMaxText()
    {
        return maxScoreField.getText().trim();
    }

    public void setCreditsText(String credits)
    {
        this.maxCredits.setText(credits);
    }
    public String getCreditsText()
    {
        return maxCredits.getText().trim();
    }
}
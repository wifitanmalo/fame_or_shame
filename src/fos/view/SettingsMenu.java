package fos.view;

// awt imports
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// package imports
import fos.service.User;
import fos.service.ValidationUtils;
import fos.data.SubjectDataHandler;
import fos.data.UserDataHandler;


public class SettingsMenu extends JPanel
{
    // text fields
    private JTextField passScoreField, maxScoreField, maxCreditsField;


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
                                                        (inputPanel.getX()-78)/2,
                                                        (Main.WINDOW_HEIGHT-50)/2,
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
                        throw new NumberFormatException("----- negative score -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newPass, CURRENT_USER.getMaxScore()))
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Passing score cannot exceed max score.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- passing score exceeds max -----");
                    }
                    else
                    {
                        // keep/change the field font color to dark gray if the value is valid
                        passScoreField.setForeground(WindowComponent.BUTTON_BACKGROUND);

                        // set the new passing score in the database
                        dataHandler.updatePassScore(CURRENT_USER.getId(), newPass, inputPanel);
                    }
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();

                    // change the field font color to red if the value is invalid
                    passScoreField.setForeground(Color.decode("#FF746C"));
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
                        throw new NumberFormatException("----- negative score -----");
                    }
                    else if (newMax < CURRENT_USER.getPassScore())
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max score cannot be less than the passing score.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- max less than passing score -----");
                    }
                    else if (newMax > 100)
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max score cannot exceed 100.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- max score exceeds 100 -----");
                    }

                    // keep/change the field font color to dark gray if the value is valid
                    maxScoreField.setForeground(WindowComponent.BUTTON_BACKGROUND);

                    // set the new max score in the database
                    dataHandler.updateMaxScore(CURRENT_USER.getId(), newMax, inputPanel);
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();

                    // change the field font color to red if the value is invalid
                    maxScoreField.setForeground(Color.decode("#FF746C"));
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
        maxCreditsField = WindowComponent.setTextField(passTitle.getX(),
                                                        WindowComponent.yNegative(creditsTitle, 4),
                                                        130,
                                                        30);
        WindowComponent.configureText(maxCreditsField,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);
        setCreditsText(String.valueOf(CURRENT_USER.getMaxCredits()));
        maxCreditsField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    int newCredits = Integer.parseInt(maxCreditsField.getText().trim());
                    if (newCredits <= 0)
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max credits must be greater than 0.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- credits less than/equal to zero -----");
                    }
                    else if (newCredits < SubjectDataHandler.SIGNED_CREDITS)
                    {
                        WindowComponent.messageBox(inputPanel,
                                                String.format("Max credits cannot be less than signed credits (%d).",
                                                                SubjectDataHandler.SIGNED_CREDITS),
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- max credits less than signed -----");
                    }
                    else if (newCredits > 100)
                    {
                        WindowComponent.messageBox(inputPanel,
                                                "Max credits cannot exceed 100.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- max credits exceeds 100 -----");
                    }

                    // keep/change the field font color to dark gray if the value is valid
                    maxCreditsField.setForeground(WindowComponent.BUTTON_BACKGROUND);

                    // set the new credits in the database
                    dataHandler.updateMaxCredits(CURRENT_USER.getId(), newCredits, inputPanel);
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();

                    // change the field font color to red if the value is invalid
                    maxCreditsField.setForeground(Color.decode("#FF746C"));
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
        inputPanel.add(maxCreditsField);
    }


    // method to set the user values to the fields
    public void setUserValues(User user, Color color)
    {
        // get the values from the current user
        double passScore = user.getPassScore();
        double maxScore = user.getMaxScore();
        int maxCredits = user.getMaxCredits();

        // set the values in the respective text field
        passScoreField.setText(String.valueOf(passScore));
        maxScoreField.setText(String.valueOf(maxScore));
        maxCreditsField.setText(String.valueOf(maxCredits));

        // set the fields font color
        passScoreField.setForeground(color);
        maxScoreField.setForeground(color);
        maxCreditsField.setForeground(color);
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
        this.maxCreditsField.setText(credits);
    }
    public String getCreditsText()
    {
        return maxCreditsField.getText().trim();
    }
}
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class SubjectMenu extends JPanel
{
    // manager object to use the subjects
    public static final Management manager = new Management();

    // subject panels
    private static final JPanel subject_box = new JPanel();
    private final CreateSubject create_subject;

    // container object
    private final Container container;

    // constructor
    public SubjectMenu()
    {
        this.create_subject = new CreateSubject();
        create_subject.setVisible(false);
        this.container = WindowComponent.get_container();
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        // create the main panel
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0, 0, Main.width, Main.height);

        // add a vertical scroll bar on the subject box
        JScrollPane scroll_subject = WindowComponent.set_scroll_bar(subject_box,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // subjects title
        JLabel subjects_title = WindowComponent.set_text("Subjects",
                                                        scroll_subject.getX(),
                                                        scroll_subject.getY()-32,
                                                        260,
                                                        26);
        WindowComponent.configure_text(subjects_title,
                                        WindowComponent.default_pressed_button_background,
                                        3,
                                        WindowComponent.get_height(subjects_title));

        // button to create a subject
        JButton add_button = WindowComponent.set_button("+",
                                                        50,
                                                        scroll_subject.getY() + ((scroll_subject.getHeight()-50)/2),
                                                        50,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_text(add_button,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        18);
        WindowComponent.button_event(add_button,
                                    () ->
                                    {
                                        if(ValidationUtils.equals(Management.signed_credits, Management.max_credits))
                                        {
                                            WindowComponent.message_box(this,
                                                                        "You have already reached the credit limit.",
                                                                        "Number limit",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                        else
                                        {
                                            WindowComponent.switch_panel(this, create_subject);
                                        }
                                    },
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components to the panel
        add(add_button);
        add(subjects_title);
        add(scroll_subject);

        // add the panels to the container
        container.add(this);
        container.add(create_subject);

        // load the saved subjects
        load_subjects();

        // create the panels of the subjects in the subject box
        refresh_subjects();

        // reload the panel to show the changes
        WindowComponent.reload(this);
    }

    // method to load the grades from the subjects file
    public void load_subjects()
    {
        try
        {
            String line;
            BufferedReader read = new BufferedReader(new FileReader("subjects.txt"));
            while ((line = read.readLine()) != null)
            {
                String[] data = line.split(",");

                // subject values
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int credits = Integer.parseInt(data[2]);
                double score = Double.parseDouble(data[3]);
                double evaluated = Double.parseDouble(data[4]);

                // create the subject with the line values
                Subject subject = new Subject(id, name, credits);
                subject.set_total_score(score);
                subject.set_total_evaluated(evaluated);

                // create the subject in the subject panel/list
                manager.create_subject(subject);
            }
            read.close();
        }
        catch (IOException error)
        {
            WindowComponent.message_box(this,
                                        "Error while reading the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a subject in the file
    public void create_subject(Subject subject)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("subjects.txt", true));
            String data = subject.get_id()
                        + ","
                        + subject.get_name()
                        + ","
                        + subject.get_credits()
                        + ",0.0,0.0";
            writer.write(data);
            writer.newLine();
            writer.close();
        }
        catch (IOException e)
        {
            WindowComponent.message_box(this,
                                        "Error while writing the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to modify a subject in the file
    public void update_subject(Subject subject,
                               double new_score,
                               double new_evaluated)
    {
        // file names
        File file = new File("subjects.txt");
        File temporal = new File("temporal.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporal)))
        {
            String current_line;
            while ((current_line = reader.readLine()) != null)
            {
                // split the line in commas
                String[] data = current_line.split(",");
                int current_id = Integer.parseInt(data[0].trim());

                // get the score/evaluated of the line if the id matches
                if (current_id == subject.get_id())
                {
                    data[3] = String.valueOf(new_score);
                    data[4] = String.valueOf(new_evaluated);
                    current_line = String.join(",", data);
                }
                writer.write(current_line);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(this,
                                        "Error while writing the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }

        // replace the original file with the temporal one
        if (!file.delete() || !temporal.renameTo(file))
        {
            WindowComponent.message_box(this,
                                        "Error while updating the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to refresh the grades on the subject box
    public void refresh_subjects()
    {
        subject_box.removeAll();
        for(Subject subject : manager.get_subjects_list())
        {
            // create the panel in the subject box
            SubjectPanel current_panel = new SubjectPanel(subject);
            current_panel.set_score_label(subject.get_total_score());
            current_panel.set_evaluated_label(subject.get_total_evaluated());
        }
        // reload the panel to show the changes
        WindowComponent.reload(subject_box);
    }

    // method to cut the first two decimals of a number
    public static double two_decimals(double number)
    {
        return (int)(number * 100) / 100.0;
    }

    // method to get the subject box
    public static JPanel get_subject_box()
    {
        return subject_box;
    }
}

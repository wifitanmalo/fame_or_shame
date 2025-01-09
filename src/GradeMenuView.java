import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeMenuView extends JFrame
{
    private Scanner input;
    private Subject subject;

    public GradeMenuView(Subject english, Scanner input)
    {
        this.subject = english;
        this.input = input;
        JPanel panel = new JPanel();
        panel.setVisible(true);

        panel.setLayout(null);
        this.getContentPane().add(panel);
        panel.setBackground(Color.lightGray);

        grade_menu();
    }

    // method to show the grade menu
    public void grade_menu()
    {
        System.out.println("\n***** GRADE MENU *****");
        System.out.println("1. Add a grade");
        System.out.println("2. Delete a grade");
        System.out.println("3. Calculate the total");
        try
        {
            System.out.print("Select your option: ");
            int option = input.nextInt();
            switch(option)
            {
                case 1:
                {
                    if(!ValidationUtils.exceeds_limit(subject.get_evaluated(), 99.9))
                    {
                        AddGradeView add_grade = new AddGradeView(subject, input);
                    }
                    else
                    {
                        System.out.println("----- The evaluated percentage is already: 100% -----");
                    }
                } break;
                case 2:
                {
                    if(!subject.get_grades_list().isEmpty())
                    {
                        DeleteGradeView delete_grade = new DeleteGradeView(subject, input);
                    }
                    else
                    {
                        System.out.println("----- ADD a grade first -----");
                    }
                } break;
                case 3: subject.calculate_total(); System.out.println("- Total score: " + subject.get_total()); break;
                default: System.out.println("----- invalid option -----"); break;
            }
        }
        catch(InputMismatchException error)
        {
            System.out.println("----- only POSITIVE numbers -----");
            input.next();
        }
    }
}

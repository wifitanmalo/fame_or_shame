import java.util.InputMismatchException;
import java.util.Scanner;

public class AddGradeView
{
    private Scanner input;
    private Subject subject;

    // constructor
    public AddGradeView(Subject subject, Scanner input)
    {
        this.subject = subject;
        this.input = input;
    }

    /*
    // method to show the add menu
    public void add_menu()
    {
        while(true)
        {
            try
            {
                System.out.print("\nEnter the score: ");
                double score = input.nextDouble();
                if(ValidationUtils.is_negative(score))
                {
                    throw new InputMismatchException("score < 0");
                }
                if(ValidationUtils.exceeds_limit(score, Subject.max_score))
                {
                    throw new NumberFormatException("score > max_score");
                }

                System.out.print("- Enter the percentage: ");
                double percentage = input.nextDouble();
                double current_evaluated = subject.get_evaluated();
                if(ValidationUtils.is_negative(percentage))
                {
                    throw new InputMismatchException("percentage < 0");
                }
                if(ValidationUtils.exceeds_limit(current_evaluated+percentage, 100))
                {
                    throw new NumberFormatException("evaluated > 100");
                }

                subject.add_grade(score, percentage);
                subject.increase_evaluated(percentage);
                System.out.println("----- grade ADDED successfully -----");
                break;

            }
            catch(InputMismatchException error)
            {
                System.out.println("----- only POSITIVE numbers -----");
                input.next();
            }
            catch(NumberFormatException error)
            {
                System.out.println("----- The total of the percentages is higher than 100 -----");
            }
        }
    }

     */
}

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class GradeMenuView
{
    Subject english = new Subject(204028,
            "English IV",
            2);
    Scanner input = new Scanner(System.in).useLocale(Locale.US);

    // method to show the grade menu
    public void grade_menu()
    {
        System.out.println("\n***** WELCOME TO FAME OR SHAME *****");
        System.out.println("1. Add a grade");
        System.out.println("2. Delete a grade");
        System.out.println("2. Calculate the total");
        try
        {
            System.out.print("Select your option: ");
            int option = input.nextInt();
            switch(option)
            {
                case 1:
                {
                    if(!ValidationUtils.exceeds_limit(english.get_evaluated(), 99.9))
                    {
                        AddGradeView add_grade = new AddGradeView(english, input);
                    }
                    else
                    {
                        System.out.println("----- The evaluated percentage is already: 100% -----");
                    }
                } break;
                case 2:
                {
                    if(!english.get_grades_list().isEmpty())
                    {
                        DeleteGradeView delete_grade = new DeleteGradeView(english, input);
                    }
                    else
                    {
                        System.out.println("----- ADD a grade first -----");
                    }
                } break;
                case 3: english.calculate_total(); System.out.println("- Total score: " + english.get_total()); break;
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

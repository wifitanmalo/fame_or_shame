import java.util.InputMismatchException;
import java.util.Scanner;

public class DeleteGradeView
{
    private Scanner input;
    private Subject subject;

    // constructor
    public DeleteGradeView(Subject subject, Scanner input)
    {
        this.subject = subject;
        this.input = input;
        delete_menu();
    }

    // method to show the delete menu
    public void delete_menu()
    {
        int list_size = subject.get_grades_list().size();
        for(int i=0; i<list_size; i++)
        {
            double score = subject.get_grades_list().get(i).get_score();
            double percentage = subject.get_grades_list().get(i).get_percentage();

            System.out.print("\n#" + i + ": " + score + " " + percentage + "%");
        }

        while(true)
        {
            try
            {
                System.out.print("\nEnter the grade position: ");
                int index = input.nextInt();
                if(ValidationUtils.exceeds_limit(index, list_size))
                {
                    throw new IndexOutOfBoundsException("index > list_size");
                }

                double percentage = subject.get_grades_list().get(index).get_percentage();
                subject.delete_grade(index);
                subject.decrease_evaluated(percentage);
                System.out.println("----- grade DELETED successfully -----");
                break;
            }
            catch(InputMismatchException error)
            {
                System.out.println("----- only INT numbers -----");
            }
            catch(IndexOutOfBoundsException error)
            {
                System.out.println("----- invalid index -----");
            }
        }
    }
}

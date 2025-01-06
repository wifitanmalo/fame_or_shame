import java.util.InputMismatchException;
import java.util.Scanner;

public class DeleteSubjectView
{
    private Scanner input;
    private Management management;

    // constructor
    public DeleteSubjectView(Management management, Scanner input)
    {
        this.management = management;
        this.input = input;
        delete_menu();
    }

    // method to show the delete menu
    public void delete_menu()
    {
        int list_size = management.get_subjects_list().size();
        for(int i=0; i<list_size; i++)
        {
            int id = management.get_subjects_list().get(i).get_id();
            String name = management.get_subjects_list().get(i).get_name();

            System.out.print("\n#" + i + ": " + id + " " + name);
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

                management.delete_subject(index);
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

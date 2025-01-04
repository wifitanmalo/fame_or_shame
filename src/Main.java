import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main
{
    Scanner input = new Scanner(System.in).useLocale(Locale.US);
    Signature english = new Signature(204028,
            "English IV",
            2);


    public Main()
    {
        while(true)
        {
            grade_menu();
        }
    }


    // method to show the main menu
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
                    if(!english.limit_verification(english.get_evaluated(), 99.9))
                    {
                        add_menu();
                    }
                    else
                    {
                        System.out.println("----- The evaluated percentage is already: 100% -----");
                    }
                } break;
                case 2: delete_menu(); break;
                case 3: System.out.println("- Total score: " + english.get_total()); break;
                default: System.out.println("----- invalid option -----"); break;
            }
        }
        catch(InputMismatchException error)
        {
            System.out.println("----- only POSITIVE numbers -----");
            input.next();
        }
    }


    // method to show the add menu
    public void add_menu()
    {
        while(true)
        {
            try
            {
                System.out.print("\nEnter the score: ");
                double score = input.nextDouble();
                if(english.negative_verification(score))
                {
                    throw new InputMismatchException("number < 0");
                }
                if(english.limit_verification(score, 5))
                {
                    throw new NumberFormatException("nigger");
                }

                System.out.print("- Enter the percentage: ");
                double percentage = input.nextDouble();
                double current_evaluated = english.get_evaluated();
                if(english.negative_verification(percentage))
                {
                    throw new InputMismatchException("percentage < 0");
                }
                if(english.limit_verification(current_evaluated+percentage, 100))
                {
                    throw new NumberFormatException("evaluated > 100");
                }

                english.add_grade(score, percentage);
                english.increase_evaluated(percentage);
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


    // method to show the delete menu
    public void delete_menu()
    {
        int list_size = english.get_grades_list().size();
        for(int i=0; i<list_size; i++)
        {
            double score = english.get_grades_list().get(i).get_score();
            double percentage = english.get_grades_list().get(i).get_percentage();

            System.out.print("\n#" + i + ": " + score + " " + percentage + "%");
        }

        while(true)
        {
            try
            {
                System.out.print("\nEnter the grade position: ");
                int index = input.nextInt();
                if(english.limit_verification(index, list_size))
                {
                    throw new IndexOutOfBoundsException("index > list_size");
                }

                double percentage = english.get_grades_list().get(index).get_percentage();
                english.delete_grade(index);
                english.decrease_evaluated(percentage);
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

    public static void main(String[] args)
    {
        Main main = new Main();
    }
}
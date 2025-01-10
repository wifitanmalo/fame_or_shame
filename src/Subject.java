import java.util.ArrayList;

public class Subject
{
    private int id;
    private String name;
    private int credits;
    public static double total_evaluated = 0;
    public static final double max_score = 5.0;
    private ArrayList<Grade> grades_list;

    // constructor
    public Subject(int id,
                   String name,
                   int credits)
    {
        this.id = id;
        this.name = name;
        this.credits = credits;
        grades_list = new ArrayList<>();
    }

    // method to add a grade on the grades list
    public void add_grade(double score, double percentage)
    {
        Grade grade = new Grade(score, percentage);
        grade.calculate_grade();
        grades_list.add(grade);
        total_evaluated += percentage;
    }

    // method to delete a grade based in their position
    public void delete_grade(int index)
    {
        grades_list.remove(index);
        total_evaluated -= grades_list.get(index).get_percentage();
    }

    /* method to calculate the total score of the signature
    public void calculate_total()
    {
        this.total = 0;
        for (Grade grade : grades_list)
        {
            this.total += grade.get_score();
        }
    }

     */

    // setters and getters
    public void set_id(int id)
    {
        this.id = id;
    }
    public int get_id()
    {
        return id;
    }

    public void set_name(String name)
    {
        this.name = name;
    }
    public String get_name()
    {
        return name;
    }

    public void set_credits(int credits)
    {
        this.credits = credits;
    }
    public int get_credits()
    {
        return credits;
    }

    public void set_grades_list(ArrayList<Grade> grades_list)
    {
        this.grades_list = grades_list;
    }
    public ArrayList<Grade> get_grades_list()
    {
        return grades_list;
    }
}

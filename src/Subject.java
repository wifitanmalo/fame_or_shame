import java.util.ArrayList;

public class Subject
{
    // attributes
    private int id;
    private String name;
    private int credits;

    // performance attributes
    private double total_score;
    private double total_evaluated;

    // minimum score to approve
    public static final double passing_score = 3.0;

    // maximum score possible
    public static final double max_score = 5.0;

    // grades lists
    private ArrayList<Grade> grades_list;

    // constructor
    public Subject(int id,
                   String name,
                   int credits)
    {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.total_score = 0.0;
        this.total_evaluated = 0.0;
        grades_list = new ArrayList<>();
    }

    // method to add a grade on the grades list
    public void add_grade(Grade grade)
    {
        grades_list.add(grade);
        new GradePanel();
    }

    // method to delete a subject by its position
    public void delete_grade(Grade grade)
    {
        grades_list.remove(grade);
    }

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

    public void set_total_score(double total_score)
    {
        this.total_score = total_score;
    }
    public double get_total_score()
    {
        return total_score;
    }

    public void set_total_evaluated(double total_evaluated)
    {
        this.total_evaluated = total_evaluated;
    }
    public double get_total_evaluated()
    {
        return total_evaluated;
    }
}

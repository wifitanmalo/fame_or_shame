import java.util.ArrayList;

public class Subjects
{
    private int id;
    private String name;
    private int credits;
    private double max_score;
    private double total;
    private double evaluated;
    private ArrayList<Grade> grades_list;

    // constructor
    public Subjects(int id,
                    String name,
                    int credits)
    {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.max_score = 5.0;
        this.total = 0;
        this.evaluated = 0;
        grades_list = new ArrayList<>();
    }

    // method to add a grade on the grades list
    public void add_grade(double score, double percentage)
    {
        Grade grade = new Grade(score, percentage);
        grade.calculate_grade();
        grades_list.add(grade);
        increase_total(grade.get_score());
    }


    // method to delete a grade based in their position
    public void delete_grade(int index)
    {
        grades_list.remove(index);
    }


    // method to increase the total score
    public void increase_total(double increase)
    {
        this.total += increase;
    }


    // method to increase the percentage evaluated
    public void increase_evaluated(double increase)
    {
        this.evaluated += increase;
    }


    // method to increase the percentage evaluated
    public void decrease_evaluated(double decrease)
    {
        this.evaluated -= decrease;
    }


    // method to calculate the total score of the signature
    public void calculate_total()
    {
        this.total = 0;
        for(int i=0; i<grades_list.size(); i++)
        {
            this.total += grades_list.get(i).get_score();
        }
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

    public void set_credits(int credits)
    {
        this.credits = credits;
    }
    public int get_credits()
    {
        return credits;
    }

    public void set_max_score(double max_score)
    {
        this.max_score = max_score;
    }
    public double get_max_score()
    {
        return max_score;
    }

    public void set_total(double total)
    {
        this.total = total;
    }
    public double get_total()
    {
        return total;
    }

    public void set_evaluated(double evaluated)
    {
        this.evaluated = evaluated;
    }
    public double get_evaluated()
    {
        return evaluated;
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

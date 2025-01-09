import java.util.ArrayList;


public class Management
{
    private int total_credits;
    public static final int max_credits = 21;
    private Subject subject;
    private ArrayList<Subject> subjects_list;

    // constructor
    public Management()
    {
        this.total_credits = 0;
        subjects_list = new ArrayList<>();
    }

    // method to add a subject on the subjects list
    public void add_subject(int id,
                            String name,
                            int credits)
    {
        Subject subject = new Subject(id,
                                    name,
                                    credits);
        subjects_list.add(subject);
    }

    // method to delete a subject based in their position
    public void delete_subject(int index)
    {
        subjects_list.remove(index);
    }

    // method to use a Subject object
    public void use_subject(int index)
    {
        this.subject = subjects_list.get(index);
    }

    // method to use a Subject object
    public Subject get_subject()
    {
        return subject;
    }

    // setters and getters

    // method to increase the percentage evaluated
    public void increase_credits(int increase)
    {
        this.total_credits += increase;
    }
    // method to increase the percentage evaluated
    public void decrease_credits(int decrease)
    {
        this.total_credits -= decrease;
    }
    public int get_total_credits()
    {
        return total_credits;
    }

    public void set_subjects_list(ArrayList<Subject> subjects_list)
    {
        this.subjects_list = subjects_list;
    }
    public ArrayList<Subject> get_subjects_list()
    {
        return subjects_list;
    }
}


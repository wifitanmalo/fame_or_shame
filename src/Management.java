import java.util.ArrayList;

public class Management
{
    // amount of signed credits
    public static int signed_credits = 0;

    // maximum amount of credits
    public static final int max_credits = 21;

    // subjects list
    private ArrayList<Subject> subjects_list;

    // constructor
    public Management()
    {
        subjects_list = new ArrayList<>();
    }

    // method to create a subject
    public void create_subject(Subject subject)
    {
        subjects_list.add(subject);
        signed_credits += subject.get_credits();
    }

    // method to delete a subject
    public void delete_subject(Subject subject)
    {
        signed_credits -= subject.get_credits();
        subjects_list.remove(subject);
    }

    // method to verify if a subject already exists
    public boolean subject_exists(int id)
    {
        for (Subject subject : subjects_list)
        {
            if (id == subject.get_id())
            {
                return true;
            }
        }
        return false;
    }

    // setters and getters
    public void set_subjects_list(ArrayList<Subject> subjects_list)
    {
        this.subjects_list = subjects_list;
    }
    public ArrayList<Subject> get_subjects_list()
    {
        return subjects_list;
    }
}


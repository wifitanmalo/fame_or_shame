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

    // method to create a subject on the subjects box/list
    public void create_subject(Subject subject)
    {
        subjects_list.add(subject);
        new SubjectPanel(subject);
        signed_credits += subject.get_credits();
    }

    // method to delete a subject by its position
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

    // method to get the subject index based in their id
    public int get_index(int id)
    {
        for(int i=0; i<subjects_list.size(); i++)
        {
            if(id == subjects_list.get(i).get_id())
            {
                return i;
            }
        }
        return 0;
    }
}


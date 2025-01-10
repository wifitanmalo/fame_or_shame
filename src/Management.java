import java.util.ArrayList;


public class Management
{
    public static int signed_credits = 0;
    public static final int max_credits = 21;
    private ArrayList<Subject> subjects_list;

    // constructor
    public Management()
    {
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
        signed_credits += credits;
    }

    // method to delete a subject based in their position
    public void delete_subject(int index)
    {
        signed_credits -= subjects_list.get(index).get_credits();
        subjects_list.remove(index);
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


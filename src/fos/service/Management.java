package fos.service;

import java.util.ArrayList;

public class Management
{
    // amount of signed credits
    public static int signedCredits = 0;

    // maximum amount of credits
    public static final int MAX_CREDITS = 21;

    // subjects list
    private ArrayList<Subject> subjectsList;

    // constructor
    public Management()
    {
        subjectsList = new ArrayList<>();
    }

    // method to create a subject
    public void createSubject(Subject subject)
    {
        subjectsList.add(subject);
        signedCredits += subject.getCredits();
    }

    // method to delete a subject
    public void deleteSubject(Subject subject)
    {
        signedCredits -= subject.getCredits();
        subjectsList.remove(subject);
    }

    // method to verify if a subject already exists
    public boolean subjectExists(int id)
    {
        for (Subject subject : subjectsList)
        {
            if (id == subject.getId())
            {
                return true;
            }
        }
        return false;
    }

    // setters and getters
    public void setSubjectsList(ArrayList<Subject> subjects_list)
    {
        this.subjectsList = subjects_list;
    }
    public ArrayList<Subject> getSubjectsList()
    {
        return subjectsList;
    }
}


package fos.service;

import fos.view.GradePanel;

import java.util.ArrayList;

public class Subject
{
    // attributes
    private int id;
    private String name;
    private int credits;

    // performance attributes
    private double totalScore;
    private double totalEvaluated;

    // minimum score to approve
    public static final double PASSING_SCORE = 3.0;

    // maximum score possible
    public static final double MAX_SCORE = 5.0;

    // grades lists
    private ArrayList<GradePanel> gradesList;

    // constructor
    public Subject(int id,
                   String name,
                   int credits)
    {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.totalScore = 0.0;
        this.totalEvaluated = 0.0;
    }

    // method to create a grade
    public void createGrade(GradePanel grade)
    {
        gradesList.add(grade);
    }

    // method to delete a grade
    public void deleteGrade(GradePanel grade)
    {
        gradesList.remove(grade);
    }

    // method to update a grade by its index
    public void updateGrade(GradePanel grade)
    {
        gradesList.set(getGradeIndex(grade), grade);
    }

    // method to get the index of the fos.view.GradePanel
    public int getGradeIndex(GradePanel grade)
    {
        return gradesList.indexOf(grade);
    }

    // setters and getters
    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setCredits(int credits)
    {
        this.credits = credits;
    }
    public int getCredits()
    {
        return credits;
    }

    public void setTotalScore(double total_score)
    {
        this.totalScore = total_score;
    }
    public double getTotalScore()
    {
        return totalScore;
    }

    public void setTotalEvaluated(double total_evaluated)
    {
        this.totalEvaluated = total_evaluated;
    }
    public double getTotalEvaluated()
    {
        return totalEvaluated;
    }

    public void setGradesList(ArrayList<GradePanel> grades_list)
    {
        this.gradesList = grades_list;
    }
    public ArrayList<GradePanel> getGradesList()
    {
        return gradesList;
    }
}

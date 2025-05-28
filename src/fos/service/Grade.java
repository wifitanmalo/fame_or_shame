package fos.service;

import java.util.ArrayList;

public class Grade
{
    private int subjectID;
    private double score;
    private double percentage;

    // list of sub grades
    private ArrayList<Grade> subGradesList;

    // constructor
    public Grade(int subjectID,
                double score,
                double percentage)
    {
        this.subjectID = subjectID;
        this.score = score;
        this.percentage = percentage;
        this.subGradesList = new ArrayList<>();
    }

    // method to calculate the sum of the sub grades
    public void calculateScore()
    {
        this.score = 0;
        for(Grade grade : subGradesList)
        {
            this.score += grade.getScore();
        }
        this.score /= subGradesList.size();
    }

    // getters and setters
    public void setSubjectID(int id) {
        this.subjectID = id;
    }
    public int getSubjectID() {
        return subjectID;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public double getScore() {
        return score;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    public double getPercentage() {
        return percentage;
    }

    public void setSubGradesList(ArrayList<Grade> subGradesList) {
        this.subGradesList = subGradesList;
    }
    public ArrayList<Grade> getSubGradesList() {
        return subGradesList;
    }
}

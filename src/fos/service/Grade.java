package fos.service;

public class Grade
{
    private int subjectID;
    private double score;
    private double percentage;

    // constructor
    public Grade(int subjectID,
                double score,
                double percentage)
    {
        this.subjectID = subjectID;
        this.score = score;
        this.percentage = percentage;
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
}

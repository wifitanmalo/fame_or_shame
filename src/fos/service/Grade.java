package fos.service;

public class Grade
{
    // attributes
    private int id;
    private int subjectID;
    private String name;
    private double score;
    private double percentage;

    // constructor
    public Grade(int id,
                int subjectID,
                String name,
                double score,
                double percentage)
    {
        this.id = id;
        this.subjectID = subjectID;
        this.name = name;
        this.score = score;
        this.percentage = percentage;
    }

    // setters and getters
    public void setID(int id) {
        this.id = id;
    }
    public int getID() {
        return id;
    }

    public void setSubjectID(int id) {
        this.subjectID = id;
    }
    public int getSubjectID() {
        return subjectID;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
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

package fos.service;

public class Subject
{
    // attributes
    private int id;
    private String name;
    private int credits;

    // performance attributes
    private double totalScore, totalEvaluated;

    // score constants
    public static final double PASSING_SCORE = 3.0, MAX_SCORE = 5.0;

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
}
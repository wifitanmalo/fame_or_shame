package fos.service;

public class User
{
    // attributes
    private int id;
    private double passScore;
    private double maxScore;
    private int maxCredits;


    // constructor
    public User(int id,
                double passScore,
                double maxScore,
                int maxCredits)
    {
        this.id = id;
        this.passScore = passScore;
        this.maxScore = maxScore;
        this.maxCredits = maxCredits;
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

    public void setPassScore(double passScore)
    {
        this.passScore = passScore;
    }
    public double getPassScore()
    {
        return passScore;
    }

    public void setMaxScore(double maxScore)
    {
        this.maxScore = maxScore;
    }
    public double getMaxScore()
    {
        return maxScore;
    }

    public void setMaxCredits(int maxCredits)
    {
        this.maxCredits = maxCredits;
    }
    public int getMaxCredits()
    {
        return maxCredits;
    }
}
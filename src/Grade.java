public class Grade
{
    // attributes
    private double score;
    private double percentage;

    // constructor
    public Grade(double score,
                 double percentage)
    {
        this.score = score;
        this.percentage = percentage;
    }

    // setters and getters
    public void set_score(double score)
    {
        this.score = score;
    }
    public double get_score()
    {
        return score;
    }

    public void set_percentage(double percentage)
    {
        this.percentage = percentage;
    }
    public double get_percentage()
    {
        return percentage;
    }
}

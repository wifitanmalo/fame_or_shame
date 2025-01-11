public class Grade
{
    // grade values
    private double score;
    private double percentage;
    private double grade_value;

    // constructor
    public Grade(double score, double percentage)
    {
        this.score = score;
        this.percentage = percentage;
        this.grade_value = score * (percentage/100);
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

    public void set_grade_value(double grade_value)
    {
        this.grade_value = grade_value;
    }
    public double get_grade_value()
    {
        return grade_value;
    }
}

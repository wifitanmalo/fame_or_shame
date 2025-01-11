public class Grade
{
    private double score;
    private double percentage;
    private double final_grade;

    // constructor
    public Grade(double score, double percentage)
    {
        this.score = score;
        this.percentage = percentage;
    }

    // method to calculate the grade based on the percentage
    public void calculate_grade()
    {
        this.final_grade = this.get_score() * (this.get_percentage()/100);
    }
    public double get_grade()
    {
        return final_grade;
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

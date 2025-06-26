package fos.service;

public class Grade
{
    // attributes
    private int id;
    private int subjectID;
    private String name;
    private double score;
    private double percentage;
    private Integer idSuperGrade;


    // constructor
    public Grade(int id,
                int subjectID,
                String name,
                double score,
                double percentage,
                Integer idSuperGrade)
    {
        this.id = id;
        this.subjectID = subjectID;
        this.name = name;
        this.score = score;
        this.percentage = percentage;
        this.idSuperGrade = idSuperGrade;
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

    public void setSuperID(Integer id) {
        this.idSuperGrade = id;
    }
    public Integer getSuperID() {
        return idSuperGrade;
    }
}

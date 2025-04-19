package fos.service;

public class ValidationUtils
{
    // method to verify if a number is negative
    public static boolean isNegative(double number)
    {
        return number < 0;
    }

    // method to verify if a number exceeds the limit
    public static boolean exceedsLimit(double number, double limit)
    {
        return number > limit;
    }

    // method to verify if a number is equal to another
    public static boolean equals(double current_number, double number)
    {
        return current_number == number;
    }

    // method to verify if a text is empty
    public static String emptyText(String text)
    {
        if (text.isEmpty())
        {
            return "0";
        }
        else
        {
            return text;
        }
    }
}

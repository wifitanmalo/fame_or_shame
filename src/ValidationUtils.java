public class ValidationUtils
{
    // method to verify if a number is positive
    public static boolean is_negative(double number)
    {
        return number < 0;
    }

    // method to verify if a number is equal to another
    public static boolean equals(double current_number, double number)
    {
        return current_number == number;
    }

    // method to verify if a number exceeds the limit
    public static boolean exceeds_limit(double number, double limit)
    {
        return number > limit;
    }
}

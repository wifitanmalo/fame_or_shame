public class ValidationUtils
{
    // method to verify if the number is positive
    public static boolean is_negative(double number)
    {
        return number < 0;
    }

    // method to verify if the number exceeds the limit
    public static boolean exceeds_limit(double number, double limit)
    {
        return number > limit;
    }

    // method to verify if the current id exists
    public static boolean id_exists(double current, double id)
    {
        return current == id;
    }
}

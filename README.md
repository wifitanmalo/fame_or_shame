# Fame or Shame

_Fame or Shame_ is an application developed with **Java** to manage your subject grades. You can create a subject with its respective data: id, name, and credits, where you can add your grades with the score obtained and its percentage to calculate your final grade for the course.

The name [_Fame or Shame_](https://gta.fandom.com/wiki/Fame_or_Shame) is owned by [_Rockstar Games_](https://www.rockstargames.com/), being a television show in the video game [_Grand Theft Auto V_](https://www.rockstargames.com/gta-v).

> [!IMPORTANT]
> Make sure you have the following installed:
>
> Java Development Kit **(version 8 or higher).**
> - [Download JDK](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
> 
>  An IDE compatible with Java, such as _IntelliJ IDEA_ **(recommended)** or _Eclipse_.
>  - [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
>  - [Download Eclipse](https://www.eclipse.org/downloads/)

## Program start

-------------

When you run the program, a window will appear with a box showing the subjects and a `+` button to create a new one.

### Create a Subject

When you click the `+` button, a window will appear with three text fields to enter the subject id, name, and credits. Additionally, there will be a "Back" button to return to the subject menu and an "Add" button to confirm the creation of the subject.

> [!CAUTION]
> - The ID and credits must be **positive integers** not exceeding 9 digits.
> - The ID must be **different** from existing ones.
> - The name cannot be **empty**.
> - The name cannot exceed **50 characters**.
> - The number of credits cannot exceed the program's **allowed limit**.
 
After finalizing the subject creation, the data will be stored in the `subjects.txt` file in the following order: `id, name, credits, total_score, total_evaluated`. The last two values will be set to `0.0` when a new subject is created.
> _**Example of a subject:**_
    
    111021,Univariate Calculus,3,0.0,0.0

You will then be redirected back to the subject menu, where the new subject will be displayed in the box with two buttons: `+` to enter the grades menu and `x` to delete the subject.

### Create a grade

When you click the `+` button on the subject, a window similiar to the previous one will appear, but with two new buttons: `Total` to calculate the total grades and a `Back` button to return to the subject menu. Above the `+` button, the calculated value of the subject will be shown, displayed in green `#C5EF48` if you passed and in red `#FF6865` if you failed.

The usage is the same as the previous menu. Clicking the `+` button will add a new grade with two text fields for the score and percentage, as well as an `x` button to delete the grade. Each grade will be stored in the `grades.txt` file in the following order: `id,score,percentage`, and the file will be updated as you make changes to the text fields.

> _**Example of grades:**_

    111021,5,10
    111021,3.4,30
    111021,4.2,10
    111021,1.4,20
    111021,5,10
    111021,0.5,20

### Calculate Total Grade

After adding all the grades for your course, you can press the `Total` button to calculate the total you obtained and whether you successfully passed the subject.

> [!CAUTION]
> - Grades and percentages must be **positive doubles**.
> - Grades cannot exceed the program's **allowed limit**.
> - Percentages must be higher than **0%.**
> - The sum of percentages cannot exceed **100%**.

## Settings

-------------

### Minimum and Maximum Grade

In the attributes of the Subject class, you will find the following variables:

    // minimum score to approve
    public static final double passing_score = 3.0;

    // maximum score possible
    public static final double max_score = 5.0;

You can change them to the grading values used by your institution.

### Credit Limit

In the attributes of the Management class, you will find the following variable: 

    // maximum amount of credits
    public static final int max_credits = 21;

You can change it to the maximum credit limit used by your institution.

## Author's Note

-------------

I hope this program makes it easier for you to calculate your grades without having to remember each grade and percentage throughout the course.

Good luck in your signatures!

    By: Nicolás Chaparro

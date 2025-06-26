# Fame or Shame

_Fame or Shame_ is an application developed with **Java** to manage your subject
grades. You can create a subject with its respective data: id, name, and credits,
where you can add your grades with the score obtained and its percentage to calculate
your final grade for the course.

The name [_Fame or Shame_](https://gta.fandom.com/wiki/Fame_or_Shame) is owned by
[_Rockstar Games_](https://www.rockstargames.com/), being a television show in the
video game [_Grand Theft Auto V_](https://www.rockstargames.com/gta-v).

> [!IMPORTANT]
> Make sure you have the _Java Development Kit_ installed **(version 17 or higher):**
>- [Download JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
>
>  `Make sure your system has the JAVA_HOME environment variable set to point to your JDK version.`
> 
> If you want to work with the source code, use a Java-compatible IDE such as:
> - [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
> - [Eclipse](https://www.eclipse.org/downloads/)
> 
>  `If you are going to run the project with the Main class, copy the \database
> folder to the root of the project. When you finish, you can delete it.`


## Program start

-------------

To use the program, you must go to the `\run` folder and open the `fame_or_shame.jar` file.
When you run the program, a window will appear with a box showing the subjects
and a `+` button to create a new one.


### Create a subject

When you click the `+` button, a window will appear with three text fields
to enter the subject id, name, and credits. Additionally, there will be a
`Back` button to return to the subject menu and an "Add" button to confirm
the creation of the subject.

> [!CAUTION]
> - The ID and credits must be **positive integers** not exceeding 9 digits.
> - The ID must be **different** from existing ones.
> - The name cannot be **empty**.
> - The name cannot exceed **50 characters**.
> - The number of credits cannot exceed the program's **allowed limit**.
 
After finalizing the subject creation, the data will be stored in the `Subject`
table of `fos.db` in the following order: `id, name, credits, totalScore,
totalEvaluated`. The last two values will be set to `0.0` when a new subject
is created.

> _**Example of a subject:**_
    
    111021, Univariate Calculus, 3, 0.0, 0.0

You will then be redirected back to the subject menu, where the new subject
will be displayed in the box with two buttons: `+` to enter the grades menu
and `x` to delete the subject.


### Create a grade

When you click the `+` button on the subject, a similar window to the
previous one will appear, but with two new buttons: `Total` to calculate
the total grades and a `Back` button to return to the subject menu. Above
the `+` button, the calculated value of the subject will be shown, displayed
in green `#C5EF48` if you passed and in red `#FF6865` if you failed.

The operation will be similar to the previous menu, but with a small change:
the click the `+` button now will show a window with a text field where you
can set a name for the grade **(no more than 30 characters).** When you press
`OK`, a new grade is added with two text fields for the score and percentage,
an `x` button to delete the grade and text showing the value the grade adds
to the total score, which changes in real time as the values ​​in both fields
change.

Each grade will be stored in the `Grade` table of `fos.db` in the following
order: `id,idSubject,name,score,percentage,idSuperGrade`, and the database
will be updated as you make changes to the text fields.

> _**Example of grades:**_

    1, 111021, workshop I, 5.0, 10.0, NULL
    2, 111021, exam I, 3.4, 30.0, NULL
    3, 111021, workshop II, 4.2, 10.0, NULL
    4, 111021, exam II, 1.4, 20.0, NULL
    5, 111021, workshop III, 5.0, 10.0, NULL
    6, 111021, final exam, 0.5, 20.0, NULL


### Calculate Total Grade

After adding all the grades for your course, you can press the `Total`
button to calculate the total you obtained and whether you successfully
passed the subject.

> [!CAUTION]
> - Grades and percentages must be **positive doubles**.
> - Grades cannot exceed the program's **allowed limit**.
> - Percentages must be higher than **0%.**
> - The sum of percentages cannot exceed **100%**.


## Settings

-------------

Below the `+` button of the subject menu, you will find a `@` button
that sends you to the settings menu where there will be three text fields.


### Passing/Maximum score

The first two are for the `passing` and `maximum score`, you can
change them to the grading values used by your institution and the
program will now work based on those values.

> [!CAUTION]
> - Both scores must be **positive doubles**.
> - The passing score must be **less than or equal** to the maximum
score.


### Credit Limit

And below the previous fields you will find the field for the `maximum
credits` your institution allows you to enroll in per semester or change
it for a much larger number so you can add as many courses as you want.

> [!CAUTION]
> - Credits must be a **positive integer.**
> - credits must be **higher** than 0.
> - Credits must be **higher** than or **equal** to the signed credits.
> - Credits must be **lower** than or **equal** to 2147483647.

## Author's Note

-------------

I hope this program makes it easier for you to calculate your grades without
having to remember each grade and percentage throughout the course.

Good luck in your signatures!

    By: Nicolás Chaparro

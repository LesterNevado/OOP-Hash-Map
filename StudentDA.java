import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import HashMap Example.Student;

public class StudentDA {
    private HashMap<String, Course> courseMap;

    public StudentDA() {

        try {
            Scanner studentFile = new Scanner(new FileReader("student.csv"));

            //disregard the header text
            //studentFile.nextLine();

            while (studentFile.hasNext()) {

                courseMap = new HashMap<>();
                String studentLineData = new String();
                studentLineData = studentFile.nextLine();

                String[] studentLineDataSpecific = new String[3];
                studentLineDataSpecific = studentLineData.split(",");

                Student student = new Student();
                student.setStudentNumber(studentLineDataSpecific[0].trim());
                student.setLastName(studentLineDataSpecific[1].trim());
                student.setFirstName((studentLineDataSpecific[2]));

                // averageGrade and hashmap are not set
                readGrades(student);

                //set average grade
                Double grade = 0.0;
                Integer counter = 0;
                for (Map.Entry<String, Course> entryMap : student.getCourseMap().entrySet()) {
                    grade += entryMap.getValue().getGrade();
                    counter++;
                }
                student.setAverageGrade(grade / counter);
                printReport(student);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printReport(Student student) {
        System.out.println("Student number: " + student.getStudentNumber());
        System.out.println("Name: " + student.getLastName() + ", " + student.getFirstName());
        System.out.println("Average grade: " + student.getAverageGrade());
        System.out.println("-------- Details ---------------------");
        System.out.println("Course code\t\tDescription\t\tUnit\t\tGrade");
        for (Map.Entry<String, Course> entryMap : student.getCourseMap().entrySet()) {
            System.out.print(entryMap.getValue().getCourseCode() + "\t\t");
            System.out.print(entryMap.getValue().getDescription() + "\t\t");
            System.out.print(entryMap.getValue().getUnit() + "\t\t");
            System.out.println(entryMap.getValue().getGrade());

        }
        System.out.println();
    }

    private void readGrades(Student student) {
        CourseDA courseDA = new CourseDA(student.getStudentNumber());
        student.setCourseMap(courseDA.getCourseMap());
    }
}

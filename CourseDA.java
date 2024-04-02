import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class CourseDA {
    private HashMap<String, Course> courseMap;

    public HashMap<String, Course> getCourseMap() {
        return courseMap;
    }


    public CourseDA(String studentNumber) {
        try {
            Scanner gradesFile = new Scanner(new FileReader("grades.csv"));

            courseMap = new HashMap<>();

            //disregard header text
            //gradesFile.nextLine();

            Integer key = 0;
            while (gradesFile.hasNext()) {
                String gradesLineData = new String();
                gradesLineData = gradesFile.nextLine();

                String[] gradesLineDataSpecific = new String[3];
                gradesLineDataSpecific = gradesLineData.split(",");

                if (studentNumber.equals(gradesLineDataSpecific[0].trim())) {

                    Course course = new Course();
                    course.setCourseCode(gradesLineDataSpecific[1].trim());
                    course.setGrade(Double.parseDouble(gradesLineDataSpecific[2].trim()));

                    //missing  description and unit
                    courseMap.put(studentNumber+key, readCourseFile(course));
                    key++;

                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Course readCourseFile(Course course) throws FileNotFoundException {
        Scanner courseFile = new Scanner(new FileReader("coursedata.csv"));
        //disregard header text
        //courseFile.nextLine();

        while (courseFile.hasNext()) {
            String courseLineData = new String();
            courseLineData = courseFile.nextLine();

            String[] courseLineDataSpecific = new String[3];
            courseLineDataSpecific = courseLineData.split(",");

            if (course.getCourseCode().equals(courseLineDataSpecific[0].trim())) {
                course.setDescription(courseLineDataSpecific[1].trim());
                course.setUnit(Integer.parseInt(courseLineDataSpecific[2].trim()));
                break;
            }
        }
        //updated course - go back to the calling method
        return course;
    }
}

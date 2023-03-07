package RegistryClasses;

public class Student implements Comparable <Student> {
    public String name;
    public String surname;
    public StudentCondition studentCondition;
    public int birthYear;
    public double points;
    public Student(String newName, String newSurname, StudentCondition newStudentCondition, int newYear, double newPoints) {
        name = newName;
        surname = newSurname;
        studentCondition = newStudentCondition;
        birthYear = newYear;
        points = newPoints;
    }
    @Override
    public String toString() {
        return "Student's data: \n\tName: " + name + " " + surname + "\n\tCondition: " + studentCondition + "\n\tBirth year: " + birthYear + "\n\tPoints: " + points + "\n";
    }
    @Override
    public int compareTo(Student o) {
        return this.surname.compareTo(o.surname);
    }
}

package RegistryClasses;

import javax.management.InstanceAlreadyExistsException;
import java.util.*;

class CompareByPoints implements Comparator<Student> {
    public int compare(Student o1, Student o2) {
        if (o1.points < o2.points){
            return -1;
        }
        else if (o1.points == o2.points) {
            return 0;
        }
        return 1;
    }
}

class CompareByName implements Comparator<Student> {
    public int compare(Student o1, Student o2) {
        return o1.surname.compareTo(o2.surname);
    }
}

public class Class {
    public String groupName;
    public List<Student> students;
    public int maxStudentsNumber;
    public int studentsNumber = 0;
    public Class(String newName, int classCapacity) {
        //System.out.println("Creating a new Class...");
        groupName = newName;
        students = new ArrayList<>();
        maxStudentsNumber = classCapacity;
    }
    public Student search(String name) {
        Student newStudent = new Student("", name, StudentCondition.OBECNY, 0, 0.0);
        for (Student i : students){
            if (i.compareTo(newStudent) == 0) {
                return i;
            }
        }
        throw new NoSuchElementException("Student was not found!\n");
    }
    public boolean search(String name, String surname) {
        Student newStudent = new Student(name, surname, StudentCondition.OBECNY, 0, 0.0);
        for (Student i : students){
            if (i.compareTo(newStudent) == 0) {
                //System.out.println("Student was found!\n");
                return true;
            }
        }
        return false;
    }
    public void addStudent( Student newStudent) throws Exception {
        //System.out.println("Preparing to add " + newStudent.name + " " + newStudent.surname + " to the Class");
        if (studentsNumber == maxStudentsNumber){
            throw new ArrayStoreException("Max capacity of class reached!\n");
        }
        //System.out.println("Checking if student is already in the class...");
        for (Student i: students) {
            if (i.compareTo(newStudent) == 0) {
                throw new Exception("This student is already in the class!\n");
            }
        }
        //System.out.println("Adding student to the class...");
        students.add(newStudent);
        studentsNumber++;
        //System.out.println("Student " + newStudent + " was added successfully");
    }
    public void addPoints(Student newStudent, double newPoints) {
        //System.out.println("Adding " + newPoints + " to Student:\n" + newStudent);
        //System.out.println("Checking if student is in the class...");
        Student foundStudent = this.search(newStudent.surname);
        foundStudent.points+=newPoints;
        //System.out.println("Points added!\n" +newStudent);
    }
    public void getStudent(Student newStudent) {
        for (int i = 0; i < students.size(); ++i) {
            if (students.get(i).equals(newStudent)) {
                students.remove(i);
                i--;
                studentsNumber--;
            }
        }
    }
    public void changeCondition(Student newStudent, StudentCondition newCondition) {
        Student foundStudent = this.search(newStudent.surname);
        foundStudent.studentCondition = newCondition;
    }
    public void removePoints(Student newStudent, double newPoints) {
        //System.out.println("Removing points " + newPoints + " from Student:\n" + newStudent);
        //System.out.println("Checking if student is in the class...");
        Student foundStudent = this.search(newStudent.surname);
        foundStudent.points-=newPoints;
        //System.out.println("Points removed!\n" +newStudent);

    }
    public ArrayList<Student> searchPartial(String data) {
        //System.out.println("Searching for students whose name or surname contains " + data);
        ArrayList<Student> matchingStudents = new ArrayList<>();
        for (Student i: students){
            if (i.surname.toLowerCase().contains(data.toLowerCase()) || i.name.toLowerCase().contains(data.toLowerCase())) {
                //System.out.println("Found " + i.name + " " + i.surname);
                matchingStudents.add(i);
            }
        }
        //System.out.println("List of students whose name or surname contains " + data);
        for (Student i : matchingStudents)
        {
            //System.out.println(i.name + " " + i.surname);
        }
        return matchingStudents;
    }
    public void countByCondition(StudentCondition condition) {
        int counter = 0;
        //System.out.println("Counting " + condition + " students...");
        for (Student i : students) {
            if (i.studentCondition.equals( condition)) {
                counter++;
            }
        }
        //System.out.println("there are " +counter +" "+ condition + " students");
        //return counter;
    }
    public void summary() {
        System.out.println("Class " + this.groupName +
                "\n\tNumber of students: " + this.studentsNumber +
                "\n\tMax number of students: " + maxStudentsNumber +
                "\nList of students:\n");
        int counter = 1;
        for (Student i : students) {
            System.out.println(counter + "." + i);
            counter++;
        }
    }
    public void sortByName() {
        //System.out.println("Sorting students by name...");
        students.sort(new CompareByName());
        //this.summary();
    }
    public void sortByPoints() {
        //System.out.println("Sorting students by points...");
        students.sort(new CompareByPoints());
        //this.summary();
    }
    public Student max() {
        System.out.println("Max element of class: ");
        return Collections.max(students);
    }
    @Override
    public String toString() {
        double stateOfClass = 100.0*studentsNumber/maxStudentsNumber;
        return "Class' data: \n\tClass name: " + groupName + "\n\tUsed capacity: " + stateOfClass + "%";
    }
}

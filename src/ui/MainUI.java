package ui;

import RegistryClasses.Class;
import RegistryClasses.ClassContainer;
import RegistryClasses.Student;
import RegistryClasses.StudentCondition;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

enum Mode {
    Studenci,
    Przedmioty
}

class Data {
    ClassContainer classContainer = new ClassContainer();
    public Data() {
        getSomeRandomData();
    }
    void getSomeRandomData(){
        try {
            classContainer.addClass("MES", 30);
            classContainer.addClass("PR", 20);
            classContainer.addClass("PAOiM", 15);
            classContainer.addClass("PKMiS", 60);
            classContainer.addClass("II", 25);
            classContainer.addClass("WP", 120);
            classContainer.addClass("Optymalizacja", 150);
            classContainer.addClass("", 1000000);
            Student exampleStudent1 = new Student("Jan", "Kowalski", StudentCondition.OBECNY, 1999, 5.0);
            Student exampleStudent2 = new Student("Jakub", "Jankowski", StudentCondition.ODRABIAJACY, 2001, 3.0);
            Student exampleStudent3 = new Student("Marcin", "Nowak", StudentCondition.OBECNY, 2000, 6.0);
            Student exampleStudent4 = new Student("Zuzanna", "Będkowska", StudentCondition.OBECNY, 2001, 5.0);
            classContainer.classes.get("MES").addStudent(exampleStudent1);
            classContainer.classes.get("MES").addStudent(exampleStudent2);
            classContainer.classes.get("MES").addStudent(exampleStudent3);
            classContainer.classes.get("MES").addStudent(exampleStudent4);
            classContainer.classes.get("PR").addStudent(exampleStudent1);
            classContainer.classes.get("PR").addStudent(exampleStudent2);
            classContainer.classes.get("PAOiM").addStudent(exampleStudent3);
            classContainer.classes.get("PAOiM").addStudent(exampleStudent4);
            classContainer.classes.get("II").addStudent(exampleStudent1);
            classContainer.classes.get("WP").addStudent(exampleStudent2);
            classContainer.classes.get("II").addStudent(exampleStudent3);
            classContainer.classes.get("WP").addStudent(exampleStudent4);
            classContainer.classes.get("Optymalizacja").addStudent(exampleStudent1);
            classContainer.classes.get("Optymalizacja").addStudent(exampleStudent2);
            classContainer.classes.get("Optymalizacja").addStudent(exampleStudent3);
            classContainer.classes.get("Optymalizacja").addStudent(exampleStudent4);
            classContainer.classes.get("").addStudent(exampleStudent1);
            classContainer.classes.get("").addStudent(exampleStudent2);
            classContainer.classes.get("").addStudent(exampleStudent3);
            classContainer.classes.get("").addStudent(exampleStudent4);

        } catch (Exception e)
        {
            System.out.print("Something went wrong! ");
            if (e.getMessage() != null) {
                System.out.print(e.getMessage());
            }
            System.out.println("Please try again!");
        }
    }
}

class DataFetcher extends AbstractTableModel {
    Data data;
    Mode tableMode = Mode.Przedmioty;
    private final String[] columnNamesStudentTable;
    private final String[] columnNamesClassesTable;
    ArrayList<Object[]> studentData;
    ArrayList<Object[]> classData;
    ArrayList<String> classNames;
    public DataFetcher() {
        data = new Data();
        studentData = new ArrayList<>();
        classData = new ArrayList<>();
        columnNamesStudentTable = new String[]{"", "Nazwisko", "Imię", "Rok urodzenia", "Stan", "Punkty"};
        columnNamesClassesTable = new String[]{"","Nazwa Przedmiotu", "Liczba studentów", "Limit miejsc", "Zapełnienie"};
        getStudentData("", "");
        getClassData();
    }
    void getStudentData(String searchText, String className){
        studentData = new ArrayList<>();
        ArrayList<Student> found = data.classContainer.classes.get(className).searchPartial(searchText);
        for (Student student : found) {
            Object[] pom = {Boolean.FALSE, student.surname, student.name, student.birthYear, student.studentCondition, student.points};
            studentData.add(pom);
        }
    }
    void getClassData(){
        classData = new ArrayList<>();
        classNames = new ArrayList<>();
        Set<Map.Entry<String, Class>> entries = data.classContainer.classes.entrySet();
        for (Map.Entry<String, Class> entry : entries) {
            String procent = (double)entry.getValue().studentsNumber/entry.getValue().maxStudentsNumber*100 + "%"; //dopiero entry.getValue() jest Class
            if (!entry.getValue().groupName.equals("")) {
                Object[] pom1 = {Boolean.FALSE, entry.getValue().groupName, entry.getValue().studentsNumber, entry.getValue().maxStudentsNumber, procent};
                classData.add(pom1);
            }
            String pom2 = entry.getValue().groupName;
            if (entry.getValue().groupName.equals("")) {
                pom2 = "Wszyscy Studenci";
            }
            classNames.add(pom2);
        }
    }

    public String getColumnName(int col) {
        if (tableMode == Mode.Studenci) {
            return columnNamesStudentTable[col];
        }
        return columnNamesClassesTable[col];
    }

    @Override
    public int getRowCount() {
        if (tableMode ==Mode.Studenci) {
            return studentData.size();
        }
        return classData.size();
    }

    @Override
    public int getColumnCount() {
        if (tableMode ==Mode.Studenci) {
            return columnNamesStudentTable.length;
        }
        return columnNamesClassesTable.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (tableMode ==Mode.Studenci) {
            return studentData.get(rowIndex)[columnIndex];
        }
        return classData.get(rowIndex)[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (tableMode == Mode.Studenci) {
            studentData.get(row)[col] = value;
        } else {
            classData.get(row)[col] = value;
        }
    }

    public java.lang.Class<?> getColumnClass(int columnIndex) {
        if (tableMode == Mode.Studenci) {
            return studentData.get(0)[columnIndex].getClass();
        } else {
            return classData.get(0)[columnIndex].getClass();
        }
    }
    void addStudentToClass(Student student, String className) throws Exception {
        if (className.equals("Wszyscy Studenci")) {
            if (!data.classContainer.classes.get("").search(student.name, student.surname)) {
                data.classContainer.classes.get("").addStudent(student);
            }
        }else {
            if (!data.classContainer.classes.get("").search(student.name, student.surname)) {
                data.classContainer.classes.get("").addStudent(student);
                data.classContainer.classes.get(className).addStudent(student);
            } else {
                student = data.classContainer.classes.get("").search(student.surname);
                data.classContainer.classes.get(className).addStudent(student);
            }
        }
    }
    void addClassToRegistry(String className, int classCapacity) {
        data.classContainer.addClass(className, classCapacity);
    }
    void editStudent(String selectedName, String name, String surname, String birthYear, String points, StudentCondition condition) {
        Student editStudent = data.classContainer.classes.get("").search(selectedName);
        if(!Objects.equals(name, "")) {
            editStudent.name = name;
        }
        if(!Objects.equals(surname, "")) {
            editStudent.surname = surname;
        }
        if(!Objects.equals(birthYear, "")) {
            editStudent.birthYear = Integer.parseInt(birthYear);
        }
        if(!Objects.equals(points, "")) {
            editStudent.points = Double.parseDouble(points);
        }
        if(condition != null) {
            data.classContainer.classes.get("").changeCondition(editStudent, condition);
        }
    }
    void addPointsToStudent(String student, String points) {
        Student editStudent = data.classContainer.classes.get("").search(student);
        if(!Objects.equals(points, "")) {
            data.classContainer.classes.get("").addPoints(editStudent, Double.parseDouble(points));
        }
    }
    void removePointsFromStudent(String student, String points) {
        Student editStudent = data.classContainer.classes.get("").search(student);
        if(!Objects.equals(points, "")) {
            data.classContainer.classes.get("").removePoints(editStudent, Double.parseDouble(points));
        }
        if (editStudent.points <= 0.0) {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("DEFICYT ETCS, STUDENT SKREŚLONY Z LISTY STUDENTÓW!"));
            JOptionPane.showConfirmDialog(null, panel, "DEFICYT ETCS!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            Set<Map.Entry<String, Class>> entries = data.classContainer.classes.entrySet();
            for (Map.Entry<String, Class> entry : entries) {
                if (entry.getValue().search(editStudent.name, editStudent.surname)) {
                    entry.getValue().getStudent(editStudent);
                }
            }
        }
    }
    void editClass(String oldName, String newName) throws Exception {
        if(!Objects.equals(newName, "")) {
            data.classContainer.renameClass(oldName, newName);
        }
    }
    void removeStudentFromGroup (@NotNull String className) {
        if (className.equals("Wszyscy Studenci")){
            className = "";
        }
        for (int i = 0; i < data.classContainer.classes.get(className).studentsNumber; ++i) {
            if(getValueAt(i, 0).equals(true)) {
                Student student = data.classContainer.classes.get(className).search((String) getValueAt(i, 1));
                if (!className.equals("")) {
                    data.classContainer.classes.get(className).getStudent(student);
                } else {
                    Set<Map.Entry<String, Class>> entries = data.classContainer.classes.entrySet();
                    for (Map.Entry<String, Class> entry : entries) {
                        if (entry.getValue().search((String)getValueAt(i, 2), (String) getValueAt(i, 1))) {
                            entry.getValue().getStudent(student);
                        }
                    }
                }
            }
        }
    }
    void removeGroupFromRegistry() {
        for (int i = 0; i < data.classContainer.classes.size() - 1; ++i) {
            if (getValueAt(i, 0).equals(true)) {
                data.classContainer.removeClass((String) getValueAt(i, 1));
            }
        }
    }
}

public class MainUI {
    private JPanel rootPanel;
    private JTable showTable;
    private JComboBox<String> studentsOrClass;
    private JTextField searchTextBox;
    private JComboBox<String> operationBox;
    private JComboBox<String> sortBy;
    private JLabel infoBox;
    private JLabel searchBox;
    private final DataFetcher dataFetcher = new DataFetcher();
    public MainUI() {
        sortBy.setVisible(false);
        infoBox.setVisible(false);
        searchBox.setVisible(false);
        searchTextBox.setVisible(false);
        createOperationBox();
        createStudentsOrClasses();
        createSortBy();
        createSearchTextBox();
        createTable();
    }
    public JPanel getRootPanel(){
        return rootPanel;
    }
    public void showRecords() {
        dataFetcher.studentData.clear();
        dataFetcher.classData.clear();
        dataFetcher.classNames.clear();
        dataFetcher.getClassData();
        String className = (String) studentsOrClass.getSelectedItem();
        assert className != null;
        if (!"Wszystkie Przedmioty".equals(className) && !"".equals(className))
        {
            sortBy.setVisible(true);
            infoBox.setVisible(true);
            searchBox.setVisible(true);
            searchTextBox.setVisible(true);
            dataFetcher.tableMode = Mode.Studenci;
            updateOperationBox();
            String searchText = searchTextBox.getText();
            String sortedBy = (String) sortBy.getSelectedItem();
            if (studentsOrClass.getSelectedItem() == null || studentsOrClass.getSelectedItem().equals( "Wszyscy Studenci")) {
                className = "";
            }
            assert sortedBy != null;
            if ("Liczba punktów, rosnąco".equals(sortedBy)) {
                dataFetcher.data.classContainer.classes.get(className).sortByPoints();
            } else {
                dataFetcher.data.classContainer.classes.get(className).sortByName();
            }
            dataFetcher.getStudentData(searchText, className);
        } else {
            dataFetcher.tableMode = Mode.Przedmioty;
            updateOperationBox();
            sortBy.setVisible(false);
            infoBox.setVisible(false);
            searchBox.setVisible(false);
            searchTextBox.setVisible(false);
            dataFetcher.getClassData();
        }
    }
    public void showEmpty() {
        DataFetcher df = new DataFetcher();
        df.studentData.clear();
        df.tableMode = dataFetcher.tableMode;
        showTable.setModel(df);
        showRecords();
        showTable.setModel(dataFetcher);
    }
    public void updateStudentOrClassesButton() {
        studentsOrClass.removeAllItems();
        studentsOrClass.addItem("");
        for(String className : dataFetcher.classNames) {
            studentsOrClass.addItem(className);
        }
        studentsOrClass.addItem("Wszystkie Przedmioty");
    }
    public void updateOperationBox(){
        operationBox.removeAllItems();
        if(dataFetcher.tableMode == Mode.Przedmioty) {
            operationBox.setModel(new DefaultComboBoxModel<>(new String[]{"", "Dodaj studenta", "Edytuj studenta", "Dodaj punkty", "Usuń punkty", "Dodaj przedmiot", "Edytuj przedmiot", "Usuń przedmiot"}));
        } else {
            operationBox.setModel(new DefaultComboBoxModel<>(new String[]{"", "Dodaj studenta", "Edytuj studenta", "Usuń studenta", "Dodaj punkty", "Usuń punkty", "Dodaj przedmiot", "Edytuj przedmiot"}));
        }
    }
    private void operationBoxUIAddStudent () {
        try {
            Object[] items = {StudentCondition.OBECNY, StudentCondition.NIEOBECNY, StudentCondition.ODRABIAJACY, StudentCondition.CHORY};
            JComboBox<Object> combo = new JComboBox<>(items);
            JComboBox<Object> classes = new JComboBox<>();
            for (String className : dataFetcher.classNames) {
                classes.addItem(className);
            }
            JTextField name = new JTextField("");
            JTextField surname = new JTextField("");
            JTextField birthYear = new JTextField("");
            JTextField points = new JTextField("");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Imię:"));
            panel.add(name);
            panel.add(new JLabel("Nazwisko:"));
            panel.add(surname);
            panel.add(new JLabel("Rok Urodzenia:"));
            panel.add(birthYear);
            panel.add(new JLabel("Punkty:"));
            panel.add(points);
            panel.add(new JLabel("Stan:"));
            panel.add(combo);
            panel.add(new JLabel("Przedmiot:"));
            panel.add(classes);
            JOptionPane.showConfirmDialog(null, panel, "Dodawanie Studenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            String studentName = name.getText();
            String studentSurname = surname.getText();
            int studentBirthYear = Integer.parseInt(birthYear.getText());
            double studentPoints = Double.parseDouble(points.getText());
            StudentCondition condition = (StudentCondition) combo.getSelectedItem();
            String className = (String) classes.getSelectedItem();
            Student student = new Student(studentName, studentSurname, condition, studentBirthYear, studentPoints);
            assert className != null;
            dataFetcher.addStudentToClass(student, className);
        }catch (Exception e)
        {
            String message = "Something went wrong!\n";
            if (e.getMessage() != null) {
                message += e.getMessage();
                message += "\n";
            }
            message += "Please try again!\n";
            JOptionPane.showMessageDialog(new JFrame(), message);
        }
    showEmpty();
    }
    private void operationBoxUIAddClass() {
        try {
            JTextField name = new JTextField("");
            JTextField capacity = new JTextField("");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nazwa przedmiotu:"));
            panel.add(name);
            panel.add(new JLabel("Limit miejsc:"));
            panel.add(capacity);
            JOptionPane.showConfirmDialog(null, panel, "Dodawanie Przedmiotu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            String className = name.getText();
            int classCapacity = Integer.parseInt(capacity.getText());
            dataFetcher.addClassToRegistry(className, classCapacity);
            updateStudentOrClassesButton();
        }catch (Exception e)
        {
            String message = "Something went wrong!\n";
            if (e.getMessage() != null) {
                message += e.getMessage();
                message += "\n";
            }
            message += "Please try again!\n";
            JOptionPane.showMessageDialog(new JFrame(), message);
        }
        showEmpty();
    }
    private void operationBoxUIEditStudent() {
        Object[] items = {StudentCondition.OBECNY, StudentCondition.NIEOBECNY, StudentCondition.ODRABIAJACY, StudentCondition.CHORY};
        JComboBox<Object> combo = new JComboBox<>(items);
        JComboBox<Object> students = new JComboBox<>();
        students.addItem("");
        for (Student student  : dataFetcher.data.classContainer.classes.get("").students) {
            students.addItem(student.surname);
        }
        JTextField name = new JTextField("");
        JTextField surname = new JTextField("");
        JTextField birthYear = new JTextField("");
        JTextField points = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Wybierz studenta do edycji:"));
        panel.add(students);
        panel.add(new JLabel("Nowe imię:"));
        panel.add(name);
        panel.add(new JLabel("Nowe nazwisko:"));
        panel.add(surname);
        panel.add(new JLabel("Nowy Rok Urodzenia:"));
        panel.add(birthYear);
        panel.add(new JLabel("Nowe Punkty:"));
        panel.add(points);
        panel.add(new JLabel("Nowy Stan:"));
        panel.add(combo);
        JOptionPane.showConfirmDialog(null, panel, "Edytowanie Studenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        dataFetcher.editStudent((String) students.getSelectedItem(), name.getText(), surname.getText(), birthYear.getText(), points.getText(), (StudentCondition) combo.getSelectedItem());
        showEmpty();
    }
    private void operationBoxUIAddPoints(){
        JComboBox<Object> students = new JComboBox<>();
        students.addItem("");
        for (Student student  : dataFetcher.data.classContainer.classes.get("").students) {
            students.addItem(student.surname);
        }
        JTextField points = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Wybierz studenta, któremu należy dodać punkty:"));
        panel.add(students);
        panel.add(new JLabel("Ilość dodanych punktów:"));
        panel.add(points);
        JOptionPane.showConfirmDialog(null, panel, "Dodawanie punktów studentowi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        dataFetcher.addPointsToStudent((String) students.getSelectedItem(), points.getText());
        showEmpty();
    }
    private void operationBoxUIRemovePoints(){
        JComboBox<Object> students = new JComboBox<>();
        students.addItem("");
        for (Student student  : dataFetcher.data.classContainer.classes.get("").students) {
            students.addItem(student.surname);
        }
        JTextField points = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Wybierz studenta, któremu należy odjąć punkty:"));
        panel.add(students);
        panel.add(new JLabel("Ilość odjętych punktów:"));
        panel.add(points);
        JOptionPane.showConfirmDialog(null, panel, "Odejmowanie punktów studentowi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        dataFetcher.removePointsFromStudent((String) students.getSelectedItem(), points.getText());
        showEmpty();
    }
    private void operationBoxUIEditClass() {
        try {
            JComboBox<Object> classes = new JComboBox<>();
            classes.addItem("");
            for (String className : dataFetcher.classNames) {
                if (!className.equals("Wszyscy Studenci")) {
                    classes.addItem(className);
                }
            }
            JTextField name = new JTextField("");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Wybierz przedmiot do edycji:"));
            panel.add(classes);
            panel.add(new JLabel("Nowa nazwa przedmiotu:"));
            panel.add(name);
            JOptionPane.showConfirmDialog(null, panel, "Edytowanie Przedmiotu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            dataFetcher.editClass((String) classes.getSelectedItem(), name.getText());
            updateOperationBox();
            showEmpty();
        }catch (Exception e)
        {
            String message = "Something went wrong!\n";
            if (e.getMessage() != null) {
                message += e.getMessage();
                message += "\n";
            }
            message += "Please try again!\n";
            JOptionPane.showMessageDialog(new JFrame(), message);
        }
        showEmpty();
    }
    private void operationBoxUIDeleteStudent() {
        try {
            dataFetcher.removeStudentFromGroup((String) studentsOrClass.getSelectedItem());
        } catch (Exception e) {
            String message = "Something went wrong!\n";
            if (e.getMessage() != null) {
                message += e.getMessage();
                message += "\n";
            }
            message += "Please try again!\n";
            JOptionPane.showMessageDialog(new JFrame(), message);
        }
        showEmpty();
    }
    private void operationBoxUIDeleteClass(){
        try {
            dataFetcher.removeGroupFromRegistry();
            updateStudentOrClassesButton();
            showEmpty();
        } catch (Exception e) {
            String message = "Something went wrong!\n";
            if (e.getMessage() != null) {
                message += e.getMessage();
                message += "\n";
            }
            message += "Please try again!\n";
            JOptionPane.showMessageDialog(new JFrame(), message);
        }
        showEmpty();
    }
    private void createTable() {
        showTable.setModel(dataFetcher);
    }
    private void createStudentsOrClasses() {
        updateStudentOrClassesButton();
        studentsOrClass.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                dataFetcher.studentData.clear();
                showEmpty();
            }
        });
    }
    private void createSortBy() {
        sortBy.setModel(new DefaultComboBoxModel<>(new String[]{"","Alfabetycznie", "Liczba punktów, rosnąco"}));
        sortBy.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                {
                    dataFetcher.studentData.clear();
                    showEmpty();
                }
            }
        });
    }
    private void createSearchTextBox() {
        searchTextBox.addActionListener(e -> showEmpty());
    }
    private void createOperationBox() {
        updateOperationBox();
        operationBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                {
                    String operation = (String) operationBox.getSelectedItem();
                    assert operation != null;
                    switch (operation) {
                        case "Dodaj studenta" -> operationBoxUIAddStudent();
                        case "Edytuj studenta" -> operationBoxUIEditStudent();
                        case "Usuń studenta" -> operationBoxUIDeleteStudent();
                        case "Dodaj punkty" -> operationBoxUIAddPoints();
                        case "Usuń punkty" -> operationBoxUIRemovePoints();
                        case "Dodaj przedmiot" -> operationBoxUIAddClass();
                        case "Edytuj przedmiot" -> operationBoxUIEditClass();
                        case "Usuń przedmiot" -> operationBoxUIDeleteClass();
                    }
                }
            }
        });
    }
}

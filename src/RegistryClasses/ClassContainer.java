package RegistryClasses;

import java.util.*;

public class ClassContainer {
    public Map<String, Class> classes;
    List<Class> emptyClasses;
    public ClassContainer() {
        //System.out.println("New class container created!");
        classes = new TreeMap<>();
        emptyClasses = new ArrayList<>();
    }
    public void renameClass(String oldName, String newName) throws Exception {
        Class oldClass = classes.remove(oldName);
        oldClass.groupName = newName;
        classes.put(newName, oldClass);
    }
    public void addClass(String newClassName, int newCapacity) {
        Class newClass = new Class( newClassName, newCapacity);
        classes.put(newClassName, newClass);
        //System.out.println("Class " + newClassName + " was successfully created!");
    }
    public void removeClass(String newClassName) {
        //System.out.println("Removing class " + newClassName + "...");
        classes.get(newClassName);
        //System.out.println("Class found, removing in progress...");
        classes.remove(newClassName);
        //System.out.println("Class was successfully removed!");
    }
    public void findEmpty() {
        //System.out.println("Searching for empty classes...");
        Set<Map.Entry<String, Class>> entries = classes.entrySet();
        for (Map.Entry<String, Class> entry : entries) {
            if (entry.getValue().studentsNumber == 0) {
                emptyClasses.add(entry.getValue());
            }
        }
        //System.out.println("List of empty classes:");
        for (Class i: emptyClasses) {
            //System.out.println(i);
        }
    }
    public void summary() {
        System.out.println("List of classes:");
        Set<Map.Entry<String, Class>> entries = classes.entrySet();
        for (Map.Entry<String, Class> entry : entries) {
            System.out.println(entry);
        }
    }
}

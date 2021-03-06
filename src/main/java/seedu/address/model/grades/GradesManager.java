package seedu.address.model.grades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import seedu.address.model.StorageController;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.person.Person;
import seedu.address.storage.adapter.XmlAdaptedGrades;
import seedu.address.ui.HtmlTableProcessor;

/**
 * The API of the GradesManager component.
 */
public class GradesManager {
    private ArrayList<Grades> grades = new ArrayList<>();

    public GradesManager() {
        readGradesList();
    }
    public ArrayList<Grades> getGrades() {
        return grades;
    }

    /**
     * Gets grades list from storage and converts it to a Grade array list
     */
    private void readGradesList() {
        ArrayList<XmlAdaptedGrades> xmlGradesList = StorageController.getGradeStorage();
        for (XmlAdaptedGrades xmlGrades : xmlGradesList) {
            grades.add(xmlGrades.toGradeType());
        }
    }

    /**
     * Converts the Gradebook array list and invokes the StorageController to save the current gradebook list to file.
     */
    public void saveGradeList() {
        ArrayList<XmlAdaptedGrades> xmlAdaptedGrades =
                grades.stream().map(XmlAdaptedGrades::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setGradeStorage(xmlAdaptedGrades);
        StorageController.storeData();
    }

    public void clearGrade() {
        grades.clear();
    }

    /**
     This method adds grade of student in Trajectory.
     */
    public String listGrade () {
        StringBuilder sb = new StringBuilder();
        int index = 1;

        sb.append(HtmlTableProcessor.getH3Representation("Grades List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Index", "Module Code", "Component Name", "Admin Number", "Marks"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Grades grade: getGrades()) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(Integer.toString(index++),
                                    grade.getModuleCode(),
                                    grade.getGradeComponentName(),
                                    grade.getAdminNo(),
                                    Float.toString(grade.getMarks())))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());
        return sb.toString();
    }

    /**
     This method gets size of grade.
     */
    public int getGradeSize () {
        int count = getGrades().size();
        return count;
    }

    /**
     This method adds grade component to a module in Trajectory.
     */
    public void addGrade (Grades grade) {
        grades.add(grade);
    }

    /**
     This method finds grade item.
     */
    public Grades findGrade (String moduleCode, String gradebookComponentName) {
        for (Grades gradeList : grades) {
            if (gradeList.getModuleCode().equals(moduleCode)
                    && gradeList.getGradeComponentName().equals(gradebookComponentName)) {
                return gradeList;
            }
        }
        return null;
    }

    /**
     This method deletes all grades of student if gradebook component is deleted.
     */
    public void deleteGrades (Grades grade) {
        if (!grades.isEmpty()) {
            grades.remove(grade);
            Grades gradeCheck = findGrade(grade.getModuleCode(), grade.getGradeComponentName());
            if (gradeCheck != null) {
                deleteGrades(gradeCheck);
            }
        }
    }

    /**
     This method shows grades of students for one grade component in module.
     */
    public void createGraph (LineChart<Number, Number> lineChart, Grades grade) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        // hashmap to store the frequency of element
        Map<Float, Integer> hashMapOfGrades = new HashMap<>();
        for (Grades gradesList : grades) {
            if (gradesList.getModuleCode().equals(grade.getModuleCode())
                    && gradesList.getGradeComponentName().equals(grade.getGradeComponentName())) {
                Integer count = hashMapOfGrades.get(gradesList.getMarks());
                hashMapOfGrades.put(gradesList.getMarks(), (count == null) ? 1 : count + 1);
            }
        }
        // displaying the occurrence of elements in the arraylist
        for (Map.Entry<Float, Integer> val : hashMapOfGrades.entrySet()) {
            series.getData().add(new XYChart.Data<>(val.getKey(), val.getValue()));
        }
        lineChart.getData().add(series);
    }


    /**
     This method checks if module code and component name have empty inputs.
     */
    public boolean isEmpty (String moduleCode, String gradebookComponentName, String adminNo) {
        boolean isEmpty = false;
        if (moduleCode.equals("") || gradebookComponentName.equals("") || adminNo.equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     This method checks if admin no already assigned a grade.
     */
    public boolean isDuplicate (String moduleCode, String gradebookComponentName, String adminNo) {
        boolean isDuplicate = false;
        Grades grade = findAdminNo(moduleCode, gradebookComponentName, adminNo);
        if (grade != null) {
            isDuplicate = true;
        }
        return isDuplicate;
    }

    /**
     This method finds admin no to an assigned grade item in Trajectory.
     */
    public Grades findAdminNo (String moduleCode, String gradebookComponentName, String adminNo) {
        for (Grades grade : grades) {
            if (grade.getModuleCode().equals(moduleCode)
                    && grade.getGradeComponentName().equals(gradebookComponentName)
                    && grade.getAdminNo().equals(adminNo)) {
                return grade;
            }
        }
        return null;
    }

    /**
     This method checks if marks are within acceptable range.
     */
    public boolean isMarksValid (float studentMarks) {
        boolean isMarksValid = false;
        if (studentMarks >= 0 && studentMarks <= 100) {
            isMarksValid = true;
        }
        return isMarksValid;
    }

    /**
     This method checks if student is enrolled to module.
     */
    public boolean isStudentEnrolledToModule (String moduleCode, String adminNo) {
        boolean isStudentEnrolledToModule = false;
        ModuleManager moduleManager = ModuleManager.getInstance();
        Module module = moduleManager.getModuleByModuleCode(moduleCode);
        for (Person personList : module.getEnrolledStudents()) {
            if (personList.getMatricNo().matricNo.equals(adminNo)) {
                isStudentEnrolledToModule = true;
            }
        }
        return isStudentEnrolledToModule;
    }

    /**
     This method checks if grades of all students enrolled to module are assigned to grade component.
     */
    public boolean isGradesComplete (String moduleCode, String componentName) {
        boolean isGradesComplete = true;
        ModuleManager moduleManager = ModuleManager.getInstance();
        Module module = moduleManager.getModuleByModuleCode(moduleCode);
        for (Person personList : module.getEnrolledStudents()) {
            Grades grade = findAdminNo(moduleCode, componentName, personList.getMatricNo().matricNo);
            if (grade == null) {
                isGradesComplete = false;
            }
        }
        return isGradesComplete;
    }
}

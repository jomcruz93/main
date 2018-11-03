package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Student prefixes */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_MATRIC = new Prefix("i/");

    /* Class prefixes */
    public static final Prefix PREFIX_CLASS_NAME = new Prefix("cn/");
    public static final Prefix PREFIX_MAXENROLLMENT = new Prefix("e/");

    /* Course prefixes */
    public static final Prefix PREFIX_COURSE_CODE = new Prefix("c/");
    public static final Prefix PREFIX_COURSE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_COURSE_FACULTY = new Prefix("f/");

    /* Gradebook prefixes */
    public static final Prefix PREFIX_GRADEBOOK_ITEM = new Prefix("cn/");
    public static final Prefix PREFIX_GRADEBOOK_MAXMARKS = new Prefix("mm/");
    public static final Prefix PREFIX_GRADEBOOK_WEIGHTAGE = new Prefix("w/");
    public static final Prefix PREFIX_GRADEBOOK_ITEM_EDIT = new Prefix("en/");

    /* Grade info prefixes */
    public static final Prefix PREFIX_STUDENT_MARKS = new Prefix("m/");

    /* Module prefixes */
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("mc/");
    public static final Prefix PREFIX_MODULE_NAME = new Prefix("mn/");

    /* User prefixes */
    public static final Prefix PREFIX_AUTH_PASSWORD = new Prefix("pw/");

    /* Note prefixes */
    public static final Prefix PREFIX_NOTE_END_DATE = new Prefix("ed/");
    public static final Prefix PREFIX_NOTE_END_TIME = new Prefix("et/");
    public static final Prefix PREFIX_NOTE_FILE_NAME = new Prefix("fn/");
    public static final Prefix PREFIX_NOTE_KEY_WORD = new Prefix("k/");
    public static final Prefix PREFIX_NOTE_LOCATION = new Prefix("lc/");
    public static final Prefix PREFIX_NOTE_START_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_NOTE_START_TIME = new Prefix("st/");
    public static final Prefix PREFIX_NOTE_TITLE = new Prefix("tt/");

    /* Current existing command keywords (first command word)*/
    public static final List<String> FIRST_COMMAND_KEYWORDS = new ArrayList<>(Arrays.asList(
            "class",
            "course",
            "grade",
            "gradebook",
            "module",
            "note",
            "student"
    ));

    /* Current existing command keywords (second command word and beyond) */
    public static final List<String> SECOND_COMMAND_KEYWORDS = new ArrayList<>(Arrays.asList(
            "add",
            "addstudent",
            "delete",
            "delstudent",
            "edit",
            "enrol",
            "export",
            "find",
            "graph",
            "list",
            "listattendance",
            "liststudents",
            "markattendance",
            "modattendance",
            "view"
    ));
}

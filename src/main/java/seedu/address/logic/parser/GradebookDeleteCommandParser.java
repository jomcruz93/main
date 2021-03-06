package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Parses input arguments and creates a new GradebookDeleteCommand object
 */
public class GradebookDeleteCommandParser {
    public static final String MESSAGE_EMPTY_INPUTS = "Module code and gradebook component name cannot be empty";
    public static final String MESSAGE_EMPTY_MODULE_CODE = "Module code cannot be empty";
    public static final String MESSAGE_EMPTY_COMPONENT_NAME = "Component name cannot be empty";
    /**
     * Parses the given {@code String args} of arguments in the context of the GradebookDeleteCommand
     * and returns a GradebookDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookDeleteCommand parse(String args) throws ParseException {
        GradebookManager gradebookManager = new GradebookManager();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradebookDeleteCommand.MESSAGE_USAGE));
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isModuleCodeEmpty = gradebookManager.isModuleCodeEmpty(moduleCodeArg);
        boolean isModuleCodeAndComponentNameEmpty = gradebookManager.isModuleCodeAndComponentNameEmpty(
                moduleCodeArg,
                gradeComponentNameArg);
        boolean isComponentNameEmpty = gradebookManager.isComponentNameEmpty(gradeComponentNameArg);
        if (isModuleCodeAndComponentNameEmpty) {
            throw new ParseException(MESSAGE_EMPTY_INPUTS);
        }
        if (isModuleCodeEmpty) {
            throw new ParseException(MESSAGE_EMPTY_MODULE_CODE);
        }
        if (isComponentNameEmpty) {
            throw new ParseException(MESSAGE_EMPTY_COMPONENT_NAME);
        }
        return new GradebookDeleteCommand(moduleCodeArg, gradeComponentNameArg);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

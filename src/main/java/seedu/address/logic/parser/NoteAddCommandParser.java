package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;


/**
 * Parses input arguments and creates a new NoteAddCommand object
 */
public class NoteAddCommandParser implements Parser<NoteAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the NoteAddCommand
     * and returns a NoteAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_NOTE_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String noteDate = (argMultimap.getValue(PREFIX_NOTE_DATE).isPresent())
                ? argMultimap.getValue(PREFIX_NOTE_DATE).get() : "No Date";

        if (noteDate.trim().length() == 0) {
            noteDate = "No Date";
        }

        Note note = new Note(moduleCode, noteDate);
        return new NoteAddCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

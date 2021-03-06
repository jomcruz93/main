package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;

/**
 * Views the details of a module in Trajectory.
 */
public class ModuleViewCommand extends Command {

    public static final String COMMAND_WORD = "module view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the details of a module in Trajectory. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113";

    public static final String MESSAGE_SUCCESS = "Viewing details of %1$s.";
    private static final String MESSAGE_MODULE_NOT_FOUND = "Module with code %s doesn't exist in Trajectory!";

    private final ModuleCode moduleCode;

    public ModuleViewCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!ModuleManager.getInstance().doesModuleExist(moduleCode.moduleCode)) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode.moduleCode));
        }

        Module module = ModuleManager.getInstance().getModuleByModuleCode(moduleCode.moduleCode);

        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode.moduleCode),
                ModuleManager.getInstance().getModuleAsHtmlRepresentation(module));
    }
}

public enum Commands {

    /**
     * Перечислены команды, которые имеют реализацию на сервере.
     */

    ADD("add",true),
    AVARAGEABOVESEALVL("average_of_meters_above_sea_level",false),
    CLEAR("clear",false),
    EXECUTESCRIPT("execute_script", true),
    EXIT("exit",false),
    HELP("help",false),
    INFO("info",false),
    INSERT("insert",true),
    MINBYCLIMATE("min_by_climate",false),
    PRINTDESCENDING("print_descending",false),
    REMOVEGREATER("remove_greater",true),
    REMOVELOVER("remove_lower",true),
    REPLACEIFGERATER("replace_if_greater",true),
    SAVE("save",false),
    SHOW("show",false),
    UPDATEID("update_id",true);

    /**
     * commandName - название команды в системе
     * isHasArgs - логическая переменная, которая характеризует команду на наличие аругментов
     */
    final private String commandName;
    final private boolean itHasArgs;

    Commands(String commandName, boolean itHasArgs) {
        this.commandName = commandName;
        this.itHasArgs = itHasArgs;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isItHasArgs() {
        return itHasArgs;
    }
}

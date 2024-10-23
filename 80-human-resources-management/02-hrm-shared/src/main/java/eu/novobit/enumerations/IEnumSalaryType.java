package eu.novobit.enumerations;

/**
 * The interface Enum salary type.
 */
public interface IEnumSalaryType {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 40;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * Fixed types.
         */
        FIXED("Fixed"),
        /**
         * Variable types.
         */
        VARIABLE("Variable"),
        ;
        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }

}

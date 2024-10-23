package eu.novobit.enumerations;

/**
 * The interface Enum workflow.
 */
public interface IEnumWorkflow {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 7;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Project types.
         */
//https://kissflow.com/workflow/what-is-a-workflow/#types
        PROJECT("PROJECT"),
        /**
         * Process types.
         */
        PROCESS("PROCESS"),
        /**
         * Case types.
         */
        CASE("CASE");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

package eu.novobit.enumerations;

/**
 * The interface Enum position type.
 */
public interface IEnumPositionType {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 6;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * The Init.
         */
        INIT("Initial state"),
        /**
         * The Final.
         */
        FINAL("Final state"),
        /**
         * Inter types.
         */
        INTER("INTER");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

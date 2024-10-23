package eu.novobit.enumerations;

/**
 * The interface Enum mode.
 */
public interface IEnumMode {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Light types.
         */
        LIGHT("light"),
        /**
         * Dark types.
         */
        DARK("dark"),
        /**
         * Semidark types.
         */
        SEMIDARK("semi-dark");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

package eu.novobit.enumerations;

/**
 * The interface Enum sql event.
 */
public interface IEnumSqlEvent {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Persist types.
         */
        PERSIST("Persist"),
        /**
         * Update types.
         */
        UPDATE("Update"),
        /**
         * Remove types.
         */
        REMOVE("Remove");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

package eu.novobit.enumerations;

/**
 * The interface Enum ws message.
 */
public interface IEnumWSMessage {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 16;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Message types.
         */
        MESSAGE("MESSAGE"),
        /**
         * Status types.
         */
        STATUS("STATUS");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

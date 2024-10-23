package eu.novobit.enumerations;

/**
 * The interface Enum job app event type.
 */
public interface IEnumJobAppEventType {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Meeting types.
         */
        MEETING("Meeting"),
        /**
         * Interview types.
         */
        INTERVIEW("Interview"),
        /**
         * Contract types.
         */
        CONTRACT("Contract");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

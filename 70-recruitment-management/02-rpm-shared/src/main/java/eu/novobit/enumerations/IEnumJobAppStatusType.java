package eu.novobit.enumerations;

/**
 * The interface Enum job app status type.
 */
public interface IEnumJobAppStatusType {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Planned types.
         */
        PLANNED("Planned"),
        /**
         * Done types.
         */
        DONE("Done"),
        /**
         * PostPoint types.
         */
        POSTPOINT("PostPoint"),
        /**
         * Cancelled types.
         */
        CANCELLED("Cancelled");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

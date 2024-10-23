package eu.novobit.enumerations;

/**
 * The interface Enum account origin.
 */
public interface IEnumAccountOrigin {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * Sys admin types.
         */
        SYS_ADMIN("SYS_ADMIN"),
        /**
         * Dom admin types.
         */
        DOM_ADMIN("DOM_ADMIN"),
        /**
         * Signup types.
         */
        SIGNUP("Signup"),
        /**
         * Employee types.
         */
        EMPLOYEE("Employee"),
        /**
         * Resume types.
         */
        RESUME("Resume");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

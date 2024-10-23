package eu.novobit.enumerations;


/**
 * The interface Enum email.
 */
public interface IEnumEmail {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    enum Types implements IEnum {
        /**
         * Created user types.
         */
        CREATED_USER(""),
        /**
         * Reset password types.
         */
        RESET_PASSWORD(""),
        /**
         * Expired password types.
         */
        EXPIRED_PASSWORD(""),
        /**
         * Unmanaged exception types.
         */
        UNMANAGED_EXCEPTION(""),
        /**
         * Validation code types.
         */
        VALIDATION_CODE("");

        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}

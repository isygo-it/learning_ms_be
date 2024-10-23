package eu.novobit.enumerations;

/**
 * The interface Enum template name.
 */
public interface IEnumTemplateName {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 25;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * User created template types.
         */
        USER_CREATED_TEMPLATE("USER_CREATED_TEMPLATE"),
        /**
         * Forgot password template types.
         */
        FORGOT_PASSWORD_TEMPLATE("FORGOT_PASSWORD_TEMPLATE"),
        /**
         * Resume shared template types.
         */
        RESUME_SHARED_TEMPLATE("RESUME_SHARED_TEMPLATE"),
        /**
         * Auth otp template types.
         */
        AUTH_OTP_TEMPLATE("AUTH_OTP_TEMPLATE"),
        /**
         * Job offer shared template types.
         */
        JOB_OFFER_SHARED_TEMPLATE("JOB_OFFER_SHARED_TEMPLATE"),
        /**
         * Wfb updated template types.
         */
        WFB_UPDATED_TEMPLATE("WFB_UPDATED_TEMPLATE"),

        /**
         * Password expire template types.
         */
        PASSWORD_EXPIRE_TEMPLATE("PASSWORD_EXPIRE_TEMPLATE");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

package eu.novobit.enumerations;

/**
 * The interface Enum auth.
 */
public interface IEnumAuth {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 6;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * Token types.
         */
        TOKEN("TOKEN"),
        /**
         * Pwd types.
         */
        PWD("PWD"),
        /**
         * Otp types.
         */
        OTP("OTP"),
        /**
         * Qrc types.
         */
        QRC("QRC");
        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

package eu.novobit.enumerations;

/**
 * The interface Enum app token.
 */
public interface IEnumAppToken {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * The Access.
         */
        ACCESS("Access Token"),
        /**
         * The Refresh.
         */
        REFRESH("Refresh Token"),

        /**
         * The Authority.
         */
        AUTHORITY("Authority Token"),
        /**
         * The Rstpwd.
         */
        RSTPWD("Reset Password Token"),

        /**
         * The Qrc.
         */
        QRC("QR code access token"),

        /**
         * The Tpsw.
         */
        TPSW("Trusted ThirdParty Software Token");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

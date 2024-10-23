package eu.novobit.enumerations;

/**
 * The interface Enum account system status.
 */
public interface IEnumAccountSystemStatus {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Idle types.
         */
        IDLE("IDLE"),
        /**
         * Expired types.
         */
        EXPIRED("Expired"),
        /**
         * Registred types.
         */
        REGISTRED("Registred"),
        /**
         * The Tem locked.
         */
        TEM_LOCKED("Temporarily Locked"),
        /**
         * Locked types.
         */
        LOCKED("Locked");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

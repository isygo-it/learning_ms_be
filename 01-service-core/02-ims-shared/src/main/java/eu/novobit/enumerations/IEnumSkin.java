package eu.novobit.enumerations;

/**
 * The interface Enum skin.
 */
public interface IEnumSkin {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Default types.
         */
        DEFAULT("default"),
        /**
         * Bordered types.
         */
        BORDERED("bordered");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

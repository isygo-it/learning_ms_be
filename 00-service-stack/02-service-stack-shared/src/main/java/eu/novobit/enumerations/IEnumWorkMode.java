package eu.novobit.enumerations;

/**
 * The interface Enum location type.
 */
public interface IEnumWorkMode {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Remote types.
         */
        REMOTE("Remote"),
        /**
         * Hybrid types.
         */
        HYBRID("Hybrid"),
        /**
         * Presential types.
         */
        PRESENTIAL("Presential");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

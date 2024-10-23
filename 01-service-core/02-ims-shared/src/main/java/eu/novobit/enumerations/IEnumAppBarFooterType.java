package eu.novobit.enumerations;

/**
 * The interface Enum app bar footer type.
 */
public interface IEnumAppBarFooterType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Fixed types.
         */
        FIXED("fixed"),
        /**
         * Static types.
         */
        STATIC("static"),
        /**
         * Hidden types.
         */
        HIDDEN("hidden");


        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

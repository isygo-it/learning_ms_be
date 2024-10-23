package eu.novobit.enumerations;

/**
 * The interface Enum gender.
 */
public interface IEnumGender {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 20;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Male types.
         */
        MALE("Male"),
        /**
         * Female types.
         */
        FEMALE("Female"),
        /**
         * Other types.
         */
        OTHER("Other");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

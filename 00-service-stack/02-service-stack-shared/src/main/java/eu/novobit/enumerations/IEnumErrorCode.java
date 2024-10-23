package eu.novobit.enumerations;

/**
 * The interface Enum error code.
 */
public interface IEnumErrorCode {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 16;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Object not found types.
         */
        OBJECT_NOT_FOUND("ObjectNotfound"),
        /**
         * Object duplicated types.
         */
        OBJECT_DUPLICATED("ObjectDuplicated"),
        /**
         * Unique constraint violated types.
         */
        UNIQUE_CONSTRAINT_VIOLATED("UniqueConstraintViolated");
        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

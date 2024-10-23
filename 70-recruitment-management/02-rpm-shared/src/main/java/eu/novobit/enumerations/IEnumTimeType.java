package eu.novobit.enumerations;

/**
 * The interface Enum time type.
 */
public interface IEnumTimeType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Fulltime types.
         */
        FULLTIME("Full-Time"),
        /**
         * Parttime types.
         */
        PARTTIME("Part-Time"),
        ;

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

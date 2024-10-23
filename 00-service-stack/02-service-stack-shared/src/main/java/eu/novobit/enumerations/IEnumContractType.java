package eu.novobit.enumerations;

/**
 * The interface Enum contract type.
 */
public interface IEnumContractType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Cdi types.
         */
        CDI("cdi"),
        /**
         * Cdd types.
         */
        CDD("cdd"),
        /**
         * Internship types.
         */
        INTERNSHIP("internship"),
        /**
         * Interim types.
         */
        INTERIM("interim");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

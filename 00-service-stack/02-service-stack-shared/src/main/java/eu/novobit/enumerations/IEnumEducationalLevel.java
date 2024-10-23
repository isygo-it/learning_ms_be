package eu.novobit.enumerations;


/**
 * The interface Enum educational level.
 */
public interface IEnumEducationalLevel {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 20;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * The High school.
         */
        HIGH_SCHOOL("High School"),
        /**
         * Bachelor types.
         */
        BACHELOR("Bachelor"),
        /**
         * Master types.
         */
        MASTER("Master"),
        /**
         * Doctorate types.
         */
        DOCTORATE("Doctorate"),
        /**
         * The Primary school.
         */
        PRIMARY_SCHOOL("Primary School"),
        /**
         * The Middle school.
         */
        MIDDLE_SCHOOL("Middle School");
        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

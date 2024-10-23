package eu.novobit.enumerations;

/**
 * The interface Enum interview skill type.
 */
public interface IEnumInterviewSkillType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Resume types.
         */
        RESUME("resume"),
        /**
         * Job types.
         */
        JOB("Job"),
        /**
         * New types.
         */
        NEW("new");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

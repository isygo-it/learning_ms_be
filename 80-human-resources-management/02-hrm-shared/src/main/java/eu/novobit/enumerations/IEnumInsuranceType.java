package eu.novobit.enumerations;

/**
 * The interface Enum insurance type.
 */
public interface IEnumInsuranceType {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 40;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * The Health insurance.
         */
        HEALTH_INSURANCE("Health Insurance"),

        /**
         * The Social security.
         */
        SOCIAL_SECURITY("Social Security");
        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }

}

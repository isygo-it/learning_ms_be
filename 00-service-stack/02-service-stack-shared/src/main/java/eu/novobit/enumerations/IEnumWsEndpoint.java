package eu.novobit.enumerations;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * The interface Enum ws endpoint.
 */
public interface IEnumWsEndpoint {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 16;

    /**
     * Gets end point.
     *
     * @param destination the destination
     * @return the end point
     */
    public static IEnumWsEndpoint.Types getEndPoint(String destination) {
        return Arrays.stream(Types.values())
                .filter(type -> StringUtils.containsIgnoreCase(destination, "/" + type.name().toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Notification types.
         */
        NOTIFICATION("Notification"),
        /**
         * The Login.
         */
        LOGIN("Login permission"),
        /**
         * Chat types.
         */
        CHAT("Chat"),
        /**
         * Visio types.
         */
        VISIO("Visio"),
        /**
         * The Free.
         */
        FREE("Free endpoint");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

package eu.novobit.enumerations;

/**
 * The interface Enum menu ihm.
 */
public interface IEnumMenuIhm {

    /**
     * The interface Menu.
     *
     * @param <T> the type parameter
     */
    public interface IMenu<T extends IRootMenu> extends IEnumType<IMenu> {

        /**
         * Gets code.
         *
         * @return the code
         */
        public Long getCode();

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription();

        /**
         * Gets icon.
         *
         * @return the icon
         */
        public String getIcon();

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName();

        /**
         * Gets root menu.
         *
         * @return the root menu
         */
        public T getRootMenu();

        /**
         * Gets url.
         *
         * @return the url
         */
        public String getUrl();
    }

    /**
     * The interface Root menu.
     */
    public interface IRootMenu extends IEnumType<IRootMenu> {

        /**
         * Gets code.
         *
         * @return the code
         */
        public Long getCode();

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription();

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName();

        /**
         * Gets icon.
         *
         * @return the icon
         */
        public String getIcon();

        /**
         * Gets url.
         *
         * @return the url
         */
        public String getUrl();
    }
}

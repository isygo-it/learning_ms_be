package eu.novobit.enumerations;

/**
 * The interface IEnumArticleType.
 */
public interface IEnumArticleType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * Article enum Types : HTML, PDF, VIDEO.
     */
    public enum Types implements IEnumArticleType {
        /**
         * The Html.
         */
        HTML,
        /**
         * The Pdf.
         */
        PDF,
        /**
         * The Video.
         */
        VIDEO;
    }
}

package eu.novobit.enumerations;

import java.io.Serializable;

/**
 * The interface Enum file type.
 *
 * @param <T> the type parameter
 */
public interface IEnumFileType<T> extends Serializable {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 16;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * Text types.
         */
        TEXT("TEXT"),
        /**
         * Html types.
         */
        HTML("HTML"),
        /**
         * Doc types.
         */
        DOC("DOC"),
        /**
         * Docx types.
         */
        DOCX("DOCX"),
        /**
         * Pdf types.
         */
        PDF("PDF");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}

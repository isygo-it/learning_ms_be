package eu.novobit.encrypt.generator;

import eu.novobit.enumerations.IEnumCharSet;

/**
 * The type Resizable key generator.
 */
public class ResizableKeyGenerator extends AbstractKeyGenerator {

    /**
     * Next guid string.
     *
     * @param length      the length
     * @param charSetType the char set type
     * @return the string
     */
    public String nextGuid(int length, IEnumCharSet.Types charSetType) {
        this.setBufferlength(length);
        return nextGuid(charSetType);
    }
}

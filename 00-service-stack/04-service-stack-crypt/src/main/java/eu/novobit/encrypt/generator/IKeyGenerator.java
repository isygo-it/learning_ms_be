package eu.novobit.encrypt.generator;

import eu.novobit.enumerations.IEnumCharSet;

/**
 * The interface Key generator.
 */
public interface IKeyGenerator {
    /**
     * Current guid string.
     *
     * @return the string
     */
    public String currentGuid();

    /**
     * Next guid string.
     *
     * @return the string
     */
    public String nextGuid();

    /**
     * Next guid string.
     *
     * @param charSetType the char set type
     * @return the string
     */
    public String nextGuid(IEnumCharSet.Types charSetType);
}

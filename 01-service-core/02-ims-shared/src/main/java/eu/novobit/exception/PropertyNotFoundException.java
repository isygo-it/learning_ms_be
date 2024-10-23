package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Property not found exception.
 */
@MsgLocale("property.not.found.exception")
public class PropertyNotFoundException extends ManagedException {

    /**
     * Instantiates a new Property not found exception.
     *
     * @param s the s
     */
    public PropertyNotFoundException(String s) {
        super(s);
    }
}

package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;

/**
 * The type Backup command exception.
 */
@MsgLocale("pm.demo.buckup.command.exception")
public class BackupCommandException extends ManagedException {

    /**
     * Instantiates a new Backup command exception.
     *
     * @param message the message
     */
    public BackupCommandException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Backup command exception.
     *
     * @param cause the cause
     */
    public BackupCommandException(Throwable cause) {
        super(cause);
    }
}

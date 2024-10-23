package eu.novobit.exception.handler;


import eu.novobit.exception.ManagedException;
import eu.novobit.exception.UnknownException;
import eu.novobit.i18n.service.LocaleService;
import feign.FeignException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;

/**
 * The type Abstract ctrl exception handler.
 */
@Slf4j
@Data
@Component
public abstract class AbstractCtrlExceptionHandler extends AbstractCtrlExceptionHandlerBuilder implements ExceptionHandler {

    /**
     * The constant UNKNOWN_REASON.
     */
    public static final String UNKNOWN_REASON = "unknown.reason";
    /**
     * The constant OPERATION_FAILED.
     */
    public static final String OPERATION_FAILED = "operation.failed";
    /**
     * The constant UNMANAGED_EXCEPTION_NOTIFICATION.
     */
    public static final String UNMANAGED_EXCEPTION_NOTIFICATION = "unmanaged.exception.notification";

    @Autowired
    private LocaleService localeService;

    public String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    @Override
    public String handleMessage(String message) {
        return getLocaleService().getMessage(message, LocaleContextHolder.getLocale());
    }

    @Override
    public String handleError(Throwable throwable) {
        log.error("<Error>: Error in exception handler:", throwable);
        StringBuilder message = new StringBuilder();
        try {
            if (JpaSystemException.class.isAssignableFrom(throwable.getClass())) {
                throwable = NestedExceptionUtils.getRootCause(throwable);
            }

            if (TransactionSystemException.class.isAssignableFrom(throwable.getClass())
                    && throwable.getCause() != null
                    && RollbackException.class.isAssignableFrom(throwable.getCause().getClass())
                    && throwable.getCause().getCause() != null) {
                throwable = throwable.getCause().getCause();
            }

            if (FeignException.class.isAssignableFrom(throwable.getClass())) {
                message.append(getLocaleService().getMessage(((FeignException) throwable).contentUTF8(), LocaleContextHolder.getLocale()));
            } else if (CannotCreateTransactionException.class.isAssignableFrom(throwable.getClass())) {
                message.append(getLocaleService().getMessage("cannot.create.transaction.exception", LocaleContextHolder.getLocale()));
            } else if (PSQLException.class.isAssignableFrom(throwable.getClass())) {
                Optional<String> keyOptional = this.getExcepMessage().keySet().stream().parallel().filter(throwable.getMessage()::contains).findFirst();
                if (keyOptional.isPresent()) {
                    message.append(localeService.getMessage(this.getExcepMessage().get(keyOptional.get()), LocaleContextHolder.getLocale()));
                } else {
                    message.append(localeService.getMessage(UNKNOWN_REASON, LocaleContextHolder.getLocale())).append(" ").append(this.getStackTrace(throwable));
                }
            } else if (javax.validation.ConstraintViolationException.class.isAssignableFrom(throwable.getClass())) {
                if (!CollectionUtils.isEmpty(((javax.validation.ConstraintViolationException) throwable).getConstraintViolations())) {
                    Iterator<ConstraintViolation<?>> it = ((javax.validation.ConstraintViolationException) throwable).getConstraintViolations().iterator();
                    while (it.hasNext()) {
                        ConstraintViolation cv = it.next();
                        if (StringUtils.hasText(cv.getPropertyPath().toString())) {
                            message.append(localeService.getMessage(cv.getPropertyPath().toString().replace("_", "."), LocaleContextHolder.getLocale())).append(": ");
                        }
                        message.append(localeService.getMessage(cv.getMessage().replace(" ", "."), LocaleContextHolder.getLocale())).append("\n");
                    }
                }
            } else if (EmptyResultDataAccessException.class.isAssignableFrom(throwable.getClass())) {
                message.append(localeService.getMessage("object.not.found", LocaleContextHolder.getLocale()));
            } else if (EntityExistsException.class.isAssignableFrom(throwable.getClass())) {
                message.append(localeService.getMessage("object.already.exists", LocaleContextHolder.getLocale()));
            } else if (PersistenceException.class.isAssignableFrom(throwable.getClass()) || DataIntegrityViolationException.class.isAssignableFrom(throwable.getClass())) {
                Optional<String> keyOptional = Optional.empty();
                if (throwable.getCause() != null && ConstraintViolationException.class.isAssignableFrom(throwable.getCause().getClass())) {
                    if (((ConstraintViolationException) throwable.getCause()).getConstraintName() != null) {
                        keyOptional = this.getExcepMessage().keySet().stream().parallel().filter(((ConstraintViolationException) throwable.getCause()).getConstraintName()::equals).findFirst();
                    } else if (throwable.getCause().getCause() != null && SQLException.class.isAssignableFrom(throwable.getCause().getCause().getClass())) {
                        keyOptional = this.getExcepMessage().keySet().stream().parallel().filter(throwable.getCause().getCause().toString().toLowerCase()::equals).findFirst();
                    }

                    if (keyOptional.isPresent()) {
                        message.append(localeService.getMessage(this.getExcepMessage().get(keyOptional.get()), LocaleContextHolder.getLocale()));
                    } else {
                        message.append(localeService.getMessage(UNKNOWN_REASON, LocaleContextHolder.getLocale())).append(" ").append(this.getStackTrace(throwable));
                    }
                } else if (throwable.getCause() != null && DataException.class.isAssignableFrom(throwable.getCause().getClass())) {
                    SQLException sqlException = ((DataException) throwable.getCause()).getSQLException();
                    if (sqlException != null) {
                        message.append(localeService.getMessage(sqlException.getMessage().toLowerCase().replace(" ", ".")
                                        .replace(":", "")
                                        .replace("(", ".")
                                        .replace(")", "")
                                        .replace("error.value.too.long.for.type.character.varying.", "length.must.be.between.0.and.")
                                , LocaleContextHolder.getLocale())).append("\n");
                    }
                } else if (throwable.getCause() != null && DataException.class.isAssignableFrom(throwable.getCause().getClass()) && throwable.getCause().getCause() != null) {
                    message.append(localeService.getMessage(UNKNOWN_REASON, LocaleContextHolder.getLocale())).append(" ").append((this.getStackTrace(throwable)));
                } else {
                    message.append(localeService.getMessage(UNKNOWN_REASON, LocaleContextHolder.getLocale())).append(" ").append(this.getStackTrace(throwable));
                }
            } else if (ManagedException.class.isAssignableFrom(throwable.getClass())) {
                message.append(getLocaleService().getMessage(((ManagedException) throwable).getMsgLocale(), LocaleContextHolder.getLocale()));
            } else {
                throw new UnknownException(throwable);
            }
        } catch (Throwable e) {
            message.append(getLocaleService().getMessage(UNKNOWN_REASON, LocaleContextHolder.getLocale())).append("\n");
            message.append(getLocaleService().getMessage(UNMANAGED_EXCEPTION_NOTIFICATION, LocaleContextHolder.getLocale())).append("\n");
            processUnmanagedException(this.getStackTrace(e));
        }
        return message.toString();
    }
}

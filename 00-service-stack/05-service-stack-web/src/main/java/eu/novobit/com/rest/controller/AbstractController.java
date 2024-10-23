package eu.novobit.com.rest.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.annotation.CtrlHandler;
import eu.novobit.app.ApplicationContextService;
import eu.novobit.exception.BeanNotFoundException;
import eu.novobit.exception.ExceptionHandlerNotDefinedException;
import eu.novobit.exception.handler.ExceptionHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * The type Abstract controller.
 */
@Slf4j
public abstract class AbstractController implements Controller {

    @Getter
    @Autowired
    private ApplicationContextService applicationContextService;

    private ExceptionHandler exceptionHandler;

    public final ExceptionHandler exceptionHandler() throws BeanNotFoundException, ExceptionHandlerNotDefinedException {
        if (this.exceptionHandler == null) {
            CtrlHandler ctrlHandler = this.getClass().getAnnotation(CtrlHandler.class);
            if (ctrlHandler != null) {
                this.exceptionHandler = applicationContextService.getBean(ctrlHandler.value());
                if (this.exceptionHandler == null) {
                    log.error("<Error>: Exception Handler bean not found");
                    throw new BeanNotFoundException(this.getClass().getSimpleName());
                }
            } else {
                CtrlDef ctrlDef = this.getClass().getAnnotation(CtrlDef.class);
                if (ctrlDef != null) {
                    this.exceptionHandler = applicationContextService.getBean(ctrlDef.handler());
                    if (this.exceptionHandler == null) {
                        log.error("<Error>: Exception Handler bean not found");
                        throw new BeanNotFoundException(this.getClass().getSimpleName());
                    }
                } else {
                    log.error("<Error>: Exception Handler bean not defined, please use CtrlExHandler or CtrlDef annotations");
                    throw new ExceptionHandlerNotDefinedException(this.getClass().getSimpleName());
                }
            }
        }

        return this.exceptionHandler;
    }

    /**
     * Handle exception message string.
     *
     * @param throwable the throwable
     * @return the string
     */
    public String handleExceptionMessage(Throwable throwable) {
        if (exceptionHandler() != null) {
            return exceptionHandler().handleError(throwable);
        }
        return throwable.toString();
    }

    @Override
    public ResponseEntity getBackExceptionResponse(Throwable e) {
        log.error("<Error>: Exception {}", e);
        return ResponseFactory.ResponseInternalError(exceptionHandler().handleError(e));
    }
}

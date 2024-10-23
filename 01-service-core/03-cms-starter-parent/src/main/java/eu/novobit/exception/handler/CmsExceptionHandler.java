package eu.novobit.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Cms exception handler.
 */
@Slf4j
@Component
public class CmsExceptionHandler extends AbstractCtrlExceptionHandler {

    @Override
    public void processUnmanagedException(String message) {
        //Email message error to system admin
        log.error(message);
    }
}

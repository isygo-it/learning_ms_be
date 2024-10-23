package eu.novobit.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Pms exception handler.
 */
@Slf4j
@Component
public class PmsExceptionHandler extends AbstractCtrlExceptionHandler {

    @Override
    public void processUnmanagedException(String message) {
        //Email message error to system admin
        log.error(message);
    }
}

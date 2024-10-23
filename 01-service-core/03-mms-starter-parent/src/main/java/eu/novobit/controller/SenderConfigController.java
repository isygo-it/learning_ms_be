package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.SenderConfigDto;
import eu.novobit.exception.handler.MmsExceptionHandler;
import eu.novobit.factory.SenderFactory;
import eu.novobit.mapper.SenderConfigMapper;
import eu.novobit.model.SenderConfig;
import eu.novobit.service.impl.SenderConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Sender config controller.
 */
//http://localhost:8060/webjars/swagger-ui/index.html#/
//http://localhost:8060/messaging/mms/private/account
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/config/mail")
@CtrlDef(handler = MmsExceptionHandler.class, mapper = SenderConfigMapper.class, service = SenderConfigService.class)
public class SenderConfigController extends MappedCrudController<SenderConfig, SenderConfigDto, SenderConfigDto, SenderConfigService> {

    @Autowired
    private SenderFactory senderFactory;

    @Override
    public SenderConfig afterUpdate(SenderConfig senderConfig) {
        senderFactory.removeSender(senderConfig.getDomain());
        return super.afterUpdate(senderConfig);
    }
}

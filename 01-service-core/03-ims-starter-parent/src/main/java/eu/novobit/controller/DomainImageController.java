package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.dto.data.KmsDomainDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.DomainMapper;
import eu.novobit.model.Domain;
import eu.novobit.remote.kms.KmsDomainService;
import eu.novobit.service.impl.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;

/**
 * The type Domain image controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/domain")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = DomainMapper.class, service = DomainService.class)
public class DomainImageController extends MappedImageController<Domain, DomainDto, DomainDto, DomainService> {

    @Autowired
    private KmsDomainService kmsDomainService;

    @Override
    public Domain afterUpdate(Domain domain) throws Exception {
        ResponseEntity<Boolean> result = kmsDomainService.updateDomain(//RequestContextDto.builder().build(),
                KmsDomainDto.builder()
                        .name(domain.getName())
                        .description(domain.getDescription())
                        .url(domain.getUrl())
                        .adminStatus(domain.getAdminStatus())
                        .build());
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody() && result.getBody()) {
            return super.afterUpdate(domain);
        }
        throw new AccountException("Update domain issue in KMS");
    }
}

package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.KmsDomainControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.KmsDomainDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.mapper.DomainMapper;
import eu.novobit.model.KmsDomain;
import eu.novobit.service.impl.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Kms domain controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/domain")
@CtrlDef(handler = KmsExceptionHandler.class, mapper = DomainMapper.class, service = DomainService.class)
public class KmsDomainController extends MappedCrudController<KmsDomain, KmsDomainDto, KmsDomainDto, DomainService>
        implements KmsDomainControllerApi {

    @Override
    public ResponseEntity<KmsDomainDto> updateAdminStatus(RequestContextDto requestContext,
                                                          String domain,
                                                          IEnumBinaryStatus.Types newStatus) {
        log.info("in update status");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().updateAdminStatus(domain, newStatus)));
        } catch (Throwable e) {
            log.error("<Error>: update Domain Status : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> updateDomain(//RequestContextDto requestContext,
                                                KmsDomainDto domain) {
        log.info("Call update domain " + domain.toString());
        try {
            return ResponseFactory.ResponseOk(crudService().checkIfExists(mapper().dtoToEntity(domain),
                    true));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}

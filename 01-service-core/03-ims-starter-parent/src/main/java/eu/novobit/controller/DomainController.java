package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.DomainControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.dto.data.KmsDomainDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.exception.KmsDomainUpdateException;
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

import java.util.List;

/**
 * The type Domain controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/domain")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = DomainMapper.class, service = DomainService.class)
public class DomainController extends MappedCrudController<Domain, DomainDto, DomainDto, DomainService>
        implements DomainControllerApi {

    @Autowired
    private KmsDomainService kmsDomainService;

    @Override
    public Domain afterUpdate(Domain domain) {
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
        throw new KmsDomainUpdateException("for domain id: " + domain.getId());
    }

    @Override
    public ResponseEntity<DomainDto> updateAdminStatus(RequestContextDto requestContext,
                                                       Long id,
                                                       IEnumBinaryStatus.Types newStatus) {
        log.info("in update status");
        try {
            DomainDto domain = mapper().entityToDto(crudService().updateAdminStatus(id, newStatus));
            ResponseEntity<KmsDomainDto> result = kmsDomainService.updateAdminStatus(RequestContextDto.builder().build(),
                    domain.getName(), newStatus);
            if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
                return ResponseFactory.ResponseOk(domain);
            } else {
                throw new KmsDomainUpdateException("for domain id: " + id);
            }
        } catch (Throwable e) {
            log.error("<Error>: update Domain Status : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<String>> getAllDomainNames(RequestContextDto requestContext) {
        log.info("getAllDomainNames {}", requestContext.getSenderDomain());
        try {
            return ResponseFactory.ResponseOk(crudService().getAllDomainNames(requestContext.getSenderDomain()));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<DomainDto> getByName(RequestContextDto requestContext) {
        log.info("get by name {}", requestContext.getSenderDomain());
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().findByName(requestContext.getSenderDomain())));
        } catch (Throwable e) {
            log.error("<Error>: get by name : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}

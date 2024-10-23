package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.AnnexControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.AnnexDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.AnnexMapper;
import eu.novobit.model.Annex;
import eu.novobit.service.IAnnexService;
import eu.novobit.service.impl.AnnexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * The type Annex controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/annex")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = AnnexMapper.class, service = AnnexService.class)
public class AnnexController extends MappedCrudController<Annex, AnnexDto, AnnexDto, AnnexService>
        implements AnnexControllerApi {

    @Autowired
    private IAnnexService annexService;

    @Override
    public ResponseEntity<List<AnnexDto>> getAnnexByCode(RequestContextDto requestContextDto,
                                                         String code) {
        try {
            List<AnnexDto> list = mapper().listEntityToDto(annexService.findAnnexByCode(code));
            if (!CollectionUtils.isEmpty(list)) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<AnnexDto>> getAnnexByCodeAndReference(RequestContextDto requestContext, String code, String reference) {
        try {
            List<AnnexDto> list = mapper().listEntityToDto(annexService.findAnnexByCodeAndRef(code, reference));
            if (!CollectionUtils.isEmpty(list)) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

}

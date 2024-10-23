package eu.novobit.controller;


import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.PropertyControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.PropertyDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.PropertyMapper;
import eu.novobit.service.IPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * The type Property controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(ImsExceptionHandler.class)
@RequestMapping(path = "/api/private/property")
public class PropertyController extends AbstractController implements PropertyControllerApi {

    @Autowired
    private IPropertyService propertyService;

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public ResponseEntity<PropertyDto> updatePropertyAccount(RequestContextDto requestContextDto,
                                                             String accountCode, PropertyDto property) {
        try {
            return ResponseFactory.ResponseOk(propertyMapper.entityToDto(propertyService.updateProperty(accountCode, propertyMapper.dtoToEntity(property))));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<PropertyDto> getPropertyByAccount(RequestContextDto requestContextDto,
                                                            String accountCode, String guiName, String name) {
        try {
            return ResponseFactory.ResponseOk(propertyMapper.entityToDto(propertyService.getPropertyByAccount(accountCode, guiName, name)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<PropertyDto>> getPropertyByAccountAndGui(RequestContextDto requestContextDto,
                                                                        String accountCode, String guiName) {
        try {
            return ResponseFactory.ResponseOk(propertyMapper.listEntityToDto(propertyService.getPropertyByAccountAndGui(accountCode, guiName)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}

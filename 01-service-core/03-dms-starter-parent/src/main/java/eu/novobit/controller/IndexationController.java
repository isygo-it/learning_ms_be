package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.IndexationApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.exception.handler.DmsExceptionHandler;
import eu.novobit.service.IConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * The type Indexation controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(DmsExceptionHandler.class)
@RequestMapping(path = "/api/private/dms/index")
public class IndexationController extends AbstractController implements IndexationApi {

    @Autowired
    private IConverterService converterService;


    //https://www.baeldung.com/apache-tika
    @Override
    public ResponseEntity<Map<String, Integer>> calcKeysOccurrences(RequestContextDto requestContext,
                                                                    String[] keys, MultipartFile file) {
        try {
            File txtFile = converterService.doConvertPdfToText(file.getInputStream());
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}

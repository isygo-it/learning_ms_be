package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.FileConverterApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.enumerations.IEnumFileType;
import eu.novobit.exception.ConvertFileException;
import eu.novobit.exception.UnsupportedFileTypeException;
import eu.novobit.exception.handler.DmsExceptionHandler;
import eu.novobit.service.IConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

/**
 * The type File converter controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(DmsExceptionHandler.class)
@RequestMapping(path = "/api/private/dms/file/convert")
public class FileConverterController extends AbstractController implements FileConverterApi {

    @Autowired
    private IConverterService converterService;

    @Override
    public ResponseEntity<Resource> convertPdf(//RequestContextDto requestContextDto,
                                               IEnumFileType.Types targetFormat,
                                               MultipartFile file) {
        try {
            File responseFile = null;
            switch (targetFormat) {
                case TEXT -> {
                    responseFile = converterService.doConvertPdfToText(file.getInputStream());
                    break;
                }
                case HTML -> {
                    responseFile = converterService.doConvertPdfToHtml(file.getInputStream());
                    break;
                }
                default -> throw new UnsupportedFileTypeException(targetFormat.meaning());
            }

            if (responseFile != null) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(responseFile.toPath()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + responseFile.getName() + "\"")
                        .body(new UrlResource(responseFile.toURI()));
            } else {
                throw new ConvertFileException(targetFormat.meaning());
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Resource> convertHtml(//RequestContextDto requestContextDto,
                                                IEnumFileType.Types targetFormat,
                                                MultipartFile file) {
        try {
            File responseFile = null;
            switch (targetFormat) {
                case PDF -> {
                    responseFile = converterService.doConvertHtmlToPdf(file.getInputStream());
                    break;
                }
                default -> throw new UnsupportedFileTypeException(targetFormat.meaning());
            }

            if (responseFile != null) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(responseFile.toPath()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + responseFile.getName() + "\"")
                        .body(new UrlResource(responseFile.toURI()));
            } else {
                throw new ConvertFileException(targetFormat.meaning());
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}

package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.config.AppProperties;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.exception.EmptyPathException;
import eu.novobit.exception.MessageTemplateNotFoundException;
import eu.novobit.exception.ResourceNotFoundException;
import eu.novobit.helper.FileHelper;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Template;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.ims.ImsPublicService;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.TemplateRepository;
import eu.novobit.service.ITemplateService;
import eu.novobit.types.TemplateVariables;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;

/**
 * The type Template service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = TemplateRepository.class)
public class TemplateService extends AbstractCrudCodifiableService<Template, TemplateRepository> implements ITemplateService {

    private final AppProperties appProperties;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ImsPublicService publicService;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    /**
     * Instantiates a new Template service.
     *
     * @param appProperties the app properties
     */
    public TemplateService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    @Override
    public Template createTemplate(String senderDomainName, Template template, MultipartFile file) throws IOException {
        Path filePath = Path.of(appProperties.getUploadDirectory() + File.separator + senderDomainName + File.separator + "templates");
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }
        template.setPath(filePath.toString());
        template.setDomain(senderDomainName);
        template = this.create(template);
        Files.copy(file.getInputStream(), filePath.resolve(template.getCode().toLowerCase() + "." + FilenameUtils.getExtension(file.getOriginalFilename())),
                StandardCopyOption.REPLACE_EXISTING);
        return template;
    }

    @Override
    public Template updateTemplate(String senderDomainName, Long id, Template template, MultipartFile file) throws IOException {
        Optional<Template> optional = templateRepository.findById(id);
        if (!optional.isPresent()) {
            throw new MessageTemplateNotFoundException("with id: " + id);
        }

        Path filePath = Path.of(appProperties.getUploadDirectory() + File.separator + senderDomainName + File.separator + "templates");
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        if (file != null && !file.isEmpty()) {
            Files.copy(file.getInputStream(), filePath.resolve(optional.get().getCode().toLowerCase() + "." + FilenameUtils.getExtension(file.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            template.setPath(filePath.toString());
        } else {
            template.setPath(optional.get().getPath());
        }

        return this.update(template);
    }

    @Override
    public void deleteTemplate(String senderDomainName, Long id) throws IOException {
        Optional<Template> optionalTemplate = templateRepository.findById(id);
        if (!optionalTemplate.isPresent()) {
            throw new MessageTemplateNotFoundException("Template not found with id: " + id);
        }

        Template template = optionalTemplate.get();
        String filePath = template.getPath() + File.separator + template.getCode().toLowerCase() + ".ftl";

        if (filePath != null && !filePath.isEmpty()) {
            deleteFile(filePath);
        }

        templateRepository.delete(template);
    }


    private void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource download(String senderDomainName, String fileName) throws IOException {
        Optional<Template> template = templateRepository.findByDomainIgnoreCaseAndCodeIgnoreCase(senderDomainName, fileName);
        if (!template.isPresent()) {
            throw new MessageTemplateNotFoundException("Could not read the file!");
        } else {
            if (StringUtils.hasText(template.get().getPath())) {
                Resource resource = new UrlResource(Path.of(template.get().getPath() + File.separator
                        + template.get().getCode().toLowerCase() + ".ftl").toUri());
                if (!resource.exists()) {
                    throw new ResourceNotFoundException("for template file " + senderDomainName + "/" + fileName);
                }
                return resource;
            } else {
                throw new EmptyPathException("for template file " + senderDomainName + "/" + fileName);
            }
        }
    }

    @Transactional
    @Override
    public String composeMessageBody(String senderDomainName, IEnumTemplateName.Types templateName, Map<String, String> variables) throws IOException, TemplateException {
        Optional<Template> optional = templateRepository.findByDomainIgnoreCaseAndName(senderDomainName, templateName);
        Template template = null;
        if (optional.isPresent()) {
            template = optional.get();
        } else {
            log.warn("Template {} is not present for domain {}, we will create a default one!", templateName, senderDomainName);
            Path filePath = Path.of(appProperties.getUploadDirectory() + File.separator + senderDomainName + File.separator + "templates");
            template = this.create(Template.builder()
                    .domain(senderDomainName)
                    .name(templateName)
                    .description(templateName.meaning())
                    .path(filePath.toString())
                    .build());
            log.info(Path.of("templates", templateName.name().toLowerCase() + ".ftl").toUri().getPath());
            Resource resource = new UrlResource(Path.of("templates", templateName.name().toLowerCase() + ".ftl").toUri());
            if (resource.exists()) {
                log.info("Copying Template resource: {}", "templates" + File.separator + templateName.name().toLowerCase() + ".ftl");
                FileHelper.makeDirectoryIfNotExist(filePath.toString());
                FileUtils.copyURLToFile(resource.getURL(), new File(filePath.toString(), template.getCode().toLowerCase() + "." + FilenameUtils.getExtension(resource.getFilename())));
            } else {
                log.error("<Error>: Template resource not found: {}", "templates" + File.separator + templateName.name().toLowerCase() + ".ftl");
            }
        }

        FileTemplateLoader templateLoader = new FileTemplateLoader(new File(template.getPath()));
        freemarkerConfig.getConfiguration().setTemplateLoader(templateLoader);

        //get sender domain variables
        ResponseEntity<DomainDto> result = publicService.getDomainByName(senderDomainName);
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
            DomainDto domain = result.getBody();
            variables.put(TemplateVariables.V_DOMAIN_URL, domain.getUrl() != null ? domain.getUrl() : "Missed");
            variables.put(TemplateVariables.V_DOMAIN_PHONE, domain.getPhone() != null ? domain.getPhone() : "Missed");
            variables.put(TemplateVariables.V_DOMAIN_EMAIL, domain.getEmail() != null ? domain.getEmail() : "Missed");
            variables.put(TemplateVariables.V_DOMAIN_ADDRESS, domain.getAddress() != null ? domain.getAddress().format() : "Missed");
            variables.put(TemplateVariables.V_DOMAIN_FACEBOOK, domain.getLnk_facebook() != null ? domain.getLnk_facebook() : "Missed");
            variables.put(TemplateVariables.V_DOMAIN_LINKEDIN, domain.getLnk_linkedin() != null ? domain.getLnk_linkedin() : "Missed");
            variables.put(TemplateVariables.V_DOMAIN_XING, domain.getLnk_xing() != null ? domain.getLnk_xing() : "Missed");
        } else {
            variables.put(TemplateVariables.V_DOMAIN_URL, "Missed");
            variables.put(TemplateVariables.V_DOMAIN_PHONE, "Missed");
            variables.put(TemplateVariables.V_DOMAIN_EMAIL, "Missed");
            variables.put(TemplateVariables.V_DOMAIN_ADDRESS, "Missed");
            variables.put(TemplateVariables.V_DOMAIN_FACEBOOK, "Missed");
            variables.put(TemplateVariables.V_DOMAIN_LINKEDIN, "Missed");
            variables.put(TemplateVariables.V_DOMAIN_XING, "Missed");
        }

        return FreeMarkerTemplateUtils.processTemplateIntoString(
                freemarkerConfig.getConfiguration().getTemplate(template.getCode().toLowerCase() + ".ftl"),
                variables);
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Template.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RTMS")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}



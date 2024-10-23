package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.model.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


/**
 * The interface Template service.
 */
public interface ITemplateService extends ICrudServiceMethods<Template> {

    /**
     * Create template template.
     *
     * @param senderDomainName the sender domain name
     * @param template         the template
     * @param file             the file
     * @return the template
     * @throws IOException the io exception
     */
    Template createTemplate(String senderDomainName, Template template, MultipartFile file) throws IOException;

    /**
     * Update template template.
     *
     * @param senderDomainName the sender domain name
     * @param id               the id
     * @param template         the template
     * @param file             the file
     * @return the template
     * @throws IOException the io exception
     */
    Template updateTemplate(String senderDomainName, Long id, Template template, MultipartFile file) throws IOException;

    /**
     * Delete template.
     *
     * @param senderDomainName the sender domain name
     * @param id               the id
     * @throws IOException the io exception
     */
    void deleteTemplate(String senderDomainName, Long id) throws IOException;


    /**
     * Download resource.
     *
     * @param senderDomainName the sender domain name
     * @param fileName         the file name
     * @return the resource
     * @throws IOException the io exception
     */
    Resource download(String senderDomainName, String fileName) throws IOException;

    /**
     * Compose message body string.
     *
     * @param senderDomainName the sender domain name
     * @param templateName     the template name
     * @param variables        the variables
     * @return the string
     * @throws IOException       the io exception
     * @throws TemplateException the template exception
     */
    String composeMessageBody(String senderDomainName,
                              IEnumTemplateName.Types templateName,
                              Map<String, String> variables) throws IOException, TemplateException;
}

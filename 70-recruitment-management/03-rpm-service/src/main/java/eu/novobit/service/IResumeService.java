package eu.novobit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.com.rest.service.ICrudFileService;
import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.AccountModelDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ResumeGlobalStatDto;
import eu.novobit.dto.data.ResumeStatDto;
import eu.novobit.enumerations.IEnumResumeStatType;
import eu.novobit.model.Resume;
import eu.novobit.model.ResumeLinkedFile;
import eu.novobit.model.ResumeShareInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface Resume service.
 */
public interface IResumeService extends ICrudServiceMethods<Resume>, ICrudFileService<Resume>, ICrudImageService<Resume> {

    /**
     * Share with accounts list.
     *
     * @param id          the id
     * @param resumeOwner the resume owner
     * @param account     the account
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    List<ResumeShareInfo> shareWithAccounts(Long id, String resumeOwner, List<AccountModelDto> account) throws JsonProcessingException;

    /**
     * Upload additional file list.
     *
     * @param id    the id
     * @param files the files
     * @return the list
     * @throws IOException the io exception
     */
    List<ResumeLinkedFile> uploadAdditionalFile(Long id, MultipartFile[] files) throws IOException;

    /**
     * Delete additional file boolean.
     *
     * @param id               the id
     * @param originalFileName the original file name
     * @return the boolean
     * @throws IOException the io exception
     */
    boolean deleteAdditionalFile(Long id, String originalFileName) throws IOException;

    /**
     * Find current user resume resume.
     *
     * @param code the code
     * @return the resume
     * @throws IOException the io exception
     */
    Resume getResumeByAccountCode(String code) throws IOException;

    /**
     * Gets resume account code.
     *
     * @param resumeCode the resume code
     * @return the resume account code
     */
    String getResumeAccountCode(String resumeCode);

    /**
     * Create current user resume resume.
     *
     * @param code   the code
     * @param resume the resume
     * @return the resume
     * @throws IOException the io exception
     */
    Resume createResumeForAccount(String code, Resume resume) throws IOException;

    /**
     * Find resume by code resume.
     *
     * @param code the code
     * @return the resume
     */
    Resume findResumeByCode(String code);

    /**
     * Gets global statistics.
     *
     * @param enumStatType   the enum stat type
     * @param requestContext the request context
     * @return the global statistics
     */
    ResumeGlobalStatDto getGlobalStatistics(IEnumResumeStatType.Types enumStatType, RequestContextDto requestContext);

    /**
     * Gets object statistics.
     *
     * @param code the code
     * @return the object statistics
     */
    ResumeStatDto getObjectStatistics(String code, RequestContextDto requestContext);
}

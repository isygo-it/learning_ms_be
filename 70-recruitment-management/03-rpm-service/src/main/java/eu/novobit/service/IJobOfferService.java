package eu.novobit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.AccountModelDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.JobOfferGlobalStatDto;
import eu.novobit.dto.data.JobOfferStatDto;
import eu.novobit.enumerations.IEnumSharedStatType;
import eu.novobit.model.JobLinkedFile;
import eu.novobit.model.JobOffer;
import eu.novobit.model.JobShareInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface Job offer service.
 */
public interface IJobOfferService extends ICrudServiceMethods<JobOffer> {


    /**
     * Find job offers not assigned to resume list.
     *
     * @param resumeCode the resume code
     * @return the list
     */
    List<JobOffer> findJobOffersNotAssignedToResume(String resumeCode);


    /**
     * Upload additional file list.
     *
     * @param id    the id
     * @param files the files
     * @return the list
     * @throws IOException the io exception
     */
    List<JobLinkedFile> uploadAdditionalFile(Long id, MultipartFile[] files) throws IOException;

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
     * Download response entity.
     *
     * @param domain   the domain
     * @param filename the filename
     * @param version  the version
     * @return the response entity
     * @throws IOException the io exception
     */
    ResponseEntity<Resource> download(String domain, String filename, Long version) throws IOException;

    /**
     * Share job list.
     *
     * @param id       the id
     * @param jobOwner the job owner
     * @param account  the account
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    List<JobShareInfo> shareJob(Long id, String jobOwner, List<AccountModelDto> account) throws JsonProcessingException;


    /**
     * Gets global statistics.
     *
     * @param statType the stat type
     * @return the global statistics
     */
    JobOfferGlobalStatDto getGlobalStatistics(IEnumSharedStatType.Types statType, RequestContextDto requestContext);

    /**
     * Gets object statistics.
     *
     * @param code the code
     * @return the object statistics
     */
    JobOfferStatDto getObjectStatistics(String code, RequestContextDto requestContextDto);
}

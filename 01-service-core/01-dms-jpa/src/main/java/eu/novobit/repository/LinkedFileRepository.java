package eu.novobit.repository;


import eu.novobit.model.LinkedFile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Linked file repository.
 */
@Repository
public interface LinkedFileRepository extends JpaPagingAndSortingSASCodifiableRepository<LinkedFile, Long> {

    /**
     * Find by check cancel false list.
     *
     * @return the list
     */
    List<LinkedFile> findByCheckCancelFalse();


    /**
     * Find by domain ignore case and original file name and check cancel false list.
     *
     * @param domain         the domain
     * @param originFileName the origin file name
     * @return the list
     */
    List<LinkedFile> findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalse(String domain, String originFileName);

    /**
     * Find by domain ignore case and original file name optional.
     *
     * @param domain         the domain
     * @param originFileName the origin file name
     * @return the optional
     */
    Optional<LinkedFile> findByDomainIgnoreCaseAndOriginalFileName(String domain, String originFileName);

    /**
     * Find by path and original file name optional.
     *
     * @param path           the path
     * @param originFileName the origin file name
     * @return the optional
     */
    Optional<LinkedFile> findByPathAndOriginalFileName(String path, String originFileName);

    /**
     * Find by domain ignore case and tags containing and check cancel false list.
     *
     * @param domain the domain
     * @param tags   the tags
     * @return the list
     */
    List<LinkedFile> findByDomainIgnoreCaseAndTagsContainingAndCheckCancelFalse(String domain, String tags);

    /**
     * Find by domain ignore case and code ignore case and check cancel false optional.
     *
     * @param domain the domain
     * @param code   the code
     * @return the optional
     */
    Optional<LinkedFile> findByDomainIgnoreCaseAndCodeIgnoreCaseAndCheckCancelFalse(String domain, String code);

    /**
     * Find by domain ignore case and categories in and check cancel false list.
     *
     * @param domain     the domain
     * @param categories the categories
     * @return the list
     */
    List<LinkedFile> findByDomainIgnoreCaseAndCategoriesInAndCheckCancelFalse(String domain, List<String> categories);


    /**
     * Find by domain ignore case and categories in list.
     *
     * @param domain     the domain
     * @param categories the categories
     * @return the list
     */
    List<LinkedFile> findByDomainIgnoreCaseAndCategoriesIn(String domain, List<String> categories);

    Optional<LinkedFile> findById(Long id);

    /**
     * Find by domain ignore case and original file name and check cancel false and version optional.
     *
     * @param domain         the domain
     * @param originFileName the origin file name
     * @param version        the version
     * @return the optional
     */
    Optional<LinkedFile> findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalseAndVersion(String domain, String originFileName, Long version);


}

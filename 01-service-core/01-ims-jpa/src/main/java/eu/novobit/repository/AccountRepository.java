package eu.novobit.repository;

import eu.novobit.enumerations.IEnumAccountSystemStatus;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumLanguage;
import eu.novobit.model.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The interface Account repository.
 */
public interface AccountRepository extends JpaPagingAndSortingSASCodifiableRepository<Account, Long> {

    /**
     * Update account admin status.
     *
     * @param enabled the enabled
     * @param id      the id
     */
    @Modifying
    @Query("update Account u set u.adminStatus = :newStatus  where u.id = :id")
    void updateAccountAdminStatus(@Param("newStatus") IEnumBinaryStatus.Types enabled,
                                  @Param("id") Long id);


    /**
     * Update account isAdmin.
     *
     * @param status the true
     * @param id     the id
     */
    @Modifying
    @Query("update Account u set u.isAdmin = :newStatus  where u.id = :id")
    void updateAccountIsAdmin(@Param("newStatus") boolean status,
                              @Param("id") Long id);

    /**
     * Update language.
     *
     * @param language the language
     * @param id       the id
     */
    @Modifying
    @Query("update Account u set u.language = :language  where u.id = :id")
    void updateLanguage(@Param("language") IEnumLanguage.Types language,
                        @Param("id") Long id);

    /**
     * Find distinct emails by domain list.
     *
     * @param domain the domain
     * @return the list
     */
    @Query("select distinct a.email from Account a where a.domain = :domain")
    List<String> findDistinctEmailsByDomain(@Param("domain") String domain);

    /**
     * Find distinct emails list.
     *
     * @return the list
     */
    @Query("select distinct a.email from Account a")
    List<String> findDistinctEmails();


    @Query(value = "select count(distinct a.code) from t_account a inner join t_connection_tracking tct on a.code=tct.account_code where a.origin like 'RESUME%'", nativeQuery = true)
    Integer countByOriginResume();

    @Query(value = "select count(distinct a.code) from t_account a inner join t_connection_tracking tct on a.code=tct.account_code where a.origin like 'EMPLOYEE%'", nativeQuery = true)
    Integer countByOriginEmployee();
    @Query(value = "select count(distinct a.code) from t_account a inner join t_connection_tracking tct on a.code=tct.account_code where a.origin like 'SYS_ADMIN%' and a.domain = :domain", nativeQuery = true)
    Integer countByOriginAccount(@Param("domain") String domain);
    long countByDomainAndCancelDateNull(String domain);
    Long countByDomainAndCancelDateNullAndSystemStatus(String senderDomain, IEnumBinaryStatus.Types status);

}

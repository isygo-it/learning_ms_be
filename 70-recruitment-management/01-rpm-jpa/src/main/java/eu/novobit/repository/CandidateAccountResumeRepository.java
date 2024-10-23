package eu.novobit.repository;

import eu.novobit.model.AssoAccountResume;

import java.util.Optional;

/**
 * The interface Candidate account resume repository.
 */
public interface CandidateAccountResumeRepository extends JpaPagingAndSortingRepository<AssoAccountResume, Long> {

    /**
     * Find by account code ignore case optional.
     *
     * @param accountCode the account code
     * @return the optional
     */
    Optional<AssoAccountResume> findByAccountCodeIgnoreCase(String accountCode);

}

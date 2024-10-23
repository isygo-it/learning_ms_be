package eu.novobit.repository;

import eu.novobit.model.ResumeShareInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Resume shared info repository.
 */
@Repository
public interface ResumeSharedInfoRepository extends JpaRepository<ResumeShareInfo, Long> {
}

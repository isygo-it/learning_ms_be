package eu.novobit.repository;

import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Application;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The interface Application repository.
 */
public interface ApplicationRepository extends JpaPagingAndSortingSASCodifiableRepository<Application, Long> {

    @Modifying
    @Query("UPDATE Application app SET app.adminStatus = :newStatus WHERE app.id = :id")
    int updateAdminStatusById(@Param("id") Long id,
                              @Param("newStatus") IEnumBinaryStatus.Types newStatus);
}

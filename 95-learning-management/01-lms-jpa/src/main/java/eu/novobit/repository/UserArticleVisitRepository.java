package eu.novobit.repository;


import eu.novobit.model.UserArticleVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserArticleVisitRepository extends JpaRepository<UserArticleVisit, Long> {

    @Query("SELECT uav.article.id FROM UserArticleVisit uav WHERE uav.userId = :userId")
    List<Long> findVisitedArticleIdsByUserId(@Param("userId") Long userId);
}
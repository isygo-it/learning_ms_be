package eu.novobit.service.filter;

import eu.novobit.exception.EmptyJpaFilterException;
import eu.novobit.model.Resume;
import eu.novobit.model.ResumeDetails;
import eu.novobit.model.ResumeEducation;
import eu.novobit.model.ResumeSkills;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Resume filter service.
 */
@Slf4j
@Service
public class ResumeFilterService {

    @Nullable
    @Autowired(required = false)
    private EntityManager entityManager;

    /**
     * Filter resume list.
     *
     * @param filters the filters
     * @return the list
     */
    public List<Resume> filterResume(List<JpaFilter> filters) {
        if (CollectionUtils.isEmpty(filters)) {
            throw new EmptyJpaFilterException("resume");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Resume> query = cb.createQuery(Resume.class);
        Root<Resume> root = query.from(Resume.class);

        Join<Resume, ResumeDetails> details = root.join("details");

        Join<ResumeDetails, ResumeSkills> skills = root.join("skills");
        Join<ResumeDetails, ResumeEducation> educations = root.join("educations");

        List<Predicate> predicates = new ArrayList<>();
        filters.forEach(jf -> {
            Path<String> path = root.get(jf.getName());
            predicates.add(cb.like(path, jf.getValue()));
        });

        query.select(root)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}

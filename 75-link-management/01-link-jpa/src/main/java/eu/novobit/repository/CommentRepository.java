package eu.novobit.repository;

import eu.novobit.model.Comment;

public interface CommentRepository extends JpaPagingAndSortingRepository<Comment, Long> {
}

package eu.novobit.service.impl;


import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.Comment;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.CommentRepository;
import eu.novobit.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = CommentRepository.class)
public class CommentService extends AbstractCrudService<Comment, CommentRepository> implements ICommentService {


}

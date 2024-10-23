package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.Post;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.PostRepository;
import eu.novobit.service.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Post service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = PostRepository.class)
public class PostService extends AbstractCrudService<Post, PostRepository> implements IPostService {

}

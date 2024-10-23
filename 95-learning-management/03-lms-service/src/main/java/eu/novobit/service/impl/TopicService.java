package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Topic;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.TopicRepository;
import eu.novobit.service.ITopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The type Topic service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = TopicRepository.class)
public class TopicService extends AbstractCrudImageService<Topic, TopicRepository> implements ITopicService {
    private final AppProperties appProperties;

    /**
     * Instantiates a new Resume service.
     *
     * @param appProperties the app properties
     */
    public TopicService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }


    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Topic.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("TOP")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

}

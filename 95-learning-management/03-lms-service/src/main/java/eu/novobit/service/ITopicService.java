package eu.novobit.service;

        import eu.novobit.com.rest.service.ICrudImageService;
        import eu.novobit.com.rest.service.ICrudServiceMethods;
        import eu.novobit.model.Topic;

/**
 * The interface Topic service.
 */
public interface ITopicService extends ICrudServiceMethods<Topic>, ICrudImageService<Topic> {

}

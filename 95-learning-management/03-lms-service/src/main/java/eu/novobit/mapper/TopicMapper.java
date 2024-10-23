package eu.novobit.mapper;

        import eu.novobit.dto.data.TopicDto;
        import eu.novobit.model.Topic;
        import org.mapstruct.Mapper;
        import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Topic mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface TopicMapper extends EntityMapper<Topic, TopicDto> {

}

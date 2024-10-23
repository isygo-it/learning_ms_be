package eu.novobit.mapper;

import eu.novobit.dto.data.AnnexDto;
import eu.novobit.model.Annex;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Annex mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AnnexMapper extends EntityMapper<Annex, AnnexDto> {

}

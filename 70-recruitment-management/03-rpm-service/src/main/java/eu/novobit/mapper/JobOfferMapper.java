package eu.novobit.mapper;

import eu.novobit.dto.data.JobOfferDto;
import eu.novobit.model.JobOffer;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Job offer mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface JobOfferMapper extends EntityMapper<JobOffer, JobOfferDto> {
}

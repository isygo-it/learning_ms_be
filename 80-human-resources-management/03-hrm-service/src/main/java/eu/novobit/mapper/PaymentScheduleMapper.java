package eu.novobit.mapper;

import eu.novobit.dto.data.PaymentScheduleDto;
import eu.novobit.model.PaymentSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Payment Schedule mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PaymentScheduleMapper extends EntityMapper<PaymentSchedule, PaymentScheduleDto> {

}

package eu.novobit.async.camel.processor;

import eu.novobit.com.camel.processor.AbstractCamelProcessor;
import eu.novobit.dto.data.TimelineDto;
import eu.novobit.model.Timeline;
import eu.novobit.repository.TimelineRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The type Time line processor.
 */
@Slf4j
@Component
@Qualifier("timeLineProcessor")
public class TimeLineProcessor extends AbstractCamelProcessor<TimelineDto> {

    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public void performProcessor(Exchange exchange, TimelineDto timelineDto) {
        exchange.getIn().setHeader("domain", timelineDto.getDomain());
        exchange.getIn().setHeader("code", timelineDto.getCode());
        timelineRepository.save(Timeline.builder()
                .code(timelineDto.getCode())
                .action(timelineDto.getAction())
                .domain(timelineDto.getDomain())
                .object(timelineDto.getObject())
                .parentCode(timelineDto.getParentCode())
                .build());
        exchange.getIn().setHeader(AbstractCamelProcessor.RETURN_HEADER, true);
    }
}

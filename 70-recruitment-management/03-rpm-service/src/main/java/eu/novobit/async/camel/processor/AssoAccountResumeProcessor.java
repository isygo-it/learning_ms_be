package eu.novobit.async.camel.processor;

import eu.novobit.com.camel.processor.AbstractStringProcessor;
import eu.novobit.dto.data.AssoAccountDto;
import eu.novobit.helper.JsonHelper;
import eu.novobit.model.AssoAccountResume;
import eu.novobit.model.Resume;
import eu.novobit.repository.AssoAccountResumeRepository;
import eu.novobit.service.IResumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The type Asso account resume processor.
 */
@Slf4j
@Component
@Qualifier("assoAccountResumeProcessor")
public class AssoAccountResumeProcessor extends AbstractStringProcessor {
    /**
     * The Resume service.
     */
    @Autowired
    IResumeService iResumeService;
    /**
     * The Asso account resume repository.
     */
    @Autowired
    AssoAccountResumeRepository assoAccountResumeRepository;

    @Override
    public void performProcessor(Exchange exchange, String object) throws Exception {
        AssoAccountDto assoAccountDto = JsonHelper.fromJson(object, AssoAccountDto.class);
        String[] splitOrigin = assoAccountDto.getOrigin().split("-");
        Resume resume = iResumeService.findResumeByCode(splitOrigin[1]);
        if ("RESUME".equals(splitOrigin[0])) {
            assoAccountResumeRepository.save(AssoAccountResume.builder().accountCode(assoAccountDto.getCode()).resume(resume).build());
        }
    }
}

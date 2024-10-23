package eu.novobit.service.impl;

import eu.novobit.model.AppNextCode;
import eu.novobit.repository.AppNextCodeRepository;
import eu.novobit.repository.NextCodeRepository;
import eu.novobit.service.AbstractNextCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Next code service.
 */
@Service
@Transactional
public class NextCodeService extends AbstractNextCodeService<AppNextCode> {

    @Autowired
    private AppNextCodeRepository nextCodeRepository;

    @Override
    public NextCodeRepository nextCodeRepository() {
        return nextCodeRepository;
    }
}
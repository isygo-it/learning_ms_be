package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.Vacation;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.VacationRepository;
import eu.novobit.service.IVacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type vacation service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = VacationRepository.class)
public class VacationService extends AbstractCrudService<Vacation, VacationRepository> implements IVacationService {

}

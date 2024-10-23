package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.config.AppProperties;
import eu.novobit.exception.CalendarNotFoundException;
import eu.novobit.jasycal.ICalendar;
import eu.novobit.jasycal.ICalendarBuilder;
import eu.novobit.jasycal.IEvent;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.VCalendar;
import eu.novobit.model.VCalendarEvent;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.VEventRepository;
import eu.novobit.service.IVEventService;
import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The type V event service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = VEventRepository.class)
public class VEventService extends AbstractCrudCodifiableService<VCalendarEvent, VEventRepository> implements IVEventService {

    private final AppProperties appProperties;

    @Autowired
    private VCalendarService calendarService;

    /**
     * Instantiates a new V event service.
     *
     * @param appProperties the app properties
     */
    public VEventService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public List<VCalendarEvent> findByDomainAndCalendar(String domain, String calendar) {
        return repository().findByDomainIgnoreCaseAndCalendar(domain, calendar);
    }

    /**
     * Find by domain and calendar and code optional.
     *
     * @param domain   the domain
     * @param calendar the calendar
     * @param code     the code
     * @return the optional
     */
    public Optional<VCalendarEvent> findByDomainAndCalendarAndCode(String domain, String calendar, String code) {
        return repository().findByDomainIgnoreCaseAndCalendarAndCodeIgnoreCase(domain, calendar, code);
    }

    @Override
    public VCalendarEvent beforeCreate(VCalendarEvent vCalendarEvent) {
        VCalendar vCalendar = calendarService.findByDomainAndName(vCalendarEvent.getDomain(), vCalendarEvent.getCalendar());
        if (vCalendar == null) {
            if (appProperties.getCreateCalendarIfNotExists()) {
                calendarService.create(VCalendar.builder()
                        .domain(vCalendarEvent.getDomain())
                        .name(vCalendarEvent.getCalendar())
                        .description(vCalendarEvent.getCalendar())
                        .build());
            } else {
                throw new CalendarNotFoundException("with domain/name : " + vCalendarEvent.getDomain() + "/" + vCalendarEvent.getCalendar());
            }
        }
        return super.beforeCreate(vCalendarEvent);
    }

    @Override
    public VCalendarEvent afterCreate(VCalendarEvent vCalendarEvent) {
        VCalendar vCalendar = calendarService.findByDomainAndName(vCalendarEvent.getDomain(), vCalendarEvent.getCalendar());
        if (vCalendar == null) {
            throw new CalendarNotFoundException("with domain/name : " + vCalendarEvent.getDomain() + "/" + vCalendarEvent.getCalendar());
        }

        try {
            ICalendar calendar = ICalendarBuilder.builder()
                    .icsPath(vCalendar.getIcsPath())
                    .build()
                    .calendar()
                    .load()
                    .addEvent(IEvent.builder()
                            .uid(new Uid(vCalendarEvent.getId().toString()))
                            .name(new Name(vCalendarEvent.getName()))
                            .description(new Description(vCalendarEvent.getName()))
                            .summary(new Summary(vCalendarEvent.getName()))
                            .dtStart(new DtStart(vCalendarEvent.getStartDate().toInstant()))
                            .dtEnd(new DtEnd(vCalendarEvent.getEndDate().toInstant()))
                            .build()
                            .event())
                    .store();
        } catch (IOException e) {
            log.error("<Error>: api exception : {} ", e);
        } catch (ParserException e) {
            log.error("<Error>: api exception : {} ", e);
        }
        return super.afterCreate(vCalendarEvent);
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(VCalendarEvent.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("REVENT")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}

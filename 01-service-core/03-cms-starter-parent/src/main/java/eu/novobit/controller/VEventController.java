package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.CalendarEventControllerAPI;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.VCalendarEventDto;
import eu.novobit.exception.CalendarNotFoundException;
import eu.novobit.exception.handler.CmsExceptionHandler;
import eu.novobit.jasycal.ICalendar;
import eu.novobit.jasycal.ICalendarBuilder;
import eu.novobit.jasycal.IEvent;
import eu.novobit.mapper.VCalendarEventMapper;
import eu.novobit.model.VCalendar;
import eu.novobit.model.VCalendarEvent;
import eu.novobit.service.impl.VCalendarService;
import eu.novobit.service.impl.VEventService;
import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The type V event controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = CmsExceptionHandler.class, mapper = VCalendarEventMapper.class, service = VEventService.class)
@RequestMapping(path = "/api/private/calendar/event")
public class VEventController extends MappedCrudController<VCalendarEvent, VCalendarEventDto, VCalendarEventDto, VEventService> implements CalendarEventControllerAPI {

    @Autowired
    private VCalendarService calendarService;

    @Override
    public VCalendarEventDto beforeUpdate(Long id, VCalendarEventDto eventDto) {
        VCalendarEvent event = this.crudService().findById(id);
        if (event != null) {
            VCalendar vCalendar = calendarService.findByDomainAndName(event.getDomain(), event.getCalendar());
            if (vCalendar == null) {
                throw new CalendarNotFoundException("with domain/name : " + event.getDomain() + "/" + event.getCalendar());
            }

            try {
                ICalendar calendar = ICalendarBuilder.builder()
                        .icsPath(vCalendar.getIcsPath())
                        .build()
                        .calendar()
                        .load()
                        .updateEvent(IEvent.builder()
                                .uid(new Uid(eventDto.getId().toString()))
                                .name(new Name(eventDto.getName()))
                                .description(new Description(eventDto.getName()))
                                .summary(new Summary(eventDto.getName()))
                                .dtStart(new DtStart(eventDto.getStartDate().toInstant()))
                                .dtEnd(new DtEnd(eventDto.getEndDate().toInstant()))
                                .build()
                                .event())
                        .store();
            } catch (IOException e) {
                log.error("<Error>: {} ", e);
            } catch (ParserException e) {
                log.error("<Error>: {} ", e);
            }
        }
        return eventDto;
    }

    @Override
    public boolean beforeDelete(Long id) {
        VCalendarEvent event = this.crudService().findById(id);
        if (event != null) {
            VCalendar vCalendar = calendarService.findByDomainAndName(event.getDomain(), event.getCalendar());
            if (vCalendar == null) {
                throw new CalendarNotFoundException("with domain/name : " + event.getDomain() + "/" + event.getCalendar());
            }


            try {
                ICalendar calendar = ICalendarBuilder.builder()
                        .icsPath(vCalendar.getIcsPath())
                        .build()
                        .calendar()
                        .load()
                        .remove(new Uid(id.toString()))
                        .store();
            } catch (IOException e) {
                log.error("<Error>: {} ", e);
            } catch (ParserException e) {
                log.error("<Error>: {} ", e);
            }
        }

        return true;
    }


    @Override
    public ResponseEntity<VCalendarEventDto> eventByDomainAndCalendarAndCode(RequestContextDto requestContext,
                                                                             String domain, String calendar,
                                                                             String code) {
        try {
            Optional<VCalendarEvent> event = this.crudService().findByDomainAndCalendarAndCode(domain, calendar, code);
            if (event.isPresent()) {
                return ResponseFactory.ResponseOk(this.mapper().entityToDto(event.get()));
            } else {
                return ResponseFactory.ResponseNoContent();
            }
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<List<VCalendarEventDto>> getAllByDomainAndCalendarName(RequestContextDto requestContext,
                                                                                 String domain, String calendar) {
        try {
            List<VCalendarEventDto> listEvent =
                    this.mapper().listEntityToDto(this.crudService().findByDomainAndCalendar(domain, calendar));
            if (CollectionUtils.isEmpty(listEvent)) {
                return ResponseFactory.ResponseNoContent();
            } else {
                return ResponseFactory.ResponseOk(listEvent);
            }
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<VCalendarEventDto> saveEvent(//RequestContextDto requestContext,
                                                       VCalendarEventDto event) {
        try {
            return ResponseFactory.ResponseOk(this.mapper().entityToDto(this.crudService().create((this.mapper().dtoToEntity(event)))));
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<VCalendarEventDto> updateEvent(//RequestContextDto requestContext,
                                                         Long id,
                                                         VCalendarEventDto event) {
        try {
            this.crudService().findById(id);
            return ResponseFactory.ResponseOk(this.mapper().entityToDto(this.crudService().update((this.mapper().dtoToEntity(event)))));
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }
}

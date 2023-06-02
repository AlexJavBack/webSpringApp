package com.runmag.web.service;

import com.runmag.web.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long id, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findByEventId(Long eventId);

    void updateEvent(EventDto eventDto);

    void deleteEvent(long eventId);
}

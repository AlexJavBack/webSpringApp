package com.runmag.web.service;

import com.runmag.web.dto.EventDto;

public interface EventService {
    void createEvent(Long id, EventDto eventDto);
}

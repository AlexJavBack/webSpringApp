package com.runmag.web.controller;

import com.runmag.web.dto.ClubDto;
import com.runmag.web.dto.EventDto;
import com.runmag.web.models.Event;
import com.runmag.web.models.User;
import com.runmag.web.security.SecurityUtil;
import com.runmag.web.service.EventService;
import com.runmag.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;
    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }


    @GetMapping("/events")
    public String eventList(Model model){
        User user = new User();
        List<EventDto> event = eventService.findAllEvents();
        String userName = SecurityUtil.getSessionUser();
        if(userName != null){
            user = userService.findByUserName(userName);
            model.addAttribute("user", user);

        }
        model.addAttribute("user", user);
        model.addAttribute("events", event);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model){
        User user = new User();
        EventDto eventDto = eventService.findByEventId(eventId);
        String userName = SecurityUtil.getSessionUser();
        if(userName != null){
            user = userService.findByUserName(userName);
            model.addAttribute("user", user);

        }
        model.addAttribute("club", eventDto);
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }


    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model){
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model){
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId, @ModelAttribute("event")EventDto eventDto
                              ,BindingResult bindingResult
                              ,Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("event", eventDto);
            return "clubs-create";
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @PostMapping(("/events/{eventId}/edit"))
    public String updateEvent(@PathVariable("eventId") Long eventId, @Valid @ModelAttribute("event") EventDto event,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDto eventDto =  eventService.findByEventId(eventId);
        event.setId(eventId);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") long eventId){
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}

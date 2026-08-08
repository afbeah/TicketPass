package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.CreateEventRequest;
import com.ticketpass.backend.dto.EventResponse;
import com.ticketpass.backend.entity.Event;
import com.ticketpass.backend.entity.EventStatus;
import com.ticketpass.backend.entity.User;
import com.ticketpass.backend.repository.EventRepository;
import com.ticketpass.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(
            EventRepository eventRepository,
            UserRepository userRepository
    ) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public EventResponse create(CreateEventRequest request) {

        User organizer = userRepository.findById(request.organizerId())
                .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado"));

        if (request.endDateTime().isBefore(request.startDateTime())) {
            throw new IllegalArgumentException(
                    "A data de término deve ser posterior à data de início"
            );
        }

        Event event = new Event();

        event.setOrganizer(organizer);
        event.setName(request.name());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setStartDateTime(request.startDateTime());
        event.setEndDateTime(request.endDateTime());
        event.setCapacity(request.capacity());
        event.setStatus(EventStatus.DRAFT);

        Event saved = eventRepository.save(event);

        return toResponse(saved);
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getOrganizer().getId(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getStartDateTime(),
                event.getEndDateTime(),
                event.getCapacity(),
                event.getStatus()
        );
    }
}
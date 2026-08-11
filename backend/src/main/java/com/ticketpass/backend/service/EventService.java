package com.ticketpass.backend.service;

import com.ticketpass.backend.client.TicketmasterClient;
import com.ticketpass.backend.dto.CreateEventRequest;
import com.ticketpass.backend.dto.EventResponse;
import com.ticketpass.backend.dto.TicketmasterEventResult;
import com.ticketpass.backend.dto.ticketmaster.TicketmasterEventResponse;
import com.ticketpass.backend.dto.ticketmaster.TicketmasterSearchResponse;
import com.ticketpass.backend.entity.Event;
import com.ticketpass.backend.entity.EventStatus;
import com.ticketpass.backend.entity.User;
import com.ticketpass.backend.repository.EventRepository;
import com.ticketpass.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketmasterClient ticketmasterClient;

    public EventService(
            EventRepository eventRepository,
            UserRepository userRepository,
            TicketmasterClient ticketmasterClient
    ) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.ticketmasterClient = ticketmasterClient;
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

    public List<TicketmasterEventResult> searchExternalEvents(
            String keyword,
            String city
    ) {
        TicketmasterSearchResponse response =
                ticketmasterClient.searchEvents(keyword, city);

        if (response.embedded() == null
                || response.embedded().events() == null) {
            return List.of();
        }

        return response.embedded().events()
                .stream()
                .map(this::toTicketmasterEventResult)
                .toList();
    }

    private TicketmasterEventResult toTicketmasterEventResult(
            TicketmasterEventResponse event
    ) {
        String imageUrl = event.images() != null && !event.images().isEmpty()
                ? event.images().get(0).url()
                : null;

        String date = event.dates() != null && event.dates().start() != null
                ? event.dates().start().localDate()
                : null;

        String time = event.dates() != null && event.dates().start() != null
                ? event.dates().start().localTime()
                : null;

        String venue = null;
        String city = null;
        String state = null;

        if (event.embedded() != null
                && event.embedded().venues() != null
                && !event.embedded().venues().isEmpty()) {

            var ticketmasterVenue = event.embedded().venues().get(0);

            venue = ticketmasterVenue.name();

            if (ticketmasterVenue.city() != null) {
                city = ticketmasterVenue.city().name();
            }

            if (ticketmasterVenue.state() != null) {
                state = ticketmasterVenue.state().name();
            }
        }

        return new TicketmasterEventResult(
                event.id(),
                event.name(),
                event.url(),
                imageUrl,
                date,
                time,
                venue,
                city,
                state
        );
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
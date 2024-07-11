package com.rock.planner.controller;

import com.rock.planner.model.*;
import com.rock.planner.repository.TripRepository;
import com.rock.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository repository;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequest tripRequest){
        Trips trips = new Trips(tripRequest);
        this.repository.save(trips);
        this.participantService.registerParticipantToEvent(tripRequest.email_to_invite(), trips);

        return ResponseEntity.ok(new TripCreateResponse(trips.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trips> getTripDetails(@PathVariable UUID id){
        Optional<Trips> trip = this.repository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trips> updateTrip(@PathVariable UUID id, TripRequest request){
        Optional<Trips> trips = repository.findById(id);

        if(trips.isPresent()){
            Trips newTrip = trips.get();
            newTrip.setEndsAt(LocalDateTime.parse(request.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setStartsAt(LocalDateTime.parse(request.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setDestination(request.destination());

            this.repository.save(newTrip);

            return ResponseEntity.ok(newTrip);
        }
        return trips.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trips> confirmTrip(@PathVariable UUID id){
        Optional<Trips> trips = repository.findById(id);

        if(trips.isPresent()){
            Trips newTrip = trips.get();
            newTrip.setIsConfirmed(true);

            this.repository.save(newTrip);
            this.participantService.triggerConfirmationEmailToEvent(id);

            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantResponse> inviteParticipant(@PathVariable UUID id,
                                                          @RequestBody ParticipantRequest participantRequest){
        Optional<Trips> trips = repository.findById(id);

        if(trips.isPresent()){
            Trips newTrip = trips.get();

            ParticipantResponse response = this.participantService
                    .registerParticipantToEvent(participantRequest.email(), newTrip);

            if(newTrip.getIsConfirmed()) this.participantService
                    .triggerConfirmationEmailToEvent(participantRequest.email());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/trips/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id){
        List<ParticipantData> participantList = this.participantService.getAllParticipantFromEvent(id);

        return ResponseEntity.ok(participantList);
    }
}

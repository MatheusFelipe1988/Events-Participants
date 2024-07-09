package com.rock.planner.controller;

import com.rock.planner.model.TripCreateResponse;
import com.rock.planner.model.TripRequest;
import com.rock.planner.model.Trips;
import com.rock.planner.repository.TripRepository;
import com.rock.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        this.participantService.registerParticipantToEvent(tripRequest.email_to_invite(), trips.getId());
        return ResponseEntity.ok(new TripCreateResponse(trips.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trips> getTripDetails(@PathVariable UUID id){
        Optional<Trips> trip = this.repository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

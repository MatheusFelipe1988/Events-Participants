package com.rock.planner.controller;

import com.rock.planner.model.Participant;
import com.rock.planner.model.ParticipantRequest;
import com.rock.planner.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantRepository repository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequest request){
        Optional<Participant> participant = this.repository.findById(id);

        if(participant.isPresent()){
            Participant newParticipant = participant.get();
            newParticipant.setConfirmed(true);
            newParticipant.setName(request.name());

            this.repository.save(newParticipant);

            return ResponseEntity.ok(newParticipant);
        }

        return ResponseEntity.notFound().build();
    }

}

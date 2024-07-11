package com.rock.planner.service;

import com.rock.planner.model.Participant;
import com.rock.planner.model.ParticipantData;
import com.rock.planner.model.ParticipantResponse;
import com.rock.planner.model.Trips;
import com.rock.planner.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantToEvent(List<String> participationToInvite, Trips trips){
        List<Participant> participants = participationToInvite.stream()
                .map(email -> new Participant(email, trips)).toList();

        this.participantRepository.saveAll(participants);

        System.out.println(participants.get(0).getId());
    }

    public ParticipantResponse registerParticipantToEvent(String email, Trips trips){
        Participant participant = new Participant(email, trips);

        this.participantRepository.save(participant);

        return new ParticipantResponse(participant.getId());
    }

    public void triggerConfirmationEmailToEvent(UUID tripId){}
    public void triggerConfirmationEmailToEvent(String email){}

    public List<ParticipantData> getAllParticipantFromEvent(UUID tripId){
        return this.participantRepository.findByTripId(tripId).stream().map(participant ->
                new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(),
                        participant.getTrips().getIsConfirmed())).toList();
    }
}

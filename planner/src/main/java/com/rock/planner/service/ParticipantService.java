package com.rock.planner.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {
    public void registerParticipantToEvent(List<String> participationToInvite, UUID id){}
    public void triggerConfirmationEmailToEvent(UUID tripId){}
}

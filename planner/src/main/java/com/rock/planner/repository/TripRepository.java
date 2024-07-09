package com.rock.planner.repository;

import com.rock.planner.model.Trips;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trips, UUID> {
}

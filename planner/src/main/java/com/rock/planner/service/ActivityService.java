package com.rock.planner.service;

import com.rock.planner.model.*;
import com.rock.planner.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequest request, Trips trips){
        Activity newActivity = new Activity(request.title(), request.occurs_at(), trips);

        this.repository.save(newActivity);

        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesFromId(UUID tripsId) {
        return this.repository.findByTripId(tripsId).stream().map(activity -> new ActivityData(activity.getId(),
                activity.getTitle(), activity.getOccursAt())).toList();
    }
}

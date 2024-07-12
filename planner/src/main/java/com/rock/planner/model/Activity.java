package com.rock.planner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "occurs_at",nullable = false)
    private LocalDateTime occursAt;

    @Column(name = "title",nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "trips_id", nullable = false)
    private Trips trips;

    public Activity(String title, String occursAt, Trips trips){
        this.title = title;
        this.occursAt = LocalDateTime.parse(occursAt, DateTimeFormatter.ISO_DATE_TIME);
        this.trips = trips;
    }
}

package com.rock.planner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activies")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "occurs_at",nullable = false)
    private LocalDateTime occursAt;

    @Column(name = "title",nullable = false)
    private String title;

    @ManyToOne
    @Column(name = "trip_id",nullable = false)
    private Trips trips;
}

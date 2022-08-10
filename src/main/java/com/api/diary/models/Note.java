package com.api.diary.models;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
public class Note {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @Column(name = "note_description")
    private String description;

    @Column(name = "note_creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "note_price", nullable = false)
    private Double price;

    @Column(name = "note_expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "note_days_remaining", nullable = false)
    private Integer daysRemaining;

    @Column(name = "note_type", nullable = false)
    private String type;

    @Column(name = "note_expired", nullable = false)
    private Boolean expired;

    @Column(name = "note_paid")
    private Boolean paid;
}

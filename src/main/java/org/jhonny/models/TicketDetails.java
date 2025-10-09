package org.jhonny.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal price;

    private DayOfWeek dayOfWeek;

    private LocalDate dateOfGame;

    private LocalTime hour;

    @ManyToOne
    @JoinColumn(name = "gameId")
    @JsonIgnore
    private Games game;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    @JsonIgnore
    private Tickets ticket;
}

package org.jhonny.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "GameSchedule",
            joinColumns = @JoinColumn(name = "gameId"),
            inverseJoinColumns = @JoinColumn(name = "scheduleId")
    )
    private List<Schedule> schedules = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<TicketDetail> tickets;

}

package ru.telgram.jokebot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode()
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Joke_Visitor")
public class JokeVisitor {

    @Id
    @SequenceGenerator(name = "joke_visitor_seq", sequenceName = "joke_visitor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "joke_visitor_seq")
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "joke_id")
    private Joke jokeId;

    @Column(name = "date")
    private Date visitAt;

}

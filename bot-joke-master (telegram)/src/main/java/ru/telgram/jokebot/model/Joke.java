package ru.telgram.jokebot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Jokes")
@Table(name = "Jokes")
public class Joke {

    @Id
    @SequenceGenerator(name = "joke_seq", sequenceName = "joke_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "joke_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    public Joke(String text, Date createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    @OneToMany(mappedBy = "jokeId", cascade = CascadeType.ALL)
    private List<JokeVisitor> jokeVisitor;
}

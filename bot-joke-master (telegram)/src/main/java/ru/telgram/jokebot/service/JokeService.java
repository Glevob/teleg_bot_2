package ru.telgram.jokebot.service;

import ru.telgram.jokebot.model.Joke;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JokeService {
    void registerJoke(Joke joke);
    List<Joke> getAllJokes();
    Optional<Joke> getJokeById(Long id);
    boolean deleteJokeById(Long id);
    void updateJoke(Joke joke);
    Joke getRandomJoke();
    void logJokeCall(Long userId, Long jokeId);// Метод для регистрации вызова анекдота пользователем
    int getLikes(Long id);
    public List<Joke> getTop5Jokes();

}
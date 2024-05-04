package ru.telgram.jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.telgram.jokebot.model.Joke;
import ru.telgram.jokebot.model.JokeVisitor;
import ru.telgram.jokebot.repository.JokesRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JokeServiceImpl implements JokeService {

    private final JokesRepository jokesRepository;

    @Override
    public void registerJoke(Joke joke) {
        jokesRepository.save(joke);
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokesRepository.findAll();
    }

    @Override
    public Optional<Joke> getJokeById(Long id) {
        Optional<Joke> jokeOptional = jokesRepository.findById(id);
        jokeOptional.ifPresent(joke -> {
            joke.getJokeVisitor().add(new JokeVisitor(null, joke, new Date()));
            jokesRepository.saveAndFlush(joke);
        });
        return jokeOptional;
    }

    @Override
    public boolean deleteJokeById(Long id) {
        Optional<Joke> jokeOptional = jokesRepository.findById(id);
        if (jokeOptional.isPresent()) {
            jokesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void updateJoke(Joke joke) {
        Optional<Joke> existingJokeOptional = jokesRepository.findById(joke.getId());
        if (existingJokeOptional.isPresent()) {
            Joke existingJoke = existingJokeOptional.get();
            existingJoke.setText(joke.getText());
            jokesRepository.save(existingJoke);
        }
    }

    @Override
    public Joke getRandomJoke() {
        return jokesRepository.findRandomJoke(); // Используем метод из репозитория
    }



    // Метод для page всех шуток
    @Override
    public Page<Joke> getJokes(Pageable pageable) {
        return jokesRepository.findAll(pageable);
    }

    // Метод для page топ-5 шуток по количеству посетителей
    @Override
    public Page<Joke> getTopJokes(Pageable pageable) {
        return jokesRepository.findByOrderByJokeVisitorDesc(pageable);
    }


}
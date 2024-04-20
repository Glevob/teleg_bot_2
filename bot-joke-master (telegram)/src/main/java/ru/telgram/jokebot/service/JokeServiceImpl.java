package ru.telgram.jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.telgram.jokebot.model.Joke;
import ru.telgram.jokebot.repository.JokesRepository;

import java.util.Comparator;
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
        Optional<Joke> joke = jokesRepository.findById(id);
        joke.ifPresent(Joke::increaseCallCount); // Увеличение частоты появления шутки
        return joke;
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
        long count = jokesRepository.count();
        if (count == 0) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt((int) count);
        Page<Joke> page = jokesRepository.findAll(PageRequest.of(randomIndex, 1));
        return page.getContent().get(0);
    }


    @Override
    public void logJokeCall(Long userId, Long jokeId) {
        Optional<Joke> joke = jokesRepository.findById(jokeId);
        joke.ifPresent(Joke::increaseCallCount); // Увеличение частоты появления шутки при вызове
    }
    @Override
    public int getLikes(Long id) {
        Optional<Joke> optionalJoke = getJokeById(id);
        return optionalJoke.map(Joke::getLikes).orElse(0);
    }

    // Метод для получения топ 5 шуток
    @Override
    public List<Joke> getTop5Jokes() {
        List<Joke> top5Jokes = jokesRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(joke -> -getLikes(joke.getId())))
                .limit(5)
                .collect(Collectors.toList());
        return top5Jokes;
    }
}
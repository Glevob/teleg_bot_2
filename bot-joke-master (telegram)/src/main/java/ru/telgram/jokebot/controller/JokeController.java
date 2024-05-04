package ru.telgram.jokebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.telgram.jokebot.model.Joke;
import ru.telgram.jokebot.service.JokeService;
import ru.telgram.jokebot.service.TelegramBotService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;
    private final TelegramBotService telegramBotService; // Внедряем TelegramBotService

    //POST /jokes - создание новой шутки
    @PostMapping
    ResponseEntity<Void> registerJoke(@RequestBody Joke joke){
        joke.setCreatedAt(new Date());
        jokeService.registerJoke(joke);
        return ResponseEntity.ok().build();
    }

    //GET /jokes - выдача всех шуток
    @GetMapping
    public ResponseEntity<Page<Joke>> getJokes(Pageable pageable) {
        return ResponseEntity.ok(jokeService.getJokes(pageable));
    }

    //GET /jokes/id - выдача шутки с определенным id
    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id){
        return jokeService.getJokeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //DELETE /jokes/id - удаление шутки
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteJokeById(@PathVariable Long id) {
        if (jokeService.deleteJokeById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PUT /jokes/id - изменение шутки
    @PutMapping("/{id}")
    ResponseEntity<Void> updateJoke(@PathVariable Long id, @RequestBody Joke updatedJoke) {
        Joke existingJoke = new Joke();
        existingJoke.setId(id);
        existingJoke.setText(updatedJoke.getText());
        jokeService.updateJoke(existingJoke);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top")
    public ResponseEntity<Page<Joke>> getTopJokes(Pageable pageable) {
        return ResponseEntity.ok(jokeService.getTopJokes(pageable));
    }
}
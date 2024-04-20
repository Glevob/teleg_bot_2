package ru.telgram.jokebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.telgram.jokebot.model.Joke;
import ru.telgram.jokebot.service.JokeService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;
    //private final TelegramBotService telegramBotService; // Внедряем TelegramBotService

    //POST /jokes - создание новой шутки
    @PostMapping
    ResponseEntity<Void> registerJoke(@RequestBody Joke joke){
        joke.setCreatedAt(new Date());
        jokeService.registerJoke(joke);
        return ResponseEntity.ok().build();
    }

    //GET /jokes - выдача всех шуток
    @GetMapping
    ResponseEntity<List<Joke>> getJokes(){
        return ResponseEntity.ok(jokeService.getAllJokes());
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

    // POST /jokes/{userId}/call - регистрация вызова анекдота пользователем
    @PostMapping("/{userId}/call")
    ResponseEntity<Void> logJokeCall(@PathVariable Long userId, Long jokeId) {
        jokeService.logJokeCall(userId, jokeId);
        return ResponseEntity.ok().build();
    }

    /*// GET /jokes/top5 - получение топ-5 самых популярных анекдотов
    @GetMapping("/top5")
    ResponseEntity<List<Joke>> getTop5Jokes() {
        List<Joke> top5Jokes = jokeService.getTop5Jokes();
        return ResponseEntity.ok(top5Jokes);
    }*/
}
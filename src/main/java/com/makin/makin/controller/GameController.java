package com.makin.makin.controller;

import com.makin.makin.model.Game;
import com.makin.makin.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGamesByName(@RequestParam String name) {
        List<Game> games = gameService.getGamesByName(name);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestParam("banner") MultipartFile banner, @RequestParam("name") String name) {
        try {
            // Salva a foto do banner no Amazon S3 e cria um novo jogo
            Game newGame = gameService.addGameWithBanner(name, banner);

            if (newGame != null) {
                return new ResponseEntity<>(newGame, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

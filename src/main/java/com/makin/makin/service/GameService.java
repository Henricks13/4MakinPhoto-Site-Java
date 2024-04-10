package com.makin.makin.service;

import com.makin.makin.model.Game;
import com.makin.makin.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Método para buscar todos os jogos
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    // Método para buscar jogo pelo nome
    public List<Game> getGamesByName(String name) {
        return gameRepository.findByNameContainingIgnoreCase(name);
    }

    // Método para adicionar um novo jogo
    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    // Método para deletar um jogo por ID
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }
}

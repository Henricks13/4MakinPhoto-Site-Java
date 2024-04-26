package com.makin.makin.service;

import com.makin.makin.model.Game;
import com.makin.makin.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MultipartFile banner;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllGames() {
        // Given
        List<Game> games = new ArrayList<>();
        games.add(new Game());
        games.add(new Game());
        Mockito.when(gameRepository.findAll()).thenReturn(games);

        // When
        List<Game> result = gameService.getAllGames();

        // Then
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testGetGamesByName() {
        // Given
        String name = "Test";
        List<Game> games = new ArrayList<>();
        games.add(new Game());
        Mockito.when(gameRepository.findByNameContainingIgnoreCase(name)).thenReturn(games);

        // When
        List<Game> result = gameService.getGamesByName(name);

        // Then
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testAddGame() {
        // Given
        Game game = new Game();
        Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        // When
        Game result = gameService.addGame(game);

        // Then
        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteGameById() {
        // Given
        Long id = 1L;

        // When
        gameService.deleteGameById(id);

        // Then
        Mockito.verify(gameRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testAddGameWithBanner() throws IOException {
        // Given
        String name = "Test";
        Game game = new Game();
        Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);
        Mockito.when(banner.getOriginalFilename()).thenReturn("banner.jpg");

        // When
        Game result = gameService.addGameWithBanner(name, banner);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getBannerUrl());
    }
}

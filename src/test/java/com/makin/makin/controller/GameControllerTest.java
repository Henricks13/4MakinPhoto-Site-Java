package com.makin.makin.controller;

import com.makin.makin.model.Game;
import com.makin.makin.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private List<Game> gameList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameList = new ArrayList<>();
        Game game1 = new Game();
        game1.setId(1L);
        game1.setName("Game 1");
        game1.setBannerUrl("banner1.jpg");

        Game game2 = new Game();
        game2.setId(2L);
        game2.setName("Game 2");
        game2.setBannerUrl("banner2.jpg");

        gameList.add(game1);
        gameList.add(game2);
    }

    @Test
    void getAllGames_ReturnsListOfGames() {
        when(gameService.getAllGames()).thenReturn(gameList);
        ResponseEntity<List<Game>> responseEntity = gameController.getAllGames();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(gameList, responseEntity.getBody());
    }

    @Test
    void searchGamesByName_ReturnsListOfGames() {
        String name = "Game 1";
        when(gameService.getGamesByName(name)).thenReturn(List.of(gameList.get(0)));
        ResponseEntity<List<Game>> responseEntity = gameController.searchGamesByName(name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(List.of(gameList.get(0)), responseEntity.getBody());
    }

    @Test
    void addGame_ReturnsCreatedResponse() throws Exception {
        String name = "New Game";
        MultipartFile banner = new MockMultipartFile("banner", "banner.jpg", "image/jpeg", new byte[0]);
        Game newGame = new Game();
        newGame.setName(name);
        newGame.setBannerUrl("banner.jpg");
        when(gameService.addGameWithBanner(name, banner)).thenReturn(newGame);
        ResponseEntity<Game> responseEntity = gameController.addGame(banner, name);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("New Game", responseEntity.getBody().getName());
    }

    @Test
    void deleteGame_ReturnsNoContentResponse() {
        Long id = 1L;
        ResponseEntity<Void> responseEntity = gameController.deleteGame(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(gameService, times(1)).deleteGameById(id);
    }
}

package com.makin.makin.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makin.makin.model.Game;
import com.makin.makin.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public GameService(GameRepository gameRepository, AmazonS3 amazonS3, @Value("${aws.s3.bucket}") String bucketName) {
        this.gameRepository = gameRepository;
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getGamesByName(String name) {
        return gameRepository.findByNameContainingIgnoreCase(name);
    }

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }

    public Game addGameWithBanner(String name, MultipartFile banner) {
        try {
            // Cria um novo jogo com o nome fornecido
            Game game = new Game();
            game.setName(name);

            // Salva a foto do banner no Amazon S3 e define a URL do banner no jogo
            String bannerUrl = saveBannerToS3(banner);
            game.setBannerUrl(bannerUrl);

            // Salva o jogo no banco de dados
            return gameRepository.save(game);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private String saveBannerToS3(MultipartFile banner) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        banner.transferTo(tempFile);
        String fileName = UUID.randomUUID().toString() + "_" + banner.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, tempFile));
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
}

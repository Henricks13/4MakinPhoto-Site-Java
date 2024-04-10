package com.makin.makin.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.makin.makin.model.Game;
import com.makin.makin.model.Photo;
import com.makin.makin.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, AmazonS3 amazonS3, @Value("${aws.s3.bucket}") String bucketName) {
        this.photoRepository = photoRepository;
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public List<Photo> getAllPhotosByGameId(Long gameId) {
        return photoRepository.findByGameId(gameId);
    }

    public Optional<Photo> addPhoto(Long gameId, MultipartFile file) {
        try {
            // Criar um arquivo temporário para a foto
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);

            // Gerar um nome único para o arquivo
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Fazer upload da foto para o Amazon S3
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, tempFile));

            // Construir a URL do objeto carregado no Amazon S3
            String url = amazonS3.getUrl(bucketName, fileName).toString();

            // Criar um novo objeto Photo com a URL e o objeto Game
            Photo photo = new Photo();
            photo.setUrl(url);

            // Criar um objeto Game com o ID recebido e setar na foto
            Game game = new Game();
            game.setId(gameId);
            photo.setGame(game);

            // Salvar o objeto Photo no banco de dados
            return Optional.of(photoRepository.save(photo));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Photo> getPhotoById(Long photoId) {
        return photoRepository.findById(photoId);
    }
}

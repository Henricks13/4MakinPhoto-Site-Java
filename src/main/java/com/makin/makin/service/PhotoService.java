package com.makin.makin.service;

import com.makin.makin.model.Photo;
import com.makin.makin.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> getAllPhotosByGameId(Long gameId) {
        return photoRepository.findByGameId(gameId);
    }

    public Optional<Photo> addPhoto(Photo photo) {
        // Adicione lógica de validação, se necessário
        return Optional.of(photoRepository.save(photo));
    }

    public Optional<Photo> getPhotoById(Long photoId) {
        return photoRepository.findById(photoId);
    }
}

package com.makin.makin.controller;

import com.makin.makin.model.Photo;
import com.makin.makin.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Photo>> getAllPhotosByGameId(@PathVariable Long gameId) {
        List<Photo> photos = photoService.getAllPhotosByGameId(gameId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Photo> addPhoto(@RequestParam("gameId") Long gameId, @RequestParam("file") MultipartFile file) {
        Optional<Photo> savedPhoto = photoService.addPhoto(gameId, file);
        return savedPhoto.map(photo -> new ResponseEntity<>(photo, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long id) {
        Optional<Photo> photo = photoService.getPhotoById(id);
        return photo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

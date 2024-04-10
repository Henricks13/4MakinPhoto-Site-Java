package com.makin.makin.controller;

import com.makin.makin.model.Photo;
import com.makin.makin.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo) {
        Photo newPhoto = photoService.addPhoto(photo).orElse(null);
        if (newPhoto != null) {
            return new ResponseEntity<>(newPhoto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long id) {
        Photo photo = photoService.getPhotoById(id).orElse(null);
        if (photo != null) {
            return new ResponseEntity<>(photo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

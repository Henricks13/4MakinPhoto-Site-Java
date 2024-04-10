package com.makin.makin.repository;

import com.makin.makin.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByGameId(Long gameId);
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário
}

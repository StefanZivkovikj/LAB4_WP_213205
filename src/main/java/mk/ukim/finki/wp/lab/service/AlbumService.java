package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Album;

import java.util.List;

public interface AlbumService {

    List<Album> findAll();

    Album findById(Long albumId);

    Album save(Album album);

    void deleteById(Long albumId);
}

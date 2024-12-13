package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;

public interface SongService {
    List<Song> listSongs();

    void addArtistToSong(Artist artist, Song song);

    Song findByTrackId(String trackId);

    List<Song> findAll();

    void addSong(Song song);

    Song findById(Long songId);

    void removeSong(Long id);

    void deleteByTrackId(String trackId);

    void update(Song song);

    void save(Song song);

    int generateNextTrackId();

    // New method for fetching songs by album
    List<Song> findAllByAlbum_Id(Long albumId);

    String findMaxTrackId();

//     List<Song> findByAlbumId(Long albumId);


}

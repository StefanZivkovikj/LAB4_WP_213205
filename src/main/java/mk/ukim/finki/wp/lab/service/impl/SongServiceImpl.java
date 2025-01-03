package mk.ukim.finki.wp.lab.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.inMemoryArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository; // Update to use SongRepository
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository; // Use the SongRepository
    private final inMemoryArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository, inMemoryArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll(); // Delegate to JPA repository
    }

    @Override
    public void addSong(Song song) {
        songRepository.save(song); // Save the new song
    }

    @Override
    public Song findById(Long songId) {
        return songRepository.findById(songId).orElse(null); // Fetch by ID
    }

    @Override
    public void removeSong(Long id) {
        songRepository.deleteById(id); // Delete by ID
    }

    @Override
    public void deleteByTrackId(String trackId) {
        List<Song> songs = songRepository.findAll();
        songs.stream()
                .filter(song -> song.getTrackId().equals(trackId))
                .findFirst()
                .ifPresent(songRepository::delete);
    }

    @Override
    public void update(Song song) {
        songRepository.save(song); // Save will handle both update and insert
    }

    @Override
    public void save(Song song) {
        if (song.getTrackId() == null || song.getTrackId().isEmpty()) {
            song.setTrackId(String.valueOf(generateNextTrackId()));
        }
        songRepository.save(song);
    }

    @Override
    public int generateNextTrackId() {
        return (int) (songRepository.count() + 1); // Use repository to calculate next ID
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll(); // Fetch all songs
    }

    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findAll().stream()
                .filter(song -> song.getTrackId().equals(trackId))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void addArtistToSong(Artist artist, Song song) {
        if (song == null || artist == null) {
            throw new IllegalArgumentException("Song or Artist cannot be null.");
        }

        if (!song.getPerformers().contains(artist)) {
            song.getPerformers().add(artist); // Add artist to the performers
            songRepository.save(song); // Save updated song
        } else {
            System.out.println("Artist is already in the performers list.");
        }
    }

    @Override
    public List<Song> findAllByAlbum_Id(Long albumId) {
        return songRepository.findAllByAlbum_Id(albumId); // Fetch songs by album ID
    }

    public String findMaxTrackId() {
        return songRepository.findMaxTrackId();
    }
//    public List<Song> findByAlbumId(Long albumId) {
//        return songRepository.findAllByAlbum_Id(albumId);
//    }

  @Override
public List<Song> filterSongs(Long albumId, String price, String title, String genre, String releaseYear) {
    return songRepository.findAll().stream()
        .filter(song -> albumId == null || albumId == -1 || song.getAlbum().getId().equals(albumId))
        .filter(song -> price == null || price.isEmpty() || song.getPrice().getValue().toString().equals(price))
        .filter(song -> title == null || title.isEmpty() || song.getTitle().toLowerCase().contains(title.toLowerCase()))
        .filter(song -> genre == null || genre.isEmpty() || song.getGenre().equalsIgnoreCase(genre))
        .filter(song -> {
            if (releaseYear == null || releaseYear.isEmpty()) return true;
            int year = song.getReleaseYear();
            switch (releaseYear) {
                case "60s": return year >= 1960 && year < 1970;
                case "70s": return year >= 1970 && year < 1980;
                case "80s": return year >= 1980 && year < 1990;
                case "90s": return year >= 1990 && year < 2000;
                case "00s": return year >= 2000 && year < 2010;
                case "10s": return year >= 2010 && year < 2020;
                case "20s": return year >= 2020 && year < 2030;
                case "30s": return year >= 2030 && year < 2040;
                default: return false;
            }
        })
        .toList();
}


    @Override
    public List<String> findAllGenres() {
        return songRepository.findAllGenres();
    }

}

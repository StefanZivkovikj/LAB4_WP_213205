package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository,ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;

    }

//    @Override
//    public List<Song> listSongs() {
//        return songRepository.findAll();
//    }


//    @Override
//    public void addArtistToSong(String trackId, Long artistId) {
//        // Fetch the song by trackId
//        Song song = songRepository.findByTrackId(trackId);
//        if (song == null) {
//            throw new IllegalArgumentException("Song not found with trackId: " + trackId);
//        }
//
//        // Fetch the artist by artistId
//        Artist artist = artistRepository.findById(artistId).orElseThrow(() ->
//                new IllegalArgumentException("Artist not found with artistId: " + artistId)
//        );
//
//        // Check if the artist is already in the performers list
//        if (!song.getPerformers().contains(artist)) {
//            song.addPerformer(artist); // Add the artist to the performers list
//            songRepository.save(song); // Save the updated song to the database
//            System.out.println("Artist with ID " + artistId + " added to song with trackId " + trackId);
//        } else {
//            System.out.println("Artist already exists in the performers list for song with trackId " + trackId);
//        }
//    }



    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public void addSong(Song song) {

    }

    @Override
    public Song findById(Long songId) {
        return null;
    }


    @Override
    public void removeSong(Long id) {

    }

    @Override
    public void deleteByTrackId(String trackId) {
    songRepository.deleteByTrackId(trackId);
    }

    @Override
    public void update(Song song) {
        songRepository.update(song);
    }

    @Override
    public void save(Song song) {
        // Generate a unique trackId for new songs
        if (song.getTrackId() == null || song.getTrackId().isEmpty()) {
            song.setTrackId(String.valueOf(generateNextTrackId()));
        }
        songRepository.save(song);
    }

    @Override
    public int generateNextTrackId() {
        return songRepository.getNextTrackId();
    }

//    public int generateNextTrackId() {
//        return songRepository.getNextTrackId(); // Delegate to repository for unique track IDs
//    }


    @Override
    public List<Song> listSongs() {
        return null;
    }

    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }



    @Override
    public void addArtistToSong(Artist artist, Song song) {
        if (song == null || artist == null) {
            throw new IllegalArgumentException("Song or Artist cannot be null.");
        }

        if (!song.getPerformers().contains(artist)) {
            if (song.getPerformers() instanceof ArrayList) {
                song.getPerformers().add(artist); // Add artist directly if the list is mutable
            } else {
                List<Artist> mutablePerformers = new ArrayList<>(song.getPerformers());
                mutablePerformers.add(artist);
                song.setPerformers(mutablePerformers); // Update the performers list
            }

            songRepository.save(song); // Save updated song
            System.out.println("Artist added to song successfully.");
        } else {
            System.out.println("Artist is already in the performers list.");
        }
    }

}

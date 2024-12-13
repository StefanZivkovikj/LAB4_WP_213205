package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Price;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SongRepository {
    private final List<Song> songs;
    private int trackIdCounter = 6;

    public SongRepository(AlbumRepository albumRepository) {
        this.songs = new ArrayList<>();


        // Fetch albums from AlbumRepository
        List<Album> albums = albumRepository.findAll();

        // Initialize songs with associated albums
        // Initialize songs with associated albums and prices
        songs.add(new Song("1", "Song 1", "Rock", 2020, List.of(new Artist(1L, "Artist A", "Rock", "TestBio")), albums.get(0), new Price("$9.99")));
        songs.add(new Song("2", "Song 2", "Pop", 2019, List.of(new Artist(2L, "Artist B", "Pop", "BioTest")), albums.get(1), new Price("$12.99")));
        songs.add(new Song("3", "Song 3", "Jazz", 2018, List.of(new Artist(3L, "Artist C", "Jazz", "TestBio")), albums.get(2), new Price("Free")));
        songs.add(new Song("4", "Song 4", "Classical", 2017, List.of(new Artist(4L, "Artist D", "Classical", "BioTest")), albums.get(3), new Price("$14.99")));
        songs.add(new Song("5", "Song 5", "Hip Hop", 2021, List.of(new Artist(5L, "Artist E", "Hip Hop", "TestBio")), albums.get(4), new Price("$19.99")));
    }

    /**
     * Returns a list of all songs.
     *
     * @return List of songs
     */
    public List<Song> findAll() {
        System.out.println("Current songs in repository: " + songs);
        return new ArrayList<>(songs); // Returns a copy of the songs list
    }


    public Song save(Song song) {
        Song existingSong = findByTrackId(song.getTrackId());
        if (existingSong != null) {
            update(song); // Update the existing song
        } else {
            songs.add(song); // Add a new song
        }
        return song;
    }



    /**
     * Finds a song by its track ID.
     *
     * @param trackId The track ID of the song.
     * @return The song with the specified track ID, or null if not found.
     */
    public Song findByTrackId(String trackId) {
        System.out.println("Searching for song with trackId: " + trackId); // Debugging line
        return songs.stream()
                .filter(song -> song.getTrackId().equals(trackId))
                .findFirst()
                .orElse(null);
    }

    public int getNextTrackId() {
        return trackIdCounter++;
    }

    public void deleteByTrackId(String trackId) {
        songs.removeIf(song -> song.getTrackId().equals(trackId));
    }

    // Update a song by trackId
    public void update(Song updatedSong) {
        for (int i = 0; i < songs.size(); i++) {
            Song existingSong = songs.get(i);
            if (existingSong.getTrackId().equals(updatedSong.getTrackId())) {
                songs.set(i, updatedSong); // Replace the song at the current index
                return;
            }
        }
    }


    /**
     * Adds a new song to the repository.
     *
     * @param song The song to be added.
     * @return The added song.
     */
    public Song addSong(Song song) {
        songs.add(song);
        return song;
    }

    /**
     * Removes a song by its track ID.
     *
     * @param trackId The track ID of the song to remove.
     * @return True if the song was removed, false otherwise.
     */
    public boolean removeSong(String trackId) {
        return songs.removeIf(song -> song.getTrackId().equals(trackId));
    }

    /**
     * Associates an artist with a song.
     *
     * @param artist The artist to be added.
     * @param song   The song to which the artist will be added.
     * @return The added artist.
     */
//    public Artist addArtistToSong(Artist artist, Song song) {
//        song.getPerformers().add(artist);
//        return artist;
//    }



}

package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlbumRepository {
    private final List<Album> albums;

    public AlbumRepository() {
        this.albums = new ArrayList<>();

        // Initialize albums
        albums.add(new Album(1L, "Thriller", "Pop", "1982"));
        albums.add(new Album(2L, "Back in Black", "Rock", "1980"));
        albums.add(new Album(3L, "The Dark Side of the Moon", "Progressive Rock", "1973"));
        albums.add(new Album(4L, "Abbey Road", "Rock", "1969"));
        albums.add(new Album(5L, "The Wall", "Rock", "1979"));
    }

    /**
     * Returns a list of all albums in the system.
     *
     * @return List of albums
     */
    public List<Album> findAll() {
        return albums;
    }

    /**
     * Finds an album by its ID.
     *
     * @param id The ID of the album.
     * @return The album with the specified ID, or null if not found.
     */
    public Album findById(Long id) {
        System.out.println("Searching for album with ID: " + id); // Debugging line
        return albums.stream()
                .filter(album -> album.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new album to the repository.
     *
     * @param album The album to be added.
     * @return The added album.
     */
    public Album addAlbum(Album album) {
        albums.add(album);
        return album;
    }
}

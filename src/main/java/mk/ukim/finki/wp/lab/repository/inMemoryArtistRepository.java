package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArtistRepository {
    private final List<Artist> artists;

    public ArtistRepository() {
        this.artists = new ArrayList<>();

        artists.add(new Artist(1L, "Axl", "Rose", "Lead vocalist of Guns N' Roses"));
        artists.add(new Artist(2L, "Jon", "Bon Jovi", "Lead vocalist of Bon Jovi"));
        artists.add(new Artist(3L, "David", "Bowie", "Singer-songwriter and actor"));

    }
    public Optional<Artist> findById(Long id) {
        return artists.stream()
                .filter(artist -> artist.getId().equals(id))
                .findFirst();
    }


    public List<Artist> findAll() {
        return artists;
    }

    public List<Artist> getArtists() {
        return artists;
    }


}

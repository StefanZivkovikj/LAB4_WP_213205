package mk.ukim.finki.wp.lab.model;

import java.util.ArrayList;
import java.util.List;

public class Song {

    private List<Artist> performers = new ArrayList<>();
    private static Long currentId = 1L;

    private Long id;
    String trackId;
    String title;
    String genre;
    int releaseYear;
//    List<Artist> performers;
    private Album album;

    public Song() {
    }

    public Song(String trackId, String title, String genre, int releaseYear, List<Artist> performers,Album album) {
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.performers = performers != null ? performers : new ArrayList<>();
        this.album = album;
    }

    private synchronized static Long generateId() {
        return currentId++;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<Artist> getPerformers() {
        return performers;
    }

    public Album getAlbum() {
        return album;
    }



    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setPerformers(List<Artist> performers) {
        this.performers = performers;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void addPerformer(Artist artist) {
        performers.add(artist); // Adds a performer to the mutable list
    }

}

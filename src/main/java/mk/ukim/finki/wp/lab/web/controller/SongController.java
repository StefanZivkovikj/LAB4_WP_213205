package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Price;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    /**
     * Displays all songs with album names and associated actions.
     *
     * @param error   Optional error message.
     * @param model   Spring model for passing attributes to the view.
     * @param session HttpSession to manage attributes.
     * @return The view name for the song list page.
     */
    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, @RequestParam(required = false) Long albumId, Model model, HttpSession session) {
        // Ensure the session attribute is set as required
        session.setAttribute("canAccessArtistPage", true);

        // Fetch the list of songs
        List<Song> songs = songService.findAll();
        List<Album> albums = albumService.findAll(); // Fetch all albums
        // Add attributes to the model for the Thymeleaf view
        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums); // Pass albums to the view
        model.addAttribute("error", error);
        System.out.println("Songs sent to view: " + songs);


        // Return the view name that corresponds to listSongs.html
        return "listSongs";
    }

    /**
     * Displays the form for adding a new song.
     *
     * @param model Spring model to pass album data to the view.
     * @return The view name for the add-song form.
     */
    @GetMapping("/add-form")
    public String getAddSongPage(Model model) {
        model.addAttribute("albums", albumService.findAll());
        model.addAttribute("song", new Song()); // Empty Song object for the form binding
        return "add-song"; // Returns the template for add-song.html
    }

    /**
     * Displays the form for editing an existing song.
     *
     * @param trackId The trackId of the song to edit.
     * @param model   Spring model to pass song and album data to the view.
     * @return The view name for the add-song form prefilled with song details.
     */
    @GetMapping("/edit-form/{trackId}")
    public String getEditSongForm(@PathVariable String trackId, Model model) {
        Song song = songService.findByTrackId(trackId);
        if (song == null) {
            return "redirect:/songs?error=SongNotFound";
        }
        model.addAttribute("song", song);
        model.addAttribute("albums", albumService.findAll());
        return "add-song";
    }



    /**
     * Handles adding a new song to the system.
     *
     * @param title       The title of the song.
     * @param trackId     The track ID of the song.
     * @param genre       The genre of the song.
     * @param releaseYear The release year of the song.
     * @param albumId     The ID of the associated album.
     * @return Redirection to the list of songs.
     */
    @PostMapping("/save")
    public String saveOrUpdateSong(
            @RequestParam(required = false) String trackId,
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam int releaseYear,
            @RequestParam Long albumId,
            @RequestParam String price,
            Model model) {

        System.out.println("Saving or updating song...");
        System.out.printf("Title: %s, Track ID: %s, Genre: %s, Year: %d, Album ID: %d, Price: %s%n",
                title, trackId, genre, releaseYear, albumId, price);

        // Validate Album
        Album album = albumService.findById(albumId);
        if (album == null) {
            model.addAttribute("error", "Invalid album selected!");
            return "add-song";
        }

        // Parse Price
        Price finalPrice;
        try {
            finalPrice = new Price(price);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid price format!");
            return "add-song";
        }

        // Handle Track ID Generation and Song Creation/Update
        if (trackId == null || trackId.isEmpty()) {
            // Generate the next track ID
            String maxTrackId = songService.findMaxTrackId();
            int nextId = (maxTrackId != null && maxTrackId.startsWith("T"))
                    ? Integer.parseInt(maxTrackId.substring(1)) + 1
                    : 1;
            String newTrackId = "T" + nextId;

            // Create a new Song
            Song newSong = new Song(newTrackId, title, genre, releaseYear, album, finalPrice);
            songService.save(newSong);
            System.out.println("New song added: " + newSong);
        } else {
            // Update an existing Song
            Song existingSong = songService.findByTrackId(trackId);
            if (existingSong == null) {
                model.addAttribute("error", "Song not found!");
                return "add-song";
            }

            existingSong.setTitle(title);
            existingSong.setGenre(genre);
            existingSong.setReleaseYear(releaseYear);
            existingSong.setAlbum(album);
            existingSong.setPrice(finalPrice);

            songService.update(existingSong);
            System.out.println("Song updated: " + existingSong);
        }

        return "redirect:/songs";
    }





    /**
     * Handles editing an existing song.
     *
     * @param trackId     The trackId of the song to edit.
     * @param title       The updated title.
     * @param genre       The updated genre.
     * @param releaseYear The updated release year.
     * @param albumId     The ID of the updated album.
     * @return Redirection to the list of songs.
     */
    @PostMapping("/edit/{trackId}")
    public String editSong(
            @PathVariable String trackId,
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam int releaseYear,
            @RequestParam Long albumId,
            @RequestParam String price) {

        Song song = songService.findByTrackId(trackId);
        if (song != null) {
            Album album = albumService.findAll().stream()
                    .filter(a -> a.getId().equals(albumId))
                    .findFirst()
                    .orElse(null);

            if (album == null) {
                return "redirect:/songs/edit-form/" + trackId + "?error=InvalidAlbum";
            }

            // Create a Price object from the input
            Price finalPrice = new Price(price);

            song.setTitle(title);
            song.setGenre(genre);
            song.setReleaseYear(releaseYear);
            song.setAlbum(album);
            song.setPrice(finalPrice); // Set the Price object
            songService.update(song);
        }

        return "redirect:/songs";
    }


    /**
     * Handles deleting a song by its trackId.
     *
     * @param trackId The trackId of the song to delete.
     * @return Redirection to the list of songs.
     */
    @PostMapping("/delete/{trackId}")
    public String deleteSong(@PathVariable String trackId) {
        songService.deleteByTrackId(trackId);
        return "redirect:/songs";
    }

    @GetMapping("/by-album/{albumId}")
    public String getSongsByAlbum(@PathVariable Long albumId, Model model) {
        List<Song> songs = songService.findAllByAlbum_Id(albumId);
        model.addAttribute("songs", songs);
        return "listSongs";
    }

    @PostMapping("/by-album")
    public String filterSongsByAlbum(@RequestParam(name = "albumId", defaultValue = "-1")  Long albumId, Model model) {

        List<Song> songs = songService.findAllByAlbum_Id(albumId);
        System.out.println("Filtered songs: " + songs);
        List<Album> albums = albumService.findAll(); // Include albums for dropdown
        if (albums.isEmpty()) {
            System.out.println("No albums found!");
        }

        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums);
        System.out.println("Album ID received: " + albumId); // Debug log

        return "listSongs";
    }

}

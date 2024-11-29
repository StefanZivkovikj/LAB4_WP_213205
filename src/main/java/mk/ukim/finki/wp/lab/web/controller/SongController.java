package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.Album;
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
    public String getSongsPage(@RequestParam(required = false) String error, Model model, HttpSession session) {
        // Ensure the session attribute is set as required
        session.setAttribute("canAccessArtistPage", true);

        // Fetch the list of songs
        List<Song> songs = songService.findAll();

        // Add attributes to the model for the Thymeleaf view
        model.addAttribute("songs", songs);
        model.addAttribute("error", error);

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
    @PostMapping("/{trackId}")
    public String saveOrUpdateSong(
            @PathVariable String trackId,
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam int releaseYear,
            @RequestParam Long albumId) {

        Album album = albumService.findAll().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElse(null);

        if (album == null) {
            return "redirect:/songs/add-form?error=InvalidAlbum";
        }

        if ("0".equals(trackId)) {
            // Add a new song
            Song newSong = new Song(null, title, genre, releaseYear, List.of(), album);
            songService.save(newSong);
        } else {
            // Edit an existing song
            Song existingSong = songService.findByTrackId(trackId);
            if (existingSong != null) {
                existingSong.setTitle(title);
                existingSong.setGenre(genre);
                existingSong.setReleaseYear(releaseYear);
                existingSong.setAlbum(album);
                songService.update(existingSong);
            }
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
            @RequestParam Long albumId) {

        Song song = songService.findByTrackId(trackId);
        if (song != null) {
            Album album = albumService.findAll().stream()
                    .filter(a -> a.getId().equals(albumId))
                    .findFirst()
                    .orElse(null);

            song.setTitle(title);
            song.setGenre(genre);
            song.setReleaseYear(releaseYear);
            song.setAlbum(album);
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
}

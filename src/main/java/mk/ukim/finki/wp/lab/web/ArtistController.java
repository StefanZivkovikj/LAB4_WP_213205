package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    /**
     * Displays a list of artists for a specific song based on trackId.
     *
     * @param trackId The trackId of the song.
     * @param session HttpSession for access control.
     * @param model   Spring model to pass attributes to the view.
     * @return The view name for the artists list.
     */
    @GetMapping
    public String showArtistList(@RequestParam String trackId, HttpSession session, Model model) {
        // Check if the user has legitimate access
        Boolean canAccess = (Boolean) session.getAttribute("canAccessArtistPage");
        if (canAccess == null || !canAccess) {
            return "redirect:/songs"; // Redirect if no access
        }

        // Remove access flag to prevent repeated access
        session.removeAttribute("canAccessArtistPage");

        // Fetch the song by trackId
        Song song = songService.findByTrackId(trackId);
        if (song == null) {
            return "redirect:/songs?error=SongNotFound";
        }

        // Fetch all artists and pass them to the model
        List<Artist> artists = artistService.listArtists();
        model.addAttribute("artists", artists);
        model.addAttribute("song", song); // Pass song to display current artists
        model.addAttribute("trackId", trackId);

        System.out.println("Passing trackId to model: " + trackId);
        return "artistsList"; // Display the artistsList.html template
    }

    /**
     * Handles assigning an artist to a specific song.
     *
     * @param trackId  The trackId of the song.
     * @param artistId The ID of the artist to assign.
     * @return Redirection to the artist list page.
     */
    @PostMapping("/{trackId}/add")
    public String addArtistToSong(@PathVariable String trackId, @RequestParam Long artistId) {
        Song song = songService.findByTrackId(trackId);
        if (song != null) {
            Optional<Artist> optionalArtist = artistService.findById(artistId);
            if (optionalArtist.isEmpty()) {
                throw new IllegalArgumentException("Artist not found with ID: " + artistId);
            }
            Artist artist = optionalArtist.get();
            if (artist != null && !song.getPerformers().contains(artist)) {
                song.getPerformers().add(artist);
                songService.update(song);
            }
        }
        return "redirect:/artist?trackId=" + trackId; // Redirect to the artist list
    }
}

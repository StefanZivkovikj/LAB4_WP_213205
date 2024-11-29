package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SongDetailsController {

    private final SongService songService;

    public SongDetailsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songDetails")
    public String showSongDetails(@RequestParam("trackId") String trackId, Model model) {
        System.out.println("Retrieving song details for trackId: " + trackId); // Debugging line
        Song song = songService.findByTrackId(trackId);

        if (song != null) {
            model.addAttribute("title", song.getTitle());
            model.addAttribute("genre", song.getGenre());
            model.addAttribute("releaseYear", song.getReleaseYear());
            model.addAttribute("performers", song.getPerformers());
            System.out.println("Song found: " + song.getTitle() + ", Performers: " + song.getPerformers()); // Debugging line
        } else {
            System.out.println("No song found with trackId: " + trackId); // Debugging line
        }

        return "songDetails"; // Return songDetails.html
    }
}

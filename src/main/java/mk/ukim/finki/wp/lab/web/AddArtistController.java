package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddArtistController {

    private final SongService songService;
    private final ArtistService artistService;

    public AddArtistController(SongService songService, ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;
    }

    @PostMapping("/addArtist")
    public String addArtistToSong(@RequestParam("artistId") Long artistId, @RequestParam("trackId") String trackId) {
        System.out.println("Received artistId: " + artistId + ", trackId: " + trackId);

        Artist artist = artistService.findById(artistId) .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + artistId));
        Song song = songService.findByTrackId(trackId);

        if (artist == null) {
            System.out.println("Artist not found with ID: " + artistId);
            return "redirect:/error";
        }

        if (song == null) {
            System.out.println("Song not found with trackId: " + trackId);
            return "redirect:/error";
        }

        songService.addArtistToSong(artist, song);
        System.out.println("Artist added to song successfully.");

        return "redirect:/songDetails?trackId=" + trackId;
    }

}

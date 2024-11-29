//package mk.ukim.finki.lab.web;
//
//import mk.ukim.finki.wp.lab.model.Song;
//import mk.ukim.finki.wp.lab.service.SongService;
//import mk.ukim.finki.wp.lab.model.Song;
//import mk.ukim.finki.wp.lab.service.SongService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class SongListController {
//
//    private final SongService songService;
//
//    @Autowired
//    public SongListController(SongService songService) {
//        this.songService = songService;
//    }
//
//    @GetMapping("/listSongs")
//    public String listSongs(Model model) {
//        // Fetch all songs using the service
//        List<Song> songs = songService.listSongs();
//
//        // Add the list of songs to the model
//        model.addAttribute("songs", songs);
//
//        // Return the view name to render (e.g., listSongs.html for Thymeleaf)
//        return "listSongs";
//    }
//}

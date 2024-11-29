//package mk.ukim.finki.wp.lab.web;
//
//import jakarta.servlet.http.HttpSession;
//import mk.ukim.finki.wp.lab.model.Song;
//import mk.ukim.finki.wp.lab.service.SongService;
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
//
//    public SongListController(SongService songService) {
//        this.songService = songService;
//    }
//
//    @GetMapping("/listsongs")
//    public String listSongs(HttpSession session, Model model) {
//        session.setAttribute("canAccessArtistPage",true);
//
//        List<Song> songs = songService.listSongs();
//        model.addAttribute("songs", songs);
//        return "listSongs"; // Refers to listSongs.html in templates folder
//    }
//}

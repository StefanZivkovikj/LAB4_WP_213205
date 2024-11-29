//package mk.ukim.finki.wp.lab.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class ArtistAccessFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization if needed
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpSession session = httpRequest.getSession(false); // Get current session
//
//        // Get the request URI
//        String requestURI = httpRequest.getRequestURI();
//
//        // Check access for paths starting with /artist
//        if (requestURI.startsWith("/artist")) {
//            if (session == null) {
//                System.out.println("Session is null. Redirecting to /listSongs.");
//                httpResponse.sendRedirect("/listSongs");
//                return;
//            }
//
//            Boolean canAccessArtistPage = Boolean.TRUE.equals(session.getAttribute("canAccessArtistPage"));
//            if (!canAccessArtistPage) {
//                System.out.println("Access to artist page denied. Redirecting to /listSongs.");
//                httpResponse.sendRedirect("/listSongs");
//                return;
//            }
//        }
//        // Check access for paths starting with /songDetails
//        else if (requestURI.startsWith("/songDetails")) {
//            if (session == null) {
//                System.out.println("Session is null. Redirecting to /listSongs.");
//                httpResponse.sendRedirect("/listSongs");
//                return;
//            }
//
//            Boolean canAccessSongDetailsPage = Boolean.TRUE.equals(session.getAttribute("canAccessSongDetailsPage"));
//            if (!canAccessSongDetailsPage) {
//                System.out.println("Access to song details page denied. Redirecting to /listSongs.");
//                httpResponse.sendRedirect("/listSongs");
//                return;
//            }
//        }
//
//        // If access is allowed, continue processing the request
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup if needed
//    }
//}

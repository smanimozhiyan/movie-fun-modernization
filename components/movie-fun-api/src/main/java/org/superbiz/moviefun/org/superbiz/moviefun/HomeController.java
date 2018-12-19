package org.superbiz.moviefun.org.superbiz.moviefun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.superbiz.moviefun.org.superbiz.moviefun.albumsapi.AlbumFixtures;
import org.superbiz.moviefun.org.superbiz.moviefun.albumsapi.AlbumInfo;
import org.superbiz.moviefun.org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.org.superbiz.moviefun.moviesapi.MovieFixtures;
import org.superbiz.moviefun.org.superbiz.moviefun.moviesapi.MovieInfo;
import org.superbiz.moviefun.org.superbiz.moviefun.moviesapi.MoviesClient;

import java.util.Map;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final MoviesClient moviesClient;
    private final AlbumsClient albumsClient;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeController(MoviesClient moviesClient, AlbumsClient albumsClient, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesClient = moviesClient;
        this.albumsClient = albumsClient;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movie : movieFixtures.load()) {
            moviesClient.addMovie(movie);
        }

        for (AlbumInfo album : albumFixtures.load()) {
            albumsClient.addAlbum(album);
        }

        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumsClient.getAlbums());

        return "setup";
    }

    @GetMapping("/albums")
    public String getAlbums(Map<String, Object> model) {
//        for (AlbumInfo album : albumFixtures.load()) {
//            albumsClient.addAlbum(album);
//        }
        model.put("albums", albumsClient.getAlbums());
        return "albums";
    }

    @GetMapping("/albums/{id}/cover")
    public String getCover(Map<String, Object> model, @PathVariable("id") long id) {
        logger.info("Getting cover details for albums id : "+id);
//        for (AlbumInfo album : albumFixtures.load()) {
//            albumsClient.addAlbum(album);
//        }
        HttpEntity coverInfo = albumsClient.getCover(id);
        logger.info("Albums client returns cover : "+coverInfo);
        model.put("albums", coverInfo);
        return "albumDetails";
    }

    @GetMapping("/albums/{id}")
    public String getAlbums(Map<String, Object> model, @PathVariable("id") long id) {
        logger.info("Getting details for albums id : "+id);
//        for (AlbumInfo album : albumFixtures.load()) {
//            albumsClient.addAlbum(album);
//        }
        AlbumInfo albumInfo = albumsClient.find(id);
        logger.info("Albums client returns albums id : "+albumInfo);
        model.put("album", albumInfo);
        return "albumDetails";
    }
}

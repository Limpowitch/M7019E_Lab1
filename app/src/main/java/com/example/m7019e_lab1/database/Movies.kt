package com.example.m7019e_lab1.database

import com.example.m7019e_lab1.models.Movie

class Movies {

    fun getMovies() : List<Movie> {
        return listOf(
            Movie(
                id = 278,
                title = "The Shawshank Redemption",
                posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
                backdropPath = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
                releaseDate = "1994-09-23",
                overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                genre = listOf("Drama", "Mafia"),
                imdb_id = "tt0111161",
                movie_homepage_url = "https://www.warnerbros.com/movies/shawshank-redemption"
            ),
            Movie(
                id = 238,
                title = "The Godfather",
                posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
                releaseDate = "1972-03-14",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
                genre = listOf("Drama"),
                imdb_id = "tt0068646",
                movie_homepage_url = "https://www.paramountpictures.com/movies/the-godfather"
            ),
            Movie(
                id = 240,
                title = "The Godfather Part II",
                posterPath = "/hek3koDUyRQk7FIhPXsa6mT2Zc3.jpg",
                backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
                releaseDate = "1974-12-20",
                overview = "In the continuing saga of the Corleone crime family, a young Vito Corleone grows up in Sicily and in 1910s New York. In the 1950s, Michael Corleone attempts to expand the family business into Las Vegas, Hollywood and Cuba.",
                genre = listOf("Drama"),
                imdb_id = "tt0071562",
                movie_homepage_url = "https://www.paramountpictures.com/movies/the-godfather-part-ii"
            ),
            Movie(
                id = 424,
                title = "Schindler's List",
                posterPath = "/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg",
                backdropPath = "/zb6fM1CX41D9rF9hdgclu0peUmy.jpg",
                releaseDate = "1993-12-15",
                overview = "The true story of how businessman Oskar Schindler saved over a thousand Jewish lives from the Nazis while they worked as slaves in his factory during World War II.",
                genre = listOf("Historical Drama", "Drama", "World war 2", "Tragedy", "Period Drama", "Biography"),
                imdb_id = "tt0108052",
                movie_homepage_url = "https://www.universalpictures.com/movies/schindlers-list"
            ),
            Movie(
                id = 389,
                title = "12 Angry Men",
                posterPath = "/ow3wq89wM8qd5X7hWKxiRfsFf9C.jpg",
                backdropPath = "/bxgTSUenZDHNFerQ1whRKplrMKF.jpg",
                releaseDate = "1957-04-10",
                overview = "The defense and the prosecution have rested and the jury is filing into the jury room to decide if a young Spanish-American is guilty or innocent of murdering his father. What begins as an open and shut case soon becomes a mini-drama of each of the jurors' prejudices and preconceptions about the trial, the accused, and each other.",
                genre = listOf("Drama"),
                imdb_id = "tt0050083",
                movie_homepage_url = "https://en.wikipedia.org/wiki/Twelve_Angry_Men"
            )
        )
    }

}
package com.example.m7019e_lab1.database

import com.example.m7019e_lab1.models.Movie

class Movies {

    fun getMovies() : List<Movie> {
        return listOf(
            Movie(
                id = 1,
                title = "The Godfather Part II",
                posterPath = "/hek3koDUyRQk7FIhPXsa6mT2Zc3.jpg",
                backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
                releaseDate = "1974-12-20",
                overview = "In the continuing saga of the Corleone crime family, a young Vito Corleone grows up in Sicily and in 1910s New York. In the 1950s, Michael Corleone attempts to expand the family business into Las Vegas, Hollywood and Cuba."
            ),
            Movie(
                id = 2,
                title = "Schindler's List",
                posterPath = "/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg",
                backdropPath = "/zb6fM1CX41D9rF9hdgclu0peUmy.jpg",
                releaseDate = "1993-12-15",
                overview = "The true story of how businessman Oskar Schindler saved over a thousand Jewish lives from the Nazis while they worked as slaves in his factory during World War II."
            )
        )
    }

}
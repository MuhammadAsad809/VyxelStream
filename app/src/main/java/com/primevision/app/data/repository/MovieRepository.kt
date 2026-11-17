package com.primevision.app.data.repository

import com.primevision.app.data.model.Category
import com.primevision.app.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MovieRepository {

    val categories = listOf(
        Category("most_watched", "Most Watched"),
        Category("for_kids", "For Kids"),
        Category("family", "Family Friendly"),
        Category("trending", "Trending"),
        Category("recently_added", "Recently Added")
    )

    val platforms = listOf(
        "HBO Max",
        "Disney+",
        "Apple TV",
        "Netflix"
    )

    // Curated high-resolution cinematic posters & stills
    private val initialMovies = listOf(
        Movie(
            id = "dune",
            title = "Dune",
            posterUrl = "https://image.tmdb.org/t/p/w780/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/lzWHmY21F0VwQ048IRSc7Z84q4.jpg",
            year = "2023",
            duration = "1h 45m",
            rating = "8.2",
            contentRating = "PG-13",
            quality = "4K",
            synopsis = "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people.",
            genres = listOf("Sci-Fi", "Adventure", "Action"),
            platform = "HBO Max",
            isFavorite = true,
            recommendedStills = listOf(
                "https://image.tmdb.org/t/p/w500/jYEW5xZkZk2WTrdbMGAPFuBqbDc.jpg",
                "https://image.tmdb.org/t/p/w500/lzWHmY21F0VwQ048IRSc7Z84q4.jpg",
                "https://image.tmdb.org/t/p/w500/aknvHjepAi1pv5N2L1fGQ9kfa51.jpg"
            )
        ),
        Movie(
            id = "joker",
            title = "Joker",
            posterUrl = "https://image.tmdb.org/t/p/w780/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
            year = "2024",
            duration = "1h 45m",
            rating = "8.2",
            contentRating = "R",
            quality = "HD",
            synopsis = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.",
            genres = listOf("Crime", "Drama", "Thriller"),
            platform = "HBO Max",
            isFavorite = false,
            recommendedStills = listOf(
                "https://image.tmdb.org/t/p/w500/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
                "https://image.tmdb.org/t/p/w500/f5F4cRhOTMuILjxHGANWyoEbydp.jpg"
            )
        ),
        Movie(
            id = "mavka",
            title = "Mavka",
            posterUrl = "https://image.tmdb.org/t/p/w780/eeJjd9JU2iOZ9vgFn97OIa9ncag.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/4HodYYKEIsGOdinkGi2Ucz6X9i0.jpg",
            year = "2022",
            duration = "1h 45m",
            rating = "8.2",
            contentRating = "CC",
            quality = "HD",
            synopsis = "The already tumultuous and difficult lives of ordinary people in the Ukrainian countryside are turned upside down by war, revolution, and change of government.",
            genres = listOf("Animation", "Fantasy", "Adventure"),
            platform = "Disney+",
            isFavorite = true,
            recommendedStills = listOf(
                "https://image.tmdb.org/t/p/w500/4HodYYKEIsGOdinkGi2Ucz6X9i0.jpg",
                "https://image.tmdb.org/t/p/w500/8cmi41E5g3Gk8K8mI7X731k6V9g.jpg",
                "https://image.tmdb.org/t/p/w500/2I5e1d7g2eP8iXf3M4k6V9g5e1.jpg"
            )
        ),
        Movie(
            id = "joker_folie",
            title = "Joker: Folie à Deux",
            posterUrl = "https://image.tmdb.org/t/p/w780/aciP8Km0waTLGEYf5zyRBF5V9xQ.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/uGmYqxh8flqkPn9D4sNcvBh08p9.jpg",
            year = "2024",
            duration = "1h 45m",
            rating = "8.2",
            contentRating = "CC",
            quality = "HD",
            synopsis = "Arthur Fleck seeks to discover true love while incarcerated at Arkham State Hospital, meeting fellow patient Harleen Quinzel in a shared musical delusion.",
            genres = listOf("Crime", "Drama", "Musical"),
            platform = "HBO Max",
            isFavorite = true,
            recommendedStills = listOf(
                "https://image.tmdb.org/t/p/w500/uGmYqxh8flqkPn9D4sNcvBh08p9.jpg",
                "https://image.tmdb.org/t/p/w500/6v7F3rM6Y4k3V7k8W2k9V9g5e1.jpg"
            )
        ),
        Movie(
            id = "interstellar",
            title = "Interstellar",
            posterUrl = "https://image.tmdb.org/t/p/w780/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/rAiYTsqJJR0KP8UN8vMzIFjDeOX.jpg",
            year = "2014",
            duration = "2h 49m",
            rating = "8.7",
            contentRating = "PG-13",
            quality = "4K",
            synopsis = "When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft along with a team of researchers to find a new planet for humans.",
            genres = listOf("Adventure", "Drama", "Sci-Fi"),
            platform = "Apple TV",
            isFavorite = false,
            recommendedStills = listOf(
                "https://image.tmdb.org/t/p/w500/rAiYTsqJJR0KP8UN8vMzIFjDeOX.jpg"
            )
        ),
        Movie(
            id = "oppenheimer",
            title = "Oppenheimer",
            posterUrl = "https://image.tmdb.org/t/p/w780/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/fm6K9vYqq0YeQZ0wwA4j7RzN9Mm.jpg",
            year = "2023",
            duration = "3h 00m",
            rating = "8.9",
            contentRating = "R",
            quality = "4K",
            synopsis = "The story of J. Robert Oppenheimer's role in the development of the atomic bomb during World War II.",
            genres = listOf("Biography", "Drama", "History"),
            platform = "Netflix",
            isFavorite = true,
            recommendedStills = listOf(
                "https://image.tmdb.org/t/p/w500/fm6K9vYqq0YeQZ0wwA4j7RzN9Mm.jpg"
            )
        )
    )

    private val initialSeries = listOf(
        Movie(
            id = "chernobyl",
            title = "Chernobyl",
            posterUrl = "https://image.tmdb.org/t/p/w780/hlLXt2tOPT6RRnjiUmoxyG1LTFi.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/uL6Ad12W9RtL2N5y8V6M4k3V7k8.jpg",
            year = "2019",
            duration = "5 Episodes",
            rating = "9.4",
            contentRating = "TV-MA",
            quality = "4K",
            synopsis = "In April 1986, an explosion at the Chernobyl nuclear power plant in the Union of Soviet Socialist Republics becomes one of the world's worst man-made catastrophes.",
            genres = listOf("Drama", "History", "Thriller"),
            isSeries = true,
            platform = "HBO Max",
            isFavorite = true
        ),
        Movie(
            id = "got",
            title = "Game of Thrones",
            posterUrl = "https://image.tmdb.org/t/p/w780/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/suopoADq0k8YZr4dQXLi6q0xHHO.jpg",
            year = "2011",
            duration = "8 Seasons",
            rating = "9.2",
            contentRating = "TV-MA",
            quality = "4K",
            synopsis = "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.",
            genres = listOf("Action", "Adventure", "Drama"),
            isSeries = true,
            platform = "HBO Max",
            isFavorite = false
        ),
        Movie(
            id = "last_of_us",
            title = "The Last of Us",
            posterUrl = "https://image.tmdb.org/t/p/w780/uKvVjHNqB5VmOrdxqMis2SYaw2V.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/original/uDgy6hyPd82kOHh6I95FLtLnj6p.jpg",
            year = "2023",
            duration = "9 Episodes",
            rating = "8.8",
            contentRating = "TV-MA",
            quality = "4K",
            synopsis = "After a global pandemic destroys civilization, a hardened survivor takes charge of a 14-year-old girl who may be humanity's last hope.",
            genres = listOf("Action", "Adventure", "Drama"),
            isSeries = true,
            platform = "HBO Max",
            isFavorite = true
        )
    )

    private val _movies = MutableStateFlow(initialMovies)
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _series = MutableStateFlow(initialSeries)
    val series: StateFlow<List<Movie>> = _series.asStateFlow()

    fun getMovieById(id: String): Movie? {
        return _movies.value.find { it.id == id } ?: _series.value.find { it.id == id }
    }

    fun toggleFavorite(id: String) {
        _movies.update { list ->
            list.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
        _series.update { list ->
            list.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }

    fun search(query: String): List<Movie> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        return (_movies.value + _series.value).filter {
            it.title.lowercase().contains(q) || it.genres.any { g -> g.lowercase().contains(q) }
        }
    }
}

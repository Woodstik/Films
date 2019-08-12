package com.example.films.data.sources.remote

import com.example.films.data.models.Post
import io.reactivex.Single
import java.util.*

class MockPostService : PostService {
    override fun getPosts(): Single<List<Post>> {
        return Single.just(listOf(
            Post(
                "cp0b93",
                "New poster for H. P. Lovecraft’s Color Out of Space starring Nicolas Cage",
                Date(1565547905000),
                "i.redd.it",
                "https://b.thumbs.redditmedia.com/7WjAPI0r9CFnQMSy6JXiCGTk7y4pZ34acQMrtNMq3iU.jpg",
                46366,
                emptyList()
            ),
            Post(
                "coytv7",
                "According to his brother Don, John Williams has about 135 minutes worth of music to write for Star Wars: The Rise of Skywalker and every theme we've ever heard will be compiled into this last effort",
                Date(1565540957000),
                "youtube.com",
                "https://b.thumbs.redditmedia.com/dJ8pDsccw6f-wMtKv6-8LNJCzVCkoURbZsLeL7A4QEM.jpg",
                785,
                emptyList()
            ),
            Post(
                "coxz4s",
                "It director Andy Muschietti to produce adaptation of Stephen King's Roadwork",
                Date(1565536831000),
                "flickeringmyth.com",
                "https://b.thumbs.redditmedia.com/Zs_tucETsgDxrpJ71jmG0veL44jOzrUhDpWlGtNi4Uw.jpg",
                502,
                emptyList()
            )
        ))
    }
}

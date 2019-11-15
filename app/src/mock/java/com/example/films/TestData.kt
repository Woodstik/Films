package com.example.films

import com.example.films.data.models.Movie
import java.util.*

object TestData {

    object Movies {
        private val all = listOf(
           Movie(
               320288,
               "Dark Phoenix",
               "The X-Men face their most formidable and powerful foe when one of their own, Jean Grey, starts to spiral out of control. During a rescue mission in outer space, Jean is nearly killed when she's hit by a mysterious cosmic force. Once she returns home, this force not only makes her infinitely more powerful, but far more unstable. The X-Men must now band together to save her soul and battle aliens that want to use Grey's new abilities to rule the galaxy.",
               Date(),
               6.0,
               "https://image.tmdb.org/t/p/w185/kZv92eTc0Gg3mKxqjjDAM73z9cy.jpg",
               "https://image.tmdb.org/t/p/w780/phxiKFDvPeQj4AbkvJLmuZEieDU.jpg",
               listOf("Jessica Chastain, James McAvoy, Micheal Fassbender"),
               "https://youtu.be/QWbMckU3AOQ"
           ),
           Movie(
               412117,
               "The Secret Life of Pets 2",
               "What happens with our pets when weâ€™re not home? This movie continues the original movie with Max and his friends.",
               Date(),
               6.1,
               "https://image.tmdb.org/t/p/w185/q3mKnSkzp1doIsCye6ap4KIUAbu.jpg",
               "https://image.tmdb.org/t/p/w780/etaok7q2E5tV36oXe7GQzhUQ4fX.jpg",
               listOf("Kevin Hart", "Jenny Slate", "Eric Stonestreet"),
               "https://youtu.be/pKLGUuJftl0"
           ),
           Movie(
               535581,
               "The Dead Don't Die",
               "In a small peaceful town, zombies suddenly rise to terrorize the town. Now three bespectacled police officers and a strange Scottish morgue expert must band together to defeat the undead.",
               Date(),
               5.7,
               "https://image.tmdb.org/t/p/w185/ycMSfP8KRFsVUWfbSxSFpD97QfD.jpg",
               "https://image.tmdb.org/t/p/w780/cXyfAViYly0Lk2CVpEKgYbt9wKQ.jpg",
               listOf("Bill Murray", "Adam Driver", "Selena Gomez"),
               "https://youtu.be/bs5ZOcU6Bnw"
           ),
           Movie(
               329996,
               "Dumbo",
               "A young elephant, whose oversized ears enable him to fly, helps save a struggling circus, but when the circus plans a new venture, Dumbo and his friends discover dark secrets beneath its shiny veneer.",
               Date(),
               6.6,
               "https://image.tmdb.org/t/p/w185/279PwJAcelI4VuBtdzrZASqDPQr.jpg",
               "https://image.tmdb.org/t/p/w780/5tFt6iuGnKapHl5tw0X0cKcnuVo.jpg",
               listOf("Eva Green", "Colin Farrell", "Danny DeVito")
           ),
           Movie(
               301528,
               "Toy Story 4",
               "Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.",
               Date(),
               0.8,
               "https://image.tmdb.org/t/p/w185/pDCiYUc09wnmg855P3gFTZOoBCv.jpg",
               "https://image.tmdb.org/t/p/w780/3FXLxOnd5LnvK8F5jUbIXOF9p9Y.jpg",
               listOf("Keanu Reeves", "Tom Hanks", "Tim Allen"),
               "https://youtu.be/wmiIUN-7qhE"
           ),
            Movie(
                420817,
                "Aladdin",
                "A kindhearted street urchin named Aladdin embarks on a magical adventure after finding a lamp that releases a wisecracking genie while a power-hungry Grand Vizier vies for the same lamp that has the power to make their deepest wishes come true.",
                Date(),
                7.2,
                "https://image.tmdb.org/t/p/w185/3iYQTLGoy7QnjcUYRJy4YrAgGvp.jpg",
                "https://image.tmdb.org/t/p/w780/v4yVTbbl8dE1UP2dWu5CLyaXOku.jpg",
                trailerUrl = "https://youtu.be/foyufD52aog"
            ),
            Movie(
                373571,
                "Godzilla: King of the Monsters",
                "The new story follows the heroic efforts of the crypto-zoological agency Monarch as its members face off against a battery of god-sized monsters, including the mighty Godzilla, who collides with Mothra, Rodan, and his ultimate nemesis, the three-headed King Ghidorah. When these ancient super-speciesâ€”thought to be mere mythsâ€”rise again, they all vie for supremacy, leaving humanityâ€™s very existence hanging in the balance.",
                Date(),
                6.5,
                "https://image.tmdb.org/t/p/w185/pU3bnutJU91u3b4IeRPQTOP8jhV.jpg",
                "https://image.tmdb.org/t/p/w780/uovH5k4BAEPqXqxgwVrTtqH169g.jpg"
            ),
            Movie(
                458156,
                "John Wick: Chapter 3 Parabellum",
                "Super-assassin John Wick returns with a \$14 million price tag on his head and an army of bounty-hunting killers on his trail. After killing a member of the shadowy international assassin’s guild, the High Table, John Wick is excommunicado, but the world’s most ruthless hit men and women await his every turn.",
                Date(),
                7.3,
                "https://image.tmdb.org/t/p/w185/ziEuG1essDuWuC5lpWUaw1uXY2O.jpg",
                "https://image.tmdb.org/t/p/w780/vVpEOvdxVBP2aV166j5Xlvb5Cdc.jpg"
            ),
            Movie(
                299534,
                "Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                Date(),
                8.5,
                "https://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                "https://image.tmdb.org/t/p/w780/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg"
            ),
            Movie(
                502416,
                "Ma",
                "Sue Ann is a loner who keeps to herself in her quiet Ohio town. One day, she is asked by Maggie, a new teenager in town, to buy some booze for her and her friends, and Sue Ann sees the chance to make some unsuspecting, if younger, friends of her own.",
                Date(),
                5.7,
                "https://image.tmdb.org/t/p/w185/6n7ASmQ1wY2cxTubFFGlcvPpyk7.jpg",
                "https://image.tmdb.org/t/p/w780/mBOv5YrX5QGr5CusK0PKSHuxOt9.jpg"
            )
       )

        fun all() = all

        fun getUpcoming() : List<Movie>{
            return all.takeLast(5)
        }

        fun getNewReleases() : List<Movie> {
            return all.take(5)
        }

        fun getById(id: Int) : Movie {
            return all.find { it.id == id }!!
        }
    }
}
package woowacourse.movie.reservation.presenter

import android.content.Context
import android.widget.Toast
import woowacourse.movie.list.model.Movie
import woowacourse.movie.reservation.contract.MovieReservationContract
import woowacourse.movie.reservation.model.MovieReservationMovieData
import woowacourse.movie.reservation.model.MovieReservationTicketCountData

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
) : MovieReservationContract.Presenter {
    private val model: MovieReservationTicketCountData = MovieReservationTicketCountData()
    private var toast: Toast? = null

    private val ticketCount
        get() = model.ticketCount

    override fun setCurrentResultTicketCountInfo() {
        view.showCurrentResultTicketCountView(ticketCount.number)
    }

    override fun storeMovieData(movieData: Movie?) {
        MovieReservationMovieData.movieData = movieData
    }

    override fun setMovieInfo() {
        val movie = MovieReservationMovieData.movieData as Movie
        view.setMovieView(movie)
    }

    override fun setPlusButtonClickInfo() {
        model.plusTicketCount()
        view.showCurrentResultTicketCountView(ticketCount.number)
    }

    override fun setMinusButtonClickInfo() {
        runCatching {
            model.minusTicketCount()
            view.showCurrentResultTicketCountView(ticketCount.number)
        }.onFailure {
            toast?.cancel()
            toast = Toast.makeText(view as Context, it.message, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    override fun setTicketingButtonClickInfo() {
        view.startMovieTicketActivity(ticketCount.number)
    }
}
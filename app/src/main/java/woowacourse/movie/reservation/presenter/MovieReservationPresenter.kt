package woowacourse.movie.reservation.presenter

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.reservation.contract.MovieReservationContract
import woowacourse.movie.reservation.model.MovieReservationDataResource
import woowacourse.movie.reservation.model.MovieReservationTicketCountData
import java.time.LocalTime

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
) : MovieReservationContract.Presenter {
    val model = MovieReservationTicketCountData

    private val ticketCount
        get() = model.ticketCount

    override fun setCurrentResultTicketCountInfo() {
        view.showCurrentResultTicketCountView(ticketCount.number)
    }

    override fun storeMovieId(movieId: Long) {
        MovieReservationDataResource.movieId = movieId
    }

    override fun setMovieInfo() {
        val movieId = MovieReservationDataResource.movieId.toInt()
        view.setMovieView(MovieDataSource.movieList[movieId])
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
            view.showToast(it.message ?: "")
        }
    }

    override fun setTicketingButtonClickInfo() {
        view.startMovieTicketActivity(ticketCount)
    }

    override fun setSpinnerInfo() {
        view.showSpinner(MovieReservationDataResource.screeningDates, MovieReservationDataResource.screeningTimesWeekdays)
    }

    override fun setSpinnerDateItemInfo() {
        view.setOnSpinnerDateItemSelectedListener(MovieReservationDataResource.screeningDates)
    }

    override fun setSpinnerTimeItemInfo() {
        view.setOnSpinnerTimeItemSelectedListener(MovieReservationDataResource.screeningTimesWeekdays)
    }

    override fun storeSelectedTime(selectedTime: LocalTime) {
        MovieReservationDataResource.selectedScreeningTime = selectedTime
    }
}

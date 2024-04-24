package woowacourse.movie.reservation.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.reservation.contract.MovieReservationContract

class DetailPresenterTest {
    private lateinit var view: MovieReservationContract.View
    private lateinit var presenter: MovieReservationContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<MovieReservationContract.View>()
        presenter = MovieReservationPresenter(view)
    }

    @Test
    fun `영화 정보가 표시되어야 한다`() {
        every { view.setMovieView(any()) } just runs
        // when
        presenter.setMovieInfo()
        // then
        verify {
            view.setMovieView(any())
        }
    }

    @Test
    fun `예약 인원이 1일 때 플러스 버튼을 누르면 예약 인원이 증가해야 한다`() {
        every { view.showCurrentResultTicketCountView(any()) } just runs
        // when
        presenter.setPlusButtonClickInfo()
        // then
        verify { view.showCurrentResultTicketCountView(2) }
    }

    @Test
    fun `예약 인원이 2일때 마이너스 버튼을 누르면 1이 되어야 한다`() {
        every { view.showCurrentResultTicketCountView(any()) } just runs
        presenter.setPlusButtonClickInfo()
        // when
        presenter.setMinusButtonClickInfo()
        // then
        verify { view.showCurrentResultTicketCountView(1) }
    }

    @Test
    fun `예약 인원이 1일 때는 마이너스 버튼을 누르면 토스트로 예외를 알려주어야 한다`() {
        every { view.showToast(any()) } just runs
        // when
        presenter.setMinusButtonClickInfo()
        // then
        verify { view.showToast("개수는 1 이상이어야 합니다.") }
    }

    @Test
    fun `예매 완료 버튼을 누르면 예매 내역이 보여져야 한다`() {
        every { view.startMovieTicketActivity(any()) } just runs
        // when
        presenter.setTicketingButtonClickInfo()
        // then
        verify { view.startMovieTicketActivity(1) }
    }
}

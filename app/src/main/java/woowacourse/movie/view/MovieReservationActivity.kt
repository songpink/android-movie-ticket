package woowacourse.movie.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Ticket
import woowacourse.movie.presenter.MovieReservationPresenter
import java.time.format.DateTimeFormatter

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    private lateinit var titleView: TextView
    private lateinit var screeningDateView: TextView
    private lateinit var runningDateView: TextView
    private lateinit var ticketCountView: TextView
    private lateinit var posterView: ImageView
    private lateinit var descriptionView: TextView
    private lateinit var minusNumberButton: Button
    private lateinit var plusNumberButton: Button
    private lateinit var ticketingButton: Button
    private val presenter = MovieReservationPresenter(this@MovieReservationActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_reservation)
        initView()
        presenter.setMovieInfo()
        presenter.setCurrentResultTicketCountInfo()
        presenter.setClickListener()
    }

    private fun initView() {
        titleView = findViewById(R.id.movie_detail_title)
        screeningDateView = findViewById(R.id.movie_detail_screening_date)
        runningDateView = findViewById(R.id.movie_detail_running_date)
        descriptionView = findViewById(R.id.movie_detail_description)
        ticketCountView = findViewById(R.id.ticket_count)
        posterView = findViewById(R.id.movie_detail_poster)
        minusNumberButton = findViewById(R.id.minus_button)
        plusNumberButton = findViewById(R.id.plus_button)
        ticketingButton = findViewById(R.id.ticketing_button)
    }

    override fun setMovieView() {
        getSerializableModel()?.let { movie ->
            val formattedScreeningDate =
                movie.screeningDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

            titleView.text = movie.title
            screeningDateView.text = formattedScreeningDate
            runningDateView.text = movie.runningTime.toString()
            descriptionView.text = movie.description
            posterView.setImageResource(movie.posterResourceId)
        }
    }

    private fun getSerializableModel(): Movie? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Movie.KEY_NAME_MOVIE, Movie::class.java)
        } else {
            intent.getSerializableExtra(Movie.KEY_NAME_MOVIE) as? Movie
        }
    }

    override fun showCurrentResultTicketCountView(info: Int) {
        ticketCountView.text = info.toString()
    }

    override fun setOnButtonsClickListener() {
        minusNumberButton.setOnClickListener {
            runCatching { presenter.clickMinusNumberButton() }
                .onFailure { Log.d("error", "setClickListener: ${it.message}") }
        }
        plusNumberButton.setOnClickListener {
            presenter.clickPlusNumberButton()
        }
        ticketingButton.setOnClickListener {
            ticketing()
        }
    }

    override fun ticketing() {
        val intent = Intent(this, MovieTicketActivity::class.java)
        intent.putExtra(Ticket.KEY_NAME_TICKET, presenter.ticketCount)
        this.startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val count = findViewById<TextView>(R.id.ticket_count).text.toString().toInt()
        outState.putInt("count", count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let {
            val count = it.getInt("count")
            val countTextView = findViewById<TextView>(R.id.ticket_count)
            countTextView.text = count.toString()
        }
    }
}

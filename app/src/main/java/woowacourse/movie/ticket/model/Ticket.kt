package woowacourse.movie.ticket.model

import java.io.Serializable
import java.time.LocalDateTime

data class Ticket(
    val title: String,
    val screeningDate: LocalDateTime,
    val price: Int,
    val id: Long,
) : Serializable
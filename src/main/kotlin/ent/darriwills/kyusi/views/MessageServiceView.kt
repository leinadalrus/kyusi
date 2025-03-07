package ent.darriwills.kyusi.views

import java.time.Instant

public data class MessageServiceView(val content: String,
    val user: UserView,
    val sent: Instant,
    val id: String? = null)
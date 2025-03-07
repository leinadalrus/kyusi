package ent.darriwills.kyusi.middleware.servies

import ent.darriwills.kyusi.views.MessageServiceView

public interface MessageService {
    fun latest(): List<MessageServiceView>
    fun after(messageId: String): List<MessageServiceView>
    fun post(message: MessageServiceView)
}
package vvzl.vw.api

import org.springframework.stereotype.Service
import vvzl.vw.properties.BotProperty
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


@Service
class TelegramSender(
    private val botProperty: BotProperty,
) : TelegramLongPollingBot() {


    override fun getBotToken() = botProperty.token

    override fun getBotUsername() = botProperty.username

    override fun onUpdateReceived(update: Update) {

        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val responseText = "я живой"
            sendNotification(chatId, responseText)
        }
    }

    private fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        execute(responseMessage)

    }


}
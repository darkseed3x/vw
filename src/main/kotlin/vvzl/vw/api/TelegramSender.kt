package vvzl.vw.api

import org.springframework.stereotype.Service
import vvzl.vw.properties.BotProperty
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


@Service
class TelegramSender(
    private val botProperty: BotProperty,
) : TelegramLongPollingBot() {


    override fun getBotToken() = botProperty.token

    override fun getBotUsername() = botProperty.username

    override fun onUpdateReceived(update: Update) {

        if (update.hasMessage()) {
            sendNotification(update.message.chatId, "я живой")
        }else if(update.hasCallbackQuery()){
            sendNotification(update.callbackQuery.from.id, "responseText")
        }else {
            throw IllegalStateException("Not supported")
        }
    }

    private fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.replyMarkup = getInlineKeyboard(
            listOf(
                listOf("dddd"),
                listOf("ssss")
            )
        )
        execute(responseMessage)

    }

    private fun getInlineKeyboard(allButtons: List<List<String>>): InlineKeyboardMarkup {
        val keyboardMarkup = InlineKeyboardMarkup()
        keyboardMarkup.keyboard = allButtons.map { list ->
            list.map() { mapToButton(it) }.toList()
        }.toList()
        return keyboardMarkup

    }

    private fun mapToButton(str: String): InlineKeyboardButton {
        return InlineKeyboardButton().apply {
            text = str
            callbackData = str
        }


    }
}
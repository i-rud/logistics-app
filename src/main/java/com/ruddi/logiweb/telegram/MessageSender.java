package com.ruddi.logiweb.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * class for sending messages to telegram chat
 * @author Ivan Rud
 */
public class MessageSender {

    @Autowired
    LogiwebBot logiwebBot;

    private final String channelId = "-1001405134260";

    public void send(String message) {
        SendMessage sender = new SendMessage();
        sender.setParseMode(ParseMode.MARKDOWN);
        sender.setChatId(channelId);

        sender.setText(message);

        try {
            logiwebBot.execute(sender);
        } catch (TelegramApiException tae) {
            tae.printStackTrace();
        }
    }
}

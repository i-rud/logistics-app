package com.ruddi.logiweb.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * telegram bot
 * @author Ivan Rud
 */
public class LogiwebBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "IRCLogiwebBot";
    }

    @Override
    public String getBotToken() {
        return "1892524158:AAFs4u7GbsJ12qs6Th3qs_XrmCE3MKUG-8Q";
    }

    @Override
    public void onUpdateReceived(Update update) {
    }
}

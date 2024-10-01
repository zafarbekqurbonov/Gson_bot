package uz.pdp;//package uz.pdp;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "@pdp_darslari_bot";
    }

    @Override
    public String getBotToken() {
        return "7390082017:AAHo3jmFGG5o1LKhRt9MGkOHJvksLGojtxY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();

            if (text.equals("/start")) {
                sendUsersList(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case "user_eshmat":
                    sendUserPosts(chatId, "Eshmat");
                    break;
                case "user_toshmat":
                    sendUserPosts(chatId, "Toshmat");
                    break;
                case "user_hikmat":
                    sendUserPosts(chatId, "Hikmat");
                    break;
                case "user_qudrat":
                    sendUserPosts(chatId, "Qudrat");
                    break;
                case "user_azamat":
                    sendUserPosts(chatId, "Azamat");
                    break;
                case "post_comment":
                    sendComments(chatId);
                    break;
                case "go_back":
                    sendUsersList(chatId);
                    break;
            }
        }
    }

    private void sendUsersList(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Users list:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        buttons.add(Arrays.asList(
                createInlineButton("Eshmat posts", "user_eshmat"),
                createInlineButton("Toshmat posts", "user_toshmat")
        ));
        buttons.add(Arrays.asList(
                createInlineButton("Hikmat posts", "user_hikmat"),
                createInlineButton("Qudrat posts", "user_qudrat")
        ));
        buttons.add(Arrays.asList(
                createInlineButton("Azamat posts", "user_azamat")
        ));

        markup.setKeyboard(buttons);
        message.setReplyMarkup(markup);

        executeMessage(message);
    }

    private void sendUserPosts(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(userName + " posts:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        buttons.add(Arrays.asList(
                createInlineButton("Comment", "post_comment")
        ));
        buttons.add(Arrays.asList(
                createInlineButton("go back", "go_back")
        ));

        markup.setKeyboard(buttons);
        message.setReplyMarkup(markup);

        executeMessage(message);

        SendMessage postMessage = new SendMessage();
        postMessage.setChatId(chatId);
        postMessage.setText("1-oktabr ustoz va murabbiylar kuni muborak bulsin\uD83C\uDF89\uD83C\uDF89");

        executeMessage(postMessage);
    }

    private void sendComments(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Comments:\n" +
                "1. Bugun 1-oktabr\n" +
                "2. Ertaga chorshanba\n" +
                "3. Imtihonga ham oz kunlar qoldi\n" +
                "4. Oktabr oyini ham boshlab oldik\n" +
                "5. Hammani o'qishlariga omad tilayman");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        buttons.add(Arrays.asList(
                createInlineButton("go back", "go_back")
        ));

        markup.setKeyboard(buttons);
        message.setReplyMarkup(markup);

        executeMessage(message);
    }

    private InlineKeyboardButton createInlineButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyTelegramBot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
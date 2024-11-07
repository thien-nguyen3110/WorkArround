package coms309.controller;

import coms309.entity.Message;
import coms309.repository.MessageRepository;
import coms309.service.ChatService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
public class ChatSocket {
//     private static MessageRepository msgRepo;

     @Autowired
     private static ChatService chatService;

     @Autowired
     private SimpMessagingTemplate messagingTemplate;

//    @Autowired
//    public void setMessageRepository(MessageRepository repo, ChatService chatservice){
//        msgRepo= repo;
//        chatService = chatservice;
//
//    }
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("groupChatId") Long groupChatId) throws IOException {
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);


        sendMessageToGroupChat(username, getChatHistory(groupChatId));
        broadcast("User: " + username + " has Joined the Chat");
        broadcastUserCount();

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        String username = sessionUsernameMap.get(session);
        if (message.startsWith("@")) {
            String destUsername = message.split(" ")[0].substring(1);
            sendMessageToGroupChat(destUsername, "[DM] " + username + ": " + message);
            sendMessageToGroupChat(username, "[DM] " + username + ": " + message);
        } else {
            broadcast(username + ": " + message);
        }

        msgRepo.save(new Message(username, message , null));

    }


    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        broadcast(username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

        throwable.printStackTrace();
    }

    private void sendMessageToGroupChat(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String getChatHistory(Long groupId) {
        List<Message> messages = chatService.getChatHistory(groupId);
        StringBuilder sb = new StringBuilder();
        if (messages != null && !messages.isEmpty()) {
            for (Message message : messages) {
                sb.append(message.getSender()).append(": ").append(message.getContent()).append("\n");
            }
        }
        return sb.toString();
    }

    private void broadcastUserCount() {
        String message = "Current users: " + usernameSessionMap.size();
        broadcast(formatMessage(message));
    }

    private String formatMessage(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "[" + timestamp + "] " + message;
    }

}

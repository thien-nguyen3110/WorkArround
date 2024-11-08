package coms309.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Controller  // Required for Spring Boot endpoint
@EnableWebSocketMessageBroker
@ServerEndpoint(value = "/chat/{username}")  // WebSocket URL
public class ChatSocket {

	private static ChatRepository msgRepo;

	@Autowired
	public void setChatRepository(ChatRepository repo) {
		msgRepo = repo;  // Setting the static variable
	}

	// Thread-safe maps for session and username management
	private static final Map<Session, String> sessionUsernameMap = new ConcurrentHashMap<>();
	private static final Map<String, Session> usernameSessionMap = new ConcurrentHashMap<>();

	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("User {} connected", username);

		// Store connecting user information
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);

		// Send chat history to the newly connected user
		sendMessageToParticularUser(username, getChatHistory());

		// Broadcast that a new user joined
		broadcast("User: " + username + " has joined the chat.");
	}

	@OnMessage
	public void onMessage(Session session, String chat) throws IOException {
		String username = sessionUsernameMap.get(session);
		logger.info("Message received from {}: {}", username, chat);

		if (chat.startsWith("@")) {
			// Direct chat handling
			String destUsername = chat.split(" ")[0].substring(1);
			String directMessage = "[DM] " + username + ": " + chat.substring(destUsername.length() + 2);
			sendMessageToParticularUser(destUsername, directMessage);
			sendMessageToParticularUser(username, directMessage);
		} else {
			// Broadcast chat
			broadcast(username + ": " + chat);
		}

		// Save chat history to repository
		msgRepo.save(new Chat(username, chat));
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		String username = sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
		logger.info("User {} disconnected", username);

		// Broadcast that the user disconnected
		broadcast(username + " disconnected");
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		String username = sessionUsernameMap.get(session);
		logger.error("Error occurred for user {}: {}", username, throwable.getMessage());
		throwable.printStackTrace();
	}

	private void sendMessageToParticularUser(String username, String chat) {
		try {
			Session session = usernameSessionMap.get(username);
			if (session != null && session.isOpen()) {
				session.getBasicRemote().sendText(chat);
			}
		} catch (IOException e) {
			logger.error("Failed to send message to {}: {}", username, e.getMessage());
		}
	}

	private void broadcast(String chat) {
		sessionUsernameMap.keySet().parallelStream().forEach(session -> {
			try {
				session.getBasicRemote().sendText(chat);
			} catch (IOException e) {
				logger.error("Failed to broadcast message: {}", e.getMessage());
			}
		});
	}

	private String getChatHistory() {
		List<Chat> chats = msgRepo.findAll();
		StringBuilder sb = new StringBuilder();
		if (chats != null && !chats.isEmpty()) {
			chats.forEach(chat -> sb.append(chat.getUserName())
					.append(": ")
					.append(chat.getContent())
					.append("\n"));
		}
		return sb.toString();
	}

	// New Feature: List active users
	public String listActiveUsers() {
		return String.join(", ", usernameSessionMap.keySet());
	}

	// New Feature: Clear chat history
	public void clearChatHistory() {
		msgRepo.deleteAll();
		logger.info("Chat history cleared.");
	}
}
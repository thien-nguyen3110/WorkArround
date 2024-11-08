package coms309.websocket;

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocketMessageBroker
public class ChatSocketConfig {

	@Autowired
	private Environment env;

	/**
	 * Registers the ServerEndpointExporter bean, which is required for WebSocket
	 * endpoint configuration. This bean is only needed when running in an
	 * embedded server (e.g., Spring Boot).
	 *
	 * @return ServerEndpointExporter instance if running in an embedded server,
	 *         null otherwise.
	 **/

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		// Check if the application is running in an embedded environment
		if (Boolean.parseBoolean(env.getProperty("spring.websocket.embedded", "true"))) {
			return new ServerEndpointExporter();
		}
		return null;
	}
}
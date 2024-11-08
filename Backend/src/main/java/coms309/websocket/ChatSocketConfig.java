package coms309.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocketMessageBroker
public class ChatSocketConfig {

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
		return new ServerEndpointExporter();
	}
}
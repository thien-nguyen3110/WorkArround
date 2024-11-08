package coms309.websocket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@EnableWebSocketMessageBroker
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Find all messages sent by a specific user.
     * @param userName the username to filter messages by
     * @return a list of messages sent by the given user
     **/

    List<Chat> findByUserName(String userName);

    /**
     * Find the most recent messages, limited by a given count.
     * @param limit the number of recent messages to retrieve
     * @return a list of recent messages, limited by the specified count
     **/

    @Query(value = "SELECT m FROM Chat m ORDER BY m.sent DESC")
    List<Chat> findRecentMessages(@Param("limit") int limit);

    /**
     * Find a message by ID.
     * @param id the ID of the message
     * @return an optional containing the found message, or empty if not found
     **/

    Optional<Chat> findById(Long id);
}
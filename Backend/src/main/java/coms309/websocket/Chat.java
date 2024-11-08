package coms309.websocket;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Entity
@EnableWebSocketMessageBroker
@Table(name = "messages")
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Lob
    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent", nullable = false)
    private LocalDateTime sent = LocalDateTime.now();

    // Default constructor
    public Chat() {}

    // Constructor for userName and content
    public Chat(String userName, String content) {
        this.userName = userName;
        this.content = content;
        this.sent = LocalDateTime.now();
    }

    // Constructor for userName, content, and sent timestamp
    public Chat(String userName, String content, LocalDateTime sent) {
        this.userName = userName;
        this.content = content;
        this.sent = sent;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public void setSent(LocalDateTime sent) {
        this.sent = sent;
    }
}
package coms309.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private String content;
    private String sender;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "group_chat")
    private GroupChat groupChat;

    public Message(){}
    public Message( String content , String sender , GroupChat groupChat){
        this.content= content;
        this.sender= sender;
        this.timestamp = LocalDateTime.now();
        this.groupChat=groupChat;

    }
}

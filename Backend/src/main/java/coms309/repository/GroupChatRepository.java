package coms309.repository;

import coms309.entity.GroupChat;
import coms309.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
    Optional<GroupChat> findById(Long groupchatId);

    //List<Message> findAllByGroupChatIdOrderByTimestamp(Long groupChatId);
}

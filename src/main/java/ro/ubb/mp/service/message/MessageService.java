package ro.ubb.mp.service.message;

import ro.ubb.mp.controller.dto.request.MessageRequestDTO;
import ro.ubb.mp.dao.model.Message;
import ro.ubb.mp.dao.model.User;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> findById(Long id);
    Message saveMessage(MessageRequestDTO messageDTO);
    void deleteMessageById(Long id);
    List<Message> getMessagesBetweenUsers(Long id1, Long id2);
    List<Message> getAllUserMessages(Long id);

    User findUserById(Long id);
}

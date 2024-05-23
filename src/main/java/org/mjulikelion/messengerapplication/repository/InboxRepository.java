package org.mjulikelion.messengerapplication.repository;

import org.mjulikelion.messengerapplication.domain.Inbox;
import org.mjulikelion.messengerapplication.domain.Message;
import org.mjulikelion.messengerapplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InboxRepository extends JpaRepository<Inbox, UUID> {

     boolean existsByUserAndMessage(User user, Message message);
     Optional<List<Inbox>> findInboxesByUser(User user);
}

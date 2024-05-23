package org.mjulikelion.messengerapplication.repository;

import org.mjulikelion.messengerapplication.domain.Message;
import org.mjulikelion.messengerapplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    Optional<List<Message>> findAllBySender(User user);
}

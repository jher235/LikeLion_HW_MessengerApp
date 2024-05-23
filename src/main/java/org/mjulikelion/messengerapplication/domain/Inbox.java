package org.mjulikelion.messengerapplication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "inbox")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inbox extends BaseEntity{

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Message message;

}

package org.mjulikelion.messengerapplication.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Entity(name = "user")
@Builder
@NoArgsConstructor
@Getter
public class User extends BaseEntity{

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;


    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Inbox> ReceivedMessages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Message> messages;

    public void changeName(String name){
        this.name = name;
    }


}

package org.mjulikelion.messengerapplication.domain;


import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mjulikelion.messengerapplication.exception.ConflictException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Entity(name = "message")
@Builder
@NoArgsConstructor
@Getter
public class Message extends BaseEntity{

    @Column(nullable = false)
    @Size(max = 50)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Inbox> inboxes;

    @Column
    private Boolean viewed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User sender;


    @Column
    private UUID ancestorMessageId;

    public void read(){
        if(!isRead()) this.viewed=true;
    }

    public boolean isRead(){
        if( this.viewed==null || !this.viewed) return false;
        return true;
    }

    public void modify(String newTitle, String newContent){
        if(isRead()){
            throw new ConflictException(ErrorCode.MESSAGE_VIEWED_CONFLICT);
        }
        this.title = newTitle;
        this.content = newContent;
    }

}

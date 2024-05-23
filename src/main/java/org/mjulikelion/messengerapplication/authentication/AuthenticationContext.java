package org.mjulikelion.messengerapplication.authentication;

import lombok.Getter;
import lombok.Setter;
import org.mjulikelion.messengerapplication.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

//spring security를 사용 시 @AuthenticationPrincipal를 사용할 수 있다고 한다.
@RequestScope
@Getter
@Setter
@Component
public class AuthenticationContext {
    private User principal;
}

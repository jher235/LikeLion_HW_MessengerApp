package org.mjulikelion.messengerapplication.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.messengerapplication.domain.User;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.repository.UserRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@AllArgsConstructor
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String accessToken = AuthenticationExtractor.extract(request);
        log.info(accessToken);
        UUID userId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        User user = findUserByUserId(userId);
        authenticationContext.setPrincipal(user);
        return true;
    }

    private User findUserByUserId(UUID uuid){
        return userRepository.findById(uuid).orElseThrow(()-> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

}

package org.mjulikelion.messengerapplication.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.resolve.AuthenticatedUserArgumentResolver;
import org.mjulikelion.messengerapplication.authentication.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticatedUserArgumentResolver authenticatedUserResolver;

    private String[] excludePathPatterns = {"/auth/login", "/auth/signup"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedUserResolver);
    }
}

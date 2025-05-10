package by.zemich.userservice.infrastructure.security;

import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.infrastructure.security.annotations.CurrentUserId;
import by.zemich.userservice.infrastructure.security.entities.UserDetailsImpl;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserId.class)
                && parameter.getParameterType().equals(UUID.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return extractFromUserDetails(userDetails);
    }

    UserId extractFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof UserDetailsImpl userDetailsImpl) {
            return new UserId(userDetailsImpl.getId());
        }
        throw new IllegalArgumentException("UserDetails does not contain UserId");
    }
}

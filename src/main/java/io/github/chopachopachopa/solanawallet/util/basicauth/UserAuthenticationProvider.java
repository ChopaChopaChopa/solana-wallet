package io.github.chopachopachopa.solanawallet.util.basicauth;

import io.github.chopachopachopa.solanawallet.exception.ErrorCode;
import io.github.chopachopachopa.solanawallet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val username = authentication.getName();
        val rawPassword = authentication.getCredentials().toString();

        UserDetails userDetails = userService.loadUserByUsername(username);
        val encodedPassword = userDetails.getPassword();

        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            return new UsernamePasswordAuthenticationToken(
                username,
                encodedPassword,
                userDetails.getAuthorities()
            );
        }
        else {
            throw new BadCredentialsException(ErrorCode.CREDENTIALS.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

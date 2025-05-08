package kr.co.lotteon.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "로그인에 실패했습니다.";

        if (exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화되어 있습니다.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        }

        response.sendRedirect("/member/login?error=true&message=" + URLEncoder.encode(errorMessage, "UTF-8"));

    }
}

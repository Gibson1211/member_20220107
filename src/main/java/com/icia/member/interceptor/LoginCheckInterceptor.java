package com.icia.member.interceptor;

import com.icia.member.common.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    // @Override: 부모가 가진 메서드를 재정의 함
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // 사용자가 요청한 주소
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        // 세션 가져옴
        HttpSession session = request.getSession();
        // 세센에 로그인 정보가 있는지 확인
        if(session.getAttribute(SessionConst.LOGIN_EMAIL) == null) {
            // 위 if문에 해당하는 건은 미로그인 상태를 의미
            // 로그인을 하지 않은 경우 바로 로그인페이지로 보냄.
            // 로그인 후 로그인 직전 페이지를 보여준다.
            response.sendRedirect("/member/login?redirectURL=" + requestURI);
            // 사용자가 요구한 주소값을 가지고 감.
            // MemberController loginForm 메서드를 일부 수정해야 함.
            return false;
        } else {
            // 여기는 로그인 한 상태를 의미
            return true;
        }
    }
}

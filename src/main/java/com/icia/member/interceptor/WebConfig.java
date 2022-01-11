package com.icia.member.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration: 설정정보를 스프링 실행 시 등록해 줌.
// interceptor를 사용하지 않고 싶을때는 @Configuration의 주석처리만으로 가능.
//@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 로그인 여부에 따른 접속가능 페이지 구분.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                // 만든 LoginCheckInterceptor 클래스 내용을 넘김
                .order(1) // 해당 인터셉터가 적용되는 순서(우선순위)
                .addPathPatterns("/**") // 해당 프로젝트의 모든 주소에 대해 인터셉터를 적용
                .excludePathPatterns("/", "/member/save", "/member/login", "/member/logout", "/css/**");
                                        // 제외 할 주소(ex. 회원가입, 메인페이지 등)
    }


}

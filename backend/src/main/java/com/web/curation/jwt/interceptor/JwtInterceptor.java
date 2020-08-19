package com.web.curation.jwt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import com.web.curation.dto.account.AuthenticationRequest;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.account.LoginService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		System.out.println("/tugether/로 시작하는 요청을 낚아채는 인터셉터 " + request.getMethod()+" : "+request.getServletPath());
		
		// option요청은 바로 통과시켜주기 
		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}else {
			// request의 Header에서 jwt-auth_token으로 넘어온 녀석을 찾아본다.
			String token = request.getHeader("jwt-auth-token");
			System.out.println("요청 시 헤더에 담아서 보낸 토큰 입니다.  jwt-auth-token: "+token);
	
			if(jwtService.checkValid(token)) { //유효한 토큰이면 진행, 아니면 예외발생 
				return true;
			}else {
				throw new RuntimeException("유효한 토큰이 아닙니다!!!");
			}
			
		}
	}
}

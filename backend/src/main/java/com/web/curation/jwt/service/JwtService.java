package com.web.curation.jwt.service;

import java.util.Date;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.JwtValidationException;
import com.web.curation.exception.TugetherException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.web.curation.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    /**
     * TODO 토큰 페이로드 부분에 꼭 필요한 정보만 담아 토큰 길이를 줄이자.
     * Jwt 토큰 발급
     */
    public String createJwtToken(final User user) {

        log.info("JwtService.createJwtToken -> 로그인 성공 시 사용자 정보를 기반으로 Jwt Token을 생성하여 반환한다.");
        log.info("expire Min Time: " + expireMin);

        final JwtBuilder tokenBuilder = Jwts.builder();

        // JWT 토큰 : 헤더 + 페이로드 + 서명
        // 헤더
        tokenBuilder.setHeaderParam("typ", "JWT"); // 토큰타입
        // 페이로드
        tokenBuilder.setSubject("로그인토큰")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("AuthenticationResponse", user);
        // 서명 - 비밀키를 이용한 암호화.
        tokenBuilder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
        // 직렬화 처리
        final String token = tokenBuilder.compact();
        log.debug("발급된 토큰 ->" + token);
        return token;
    }

    // 전달받은 토큰을 secret 값을 이용해 파싱하는 과정에서 토큰 위변조를 감지하여 예외를 발생시킨다.
    public void checkValid(final String jwt) {

        // 토큰의 유효성 확인
        try {
            log.info("절달받은 토큰 -> " + jwt);
            getDecodeToken(jwt);
        } catch (SignatureException e) {
            log.info("유효하지 않은 토큰입니다.");
            throw new JwtValidationException();
        } catch (ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다.");
            throw new JwtValidationException();
        } catch (JwtException e) {
            log.info("토큰이 변조되었습니다.");
            throw new JwtValidationException();
        }
    }

    // 비밀키를 이용해 토큰 파싱 과정에서 토큰 위변조 탐지가 된다.
    public Jws<Claims> getDecodeToken(final String jwt) {
        log.info("토큰 해독중 . . .");
        Jws<Claims> claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        return claims;
    }

}

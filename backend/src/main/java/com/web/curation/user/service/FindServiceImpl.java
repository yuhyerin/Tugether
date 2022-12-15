package com.web.curation.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.curation.common.ResponseCode;
import com.web.curation.entity.User;
import com.web.curation.exception.MailSendException;
import com.web.curation.user.dto.FindPasswordDto;
import com.web.curation.user.repo.UserRepo;
import com.web.curation.util.CipherUtil;
import com.web.curation.util.RandomNumberUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FindServiceImpl implements FindService {

	private final UserRepo userRepo;
	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Override
	public String sendCertificationNumberMail(String email) {

		// 인증번호 생성
		String certificationNumber = RandomNumberUtil.createRandomNumber();

		// 메일 내용 생성
		StringBuilder contents = new StringBuilder();
		contents.append("<h3>요청하신 이메일 ");
		contents.append(email);
		contents.append("로 회원가입을 요청하셨습니다.</h3>");
		contents.append("<p> [인증번호] : ");
		contents.append("<h3>" + certificationNumber + "</h3>");
		contents.append("를 입력해주세요.</p>");

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setSubject("[튜게더] 회원가입 인증 요청");
			mimeMessage.setContent(contents.toString(), "text/html;charset=euc-kr");
			mimeMessage.setFrom(fromEmail);
			mimeMessage.setRecipients(Message.RecipientType.TO, email);

			// 메일 전송
			javaMailSender.send(mimeMessage);

		} catch (MessagingException e) {
			/**
			 * MessagingException은 RuntimeException을 상속받지 않는 CheckedException 이다. 따라서 반드시
			 * try catch로 처리를 해주어야 한다.
			 */
			throw new MailSendException(ResponseCode.MAIL_SEND_FAIL);
		}
		/**
		 * 하지만 AuthenticationFailedException 는 RuntimeException을 상속받는 UnCheckedException
		 * 이므로 따로 처리를 해주지 않아도 된다.
		 */
		return certificationNumber;
	}

	@Override
	public FindPasswordDto findPassword(String email) {

		Optional<User> findUser = userRepo.findUserByEmail(email);
		if (findUser.isPresent()) {
			// 임시비밀번호 발송
			String tempPassword = sendTempPasswordMail(email);

			// password 초기화
			User user = findUser.get();
//			user.setPassword(EncryptUtil.encrypt(
//					tempPassword.getBytes(),
//					EncryptUtil.KEY));
//			user.setTemp(true);
			userRepo.save(user);

		} else {
			log.error("해당 이메일의 회원은 존재하지 않습니다");
		}
		return FindPasswordDto.builder().email(email).build();
	}

	@Override
	public String sendTempPasswordMail(String email) {

		// 임시 비밀번호
		String tempPassword = RandomNumberUtil.createRandomNumber();

		// 메일 내용 생성
		StringBuilder contents = new StringBuilder();
		contents.append("<h3>비밀번호 찾기를 요청하셨습니다.</h3>");
		contents.append("<p> [임시 비밀번호] : ");
		contents.append("<h3>" + tempPassword + "</h3>");
		contents.append("를 입력해주세요.</p>");

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setSubject("[튜게더] 임시 비밀번호 발급 ");
			mimeMessage.setContent(contents.toString(), "text/html;charset=euc-kr");
			mimeMessage.setFrom(fromEmail);
			mimeMessage.setRecipients(Message.RecipientType.TO, email);

			// 메일 전송
			javaMailSender.send(mimeMessage);

		} catch (MessagingException e) {
			/**
			 * MessagingException은 RuntimeException을 상속받지 않는 CheckedException 이다. 따라서 반드시
			 * try catch로 처리를 해주어야 한다.
			 */
			throw new MailSendException(ResponseCode.MAIL_SEND_FAIL);
		}

		return tempPassword;
	}

	@Override
	public ResponseEntity<Map<String, Object>> changePW(User u) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			userRepo.save(u);
			resultMap.put("status", true);
			resultMap.put("data", "success");
			resultMap.put("object", u);
			status = HttpStatus.ACCEPTED;
		} catch (IllegalArgumentException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Override
	public User changePasswordByEmail(String email) {
		return userRepo.findUserByEmail(email).get();
	}

	@Override
	public boolean checkPW(String email, String password) {
		User u = userRepo.findUserByEmail(email).get();

//		return u.getPassword().equals(password);
		return true;
	}

}

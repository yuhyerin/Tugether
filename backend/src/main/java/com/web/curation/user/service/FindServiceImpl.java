package com.web.curation.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.web.curation.common.ResponseCode;
import com.web.curation.dto.BasicResponse;
import com.web.curation.entity.User;
import com.web.curation.exception.MailSendException;
import com.web.curation.user.repo.UserRepo;

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
	public String sendCertificationNumber(String email) {

		// 인증번호 생성
		String certificationNumber = createpw();
		
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

		} 
		catch (MessagingException e) {
			/** MessagingException은 RuntimeException을 상속받지 않는 CheckedException 이다.
			 * 따라서 반드시 try catch로 처리를 해주어야 한다. */
			throw new MailSendException(ResponseCode.MAIL_SEND_FAIL);
		}
		/**
		 * 하지만 AuthenticationFailedException 는 RuntimeException을 상속받는 UnCheckedException 이므로 
		 * 따로 처리를 해주지 않아도 된다. 
		 * */
		return certificationNumber;
	}

	@Override
	public ResponseEntity<Object> findUserByEmail(String email) throws MessagingException {

		ResponseEntity<Object> response = null;
		BasicResponse result = new BasicResponse();
		result.object = email;
		User u = userRepo.findUserByEmail(email);
		if (u != null) {
			sendMail(email);
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@Override
	public void sendMail(String email) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		String tempPW = createpw();

		String htmlMsg = "망두 password : <h3>" + tempPW + "</h3>";
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(htmlMsg, true); // Use this or above line.
//		mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
		helper.setTo(email);
		mimeMessage.setFrom("wjdgusgml997@gmail.com");
		mimeMessage.setSubject("망두네 임시 비밀번호");
		try {
			javaMailSender.send(mimeMessage);
			User u = userRepo.findUserByEmail(email);
			u.setPassword(tempPW);
			u.setTemp(true);
			userRepo.save(u);
		} catch (MailSendException e) {

		}
		// update
	}

	private String createpw() {
		StringBuilder key = null;
		Random rd = new Random();
		while (true) {
			key = new StringBuilder();
			for (int i = 0; i < 8; i++) {
				char temp = (char) (rd.nextInt(90) + 33);
				key.append(temp);
			}
			System.out.println(key.toString());
			StringTokenizer st = new StringTokenizer(key.toString(), "1234567890");
			if (st.countTokens() > 1) {
				System.out.println(st.toString() + ", " + st.countTokens());
				break;
			}
		}
		return key.toString(); // 8자리 랜덤한 숫자 생성
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
		return userRepo.findUserByEmail(email);
	}

	@Override
	public boolean checkPW(String email, String password) {
		User u = userRepo.findUserByEmail(email);

		return u.getPassword().equals(password);
	}

}

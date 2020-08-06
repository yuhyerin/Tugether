package com.web.curation.service.account;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.User;
import com.web.curation.repo.UserRepo;

@Service
public class FindServiceImpl implements FindService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JavaMailSender mailSender;
//	private static final String FROM_ADDRESS = "wjdgusgml997@gmail.com";
	
	@Override
	public String sendTest(String email) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		StringBuilder sb = new StringBuilder();
		String message = createpw();
		sb.append("<h3>망두네 이메일 검사 : ");
		sb.append(email);
		sb.append("로 회원가입을 시도한다.</h3>");
		sb.append("<p>망두네 인증번호 : ");
		sb.append("<h3>"+message+"</h3>");
		sb.append("로 인증번호를 입력해주세요.</p>");
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(sb.toString(), true); // Use this or above line.
		helper.setTo(email);
		mimeMessage.setFrom("wjdgusgml997@gmail.com");
		mimeMessage.setSubject("망두네 회원가입");
		try {
			mailSender.send(mimeMessage);
		} catch(MailSendException e) {
			e.printStackTrace();
			return null;
		}
		return message;
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
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		String tempPW = createpw();

		String htmlMsg = "망두 password : <h3>" + tempPW + "</h3>";
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(htmlMsg, true); // Use this or above line.
//		mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
		helper.setTo(email);
		mimeMessage.setFrom("wjdgusgml997@gmail.com");
		mimeMessage.setSubject("망두네 임시 비밀번호");
		try {
		mailSender.send(mimeMessage);
		User u = userRepo.findUserByEmail(email);
		u.setPassword(tempPW);
		u.setTemp(true);
		userRepo.save(u);
		} catch(MailSendException e) {
			
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
				System.out.println(st.toString()+", "+ st.countTokens());
				break;
			}
		}
		return key.toString(); // 8자리 랜덤한 숫자 생성
	}

	@Override
	   public ResponseEntity<Map<String,Object>> changePW(User u) {
	      Map<String, Object> resultMap = new HashMap<String, Object>();
	      HttpStatus status = null;
	      try {
	         userRepo.save(u);
	         resultMap.put("status",true);
	         resultMap.put("data","success");
	         resultMap.put("object",u);
	         status = HttpStatus.ACCEPTED;
	      } catch (IllegalArgumentException e) {
	         status = HttpStatus.INTERNAL_SERVER_ERROR;
	      }
	      return new ResponseEntity<Map<String,Object>>(resultMap, status);
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

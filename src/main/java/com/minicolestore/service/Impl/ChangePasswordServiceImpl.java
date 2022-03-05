package com.minicolestore.service.Impl;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.minicolestore.User;
import com.minicolestore.repository.UserRepository;
import com.minicolestore.service.ChangePasswordService;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	private JavaMailSender mailSender;

	private boolean passwordCheck(String email) throws Exception {

		Optional<User> userFind = userRepo.findByEmailIgnoreCase(email);

		if (!userFind.isPresent()) {

			throw new Exception("El email proporcionado no se encuentra registrado");

		}

		return true;

	}
	
	private User returnUser(String email) {
		User user = userRepo.findByEmailIgnoreCase(email).get();
		return user;
	}

	@Override
	public void changePassword(String email) throws Exception {

		if (passwordCheck(email)) {
			
			User user = returnUser(email);
			
			//send email to reset password
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			String mailSubject = "Team MiniColeStore";
			String mailContent = "<p> Apreciado <b>" + user.getNombre()+" "+user.getApellido()+"</b></p>";
			mailContent += "<p>para restaurar tu contraseña has click en el siguiente enlace:<p>";
			mailContent += "http://localhost:8080/changePasswordCheck?id=" + user.getId() + "&code=" + user.getCodigoVerificacion();
			
			helper.setFrom("duvanceliz@gmail.com", "Team MiniColeStore");
			helper.setTo(email);
			helper.setSubject(mailSubject);
			helper.setText(mailContent, true);
			
			mailSender.send(message);
		}

	}

}

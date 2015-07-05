package com.paymee.service.email;

import com.paymee.domain.identity.Identity;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailValidationService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailValidationService.class);

	@Value("${smtp.user:none}")
	private String smtpUser;

	@Value("${smtp.pass:none}")
	private String smtpPass;

	@Value("${smtp.host:email-smtp.us-east-1.amazonaws.com}")
	private String smtpHost;

	@Value("${paymee.server.name:paymee.co}")
	private String paymeeServerName;

	public void sendValidationEmail(Long id, Identity identity) {
		if (smtpUser.equals("none")) {
			LOG.warn("Email sending disabled - please set smtp.user / smtp.pass / smtp.host to enable emails. Validation token = "+identity.getValidationToken());
			return;
		}
		try {
			Email email = new SimpleEmail();
			email.setHostName(smtpHost);
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(smtpUser, smtpPass));
			email.setSSLOnConnect(true);
			email.setFrom("do-not-reply@paymee.co");
			email.setSubject("Paymee email identity verification");
			email.setMsg("Thank you for registering with paymee.  Your account validation code is: "+identity.getValidationToken()+"\n"
					+"Please click on this link to validate your paymee account details: https://"+paymeeServerName+"/rest/validation/"+id+"/identity/"+identity.getId()+"/verify?validationToken="+identity.getValidationToken());
					email.addTo(identity.getIdentifier());
			email.send();
		} catch (EmailException e) {
			throw new RuntimeException(e);
		}
	}

}

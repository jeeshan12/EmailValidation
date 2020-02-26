package email.automation.emailservice;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import email.automation.pojo.EmailContents;
import email.automation.pojo.InboxEmail;

/**
 * 
 * Class to handle Nada Email API's
 * 
 * @author Jeeshan
 *
 */
public class EmailService {
	private static final String NADA_EMAIL_DOMAIN = "@getnada.com";
	private static final String INBOX_MESSAGE_KEY_NAME = "msgs";
	private static final String EMAIL_ID_PARAM = "email-id";
	private static final String MESSAGE_ID_PARAM = "message-id";
	private static final String NADA_EMAIL_INBOX_API = "https://getnada.com/api/v1/inboxes/{email-id}";
	private static final String NADA_EMAIL_MESSAGE_API = "https://getnada.com/api/v1/messages/{message-id}";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final int EMAIL_CHARS_LENGTH = 12;

	private String emailId;

	/**
	 * Method to generate the random email
	 */
	private void generateEmailId() {
		this.emailId = RandomStringUtils.randomAlphanumeric(EMAIL_CHARS_LENGTH).toLowerCase().concat(NADA_EMAIL_DOMAIN);
	}

	public String getEmailId() {
		if (Objects.isNull(this.emailId)) {
			this.generateEmailId();

		}
		return this.emailId;
	}

	/**
	 * This method will reset the mail id
	 */
	public void reset() {
		this.emailId = null;
	}

	/**
	 * 
	 * method to get message inbox
	 * 
	 * @return {@link List}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnirestException
	 * 
	 * 
	 */
	public List<InboxEmail> getInbox()
			throws JsonParseException, JsonMappingException, IOException, JSONException, UnirestException {
		String msgs = Unirest.get(NADA_EMAIL_INBOX_API).routeParam(EMAIL_ID_PARAM, this.getEmailId()).asJson().getBody()
				.getObject().getJSONArray(INBOX_MESSAGE_KEY_NAME).toString();
		return MAPPER.readValue(msgs, new TypeReference<List<InboxEmail>>() {
		});
	}

	public EmailContents getMessageById(final String messageId)
			throws JsonParseException, JsonMappingException, IOException, UnirestException {
		String msgs = Unirest.get(NADA_EMAIL_MESSAGE_API).routeParam(MESSAGE_ID_PARAM, messageId).asJson().getBody()
				.getObject().toString();
		return MAPPER.readValue(msgs, EmailContents.class);
	}

	/**
	 * 
	 * Method to get Message with a subject
	 * 
	 * @param emailSubject
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws UnirestException
	 */
	public EmailContents getMessageWithSubject(final String emailSubject)
			throws JsonParseException, JsonMappingException, IOException, UnirestException {
		return this.getInbox().stream().filter(mail -> mail.getSubject().startsWith(emailSubject)).findFirst()
				.map(InboxEmail::getMessageId).map(t -> {
					try {
						return getMessageById(t);
					} catch (IOException | UnirestException e) {
						System.out.println("Execption occured :" + e);
					}
					return null;
				}).orElseThrow(IllegalArgumentException::new);
	}
}

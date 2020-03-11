package com.orvif.website3.service;

import com.orvif.website3.Entity.Newsletter;
import com.orvif.website3.Repository.NewsletterRepository;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.MediaType;
import java.util.List;

public class MailgunHandler {

	@Autowired
	private NewsletterRepository nr;

	/**
	 * Send an html mail to a list of users specified in parameter
	 *
	 * @param subject       Subject of the mail
	 * @param content       Html content of the mails
	 * @param mailAddresses Address mail array
	 */
	public static void sendHtmlMail(String subject, String content, String... mailAddresses) {
		for (String to : mailAddresses) {
			sendHtmlMail(subject, content, to);
		}
	}

	/**
	 * Send an Html mail to all subscribers
	 *
	 * @param subject the subject of the mail
	 * @param content the html content of the mail
	 *
	 */
	// WAS SET TO STATIC
	public void sendToNewsletterSubscribers(String subject, String content) {
		List<Newsletter> subscribers = nr.findAll();
		subscribers.forEach(newsletter -> sendHtmlMail(subject, content, newsletter.getMail()));
	}

	/**
	 * Send an html mail
	 *
	 * @param subject subject of the mail
	 * @param content html content of the mail
	 * @param to      recipient
	 */
	private static void sendHtmlMail(String subject, String content, String to) {
		String domainName = System.getProperty("mailgun_domain_name");
		String apiKey = System.getProperty("mailgun_api_key");
		String fromName = System.getProperty("mailgun_from_name");
		String fromMail = System.getProperty("mailgun_from_mail");

		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", apiKey));
		WebResource webResource =
				client.resource("https://api.mailgun.net/v3/" + domainName + "/messages");
		FormDataMultiPart formData = new FormDataMultiPart();
		formData.field("from", fromName + " <" + fromMail + ">");
		formData.field("to", to);
		formData.field("subject", subject);
		formData.field("html", content);
		webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, formData);
	}

}

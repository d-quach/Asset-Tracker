package asset.tracker.manager;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.NamingException;
import javax.xml.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Uses the JavaMail API to send emails.
 * 
 * @author Quack
 *
 */
public final class EmailService {

	// Stores credentials from the XML file.
	private ArrayList<String> keys = new ArrayList<>();

	/**
	 * A constructor that retrieves the login information.
	 * 
	 * @throws ParserConfigurationException thrown if the parser cannot be
	 *                                      configured.
	 * @throws SAXException                 thrown if the XML file cannot be parsed.
	 * @throws IOException                  thrown if there is an input/output
	 *                                      error.
	 */
	public EmailService() throws ParserConfigurationException, SAXException, IOException {

		this.keys = readXML();
	}

	/**
	 * A utility class for sending email's.
	 * 
	 * @param to   the recipient.
	 * @param body the contents of the email.
	 * @throws MessagingException thrown when the message fails to send.
	 * @throws NamingException    thrown when the message cannot be addressed.
	 */
	public void sendMail(String to, String body) throws MessagingException, NamingException {

		String user = this.keys.get(0);
		String pass = this.keys.get(1);

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.port", 465);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		Message message = new MimeMessage(session);
		message.setSubject("Your Password Has Been Reset");
		message.setText(body);

		Address fromAddress = new InternetAddress(user);
		Address toAddress = new InternetAddress(to);
		message.setFrom(fromAddress);
		message.setRecipient(Message.RecipientType.TO, toAddress);

		Transport transport = session.getTransport();
		transport.connect(user, pass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

	}

	/**
	 * A utility method for parsing an XML file to retrieve specific items.
	 * 
	 * @return the ArrayList that contains login credentials for the mailing
	 *         account.
	 * @throws ParserConfigurationException thrown if the parser cannot be
	 *                                      configured.
	 * @throws SAXException                 thrown if the XML file cannot be parsed.
	 * @throws IOException                  thrown if there is an input/output
	 *                                      error.
	 */
	private static ArrayList<String> readXML() throws ParserConfigurationException, SAXException, IOException {

		final String PATH = "res/config.xml";
		ArrayList<String> keys = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(PATH));
		doc.getDocumentElement().normalize();
		NodeList nl = doc.getElementsByTagName("env-entry");

		for (int i = 0; i < nl.getLength(); i++) {

			Node node = nl.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element item = (Element) node;

				if (item.getElementsByTagName("env-entry-name").item(0).getTextContent() != null) {
					keys.add(item.getElementsByTagName("env-entry-value").item(0).getTextContent());
				}

			}

		}
		return keys;
	}

}
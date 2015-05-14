package com.ep.screenshot;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EPScreenshot {

	public static void main(String a[]) throws InterruptedException {

		boolean sendMail = true;
		
		final String username = System.getProperty("FromEmailUserName");
		final String password = System.getProperty("FromEmailPassWord");
		final String fromEmailUserName = System.getProperty("FromEmailUserName");
		final String fromEmailPWD = System.getProperty("FromEmailPassWord");
		
		Properties properties = new Properties();		
		properties.put("mail.smtp.host", System.getProperty("mail.smtp.host"));
		properties.put("mail.smtp.socketFactory.port", System.getProperty("mail.smtp.socketFactory.port"));
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", System.getProperty("mail.smtp.port"));

		
		WebDriver driver = new FirefoxDriver();
		System.out.println("in method=============");
		System.out.println(driver);
		driver.get(System.getProperty("DashboardURL"));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		HttpURLConnection connection = null;
		try {
			
			URL dashBoardURL = new URL(System.getProperty("DashboardURL"));
			connection = (HttpURLConnection) dashBoardURL.openConnection();
			connection.setRequestMethod("HEAD");
			System.out.println("connection : " + connection);
			int code = connection.getResponseCode();
			System.out.println("HTTP Status Code : " + code);
			try {
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile,
						new File(System.getProperty("user.dir")
								+ "\\dashBoardImage.png"));
				System.out.println("Screenshot is captured and stored in your "
						+ System.getProperty("user.dir")
						+ "\\dashBoardImage.png");
			} catch (Exception e) {
				System.out.println("Error in loading the Google page");
			}
		} catch (MalformedURLException mfe) {
			System.out.println("=========================MFU");
			mfe.printStackTrace();
			
			sendMail = false;
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			try {
				FileUtils.copyFile(scrFile,
						new File(System.getProperty("user.dir")
								+ "\\dashBoardImage.png"));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// driver.quit();
			Session session = Session.getDefaultInstance(properties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(fromEmailUserName,
									fromEmailPWD);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(System.getProperty("fromEmail")));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(System.getProperty("FailedRecipientList")));
				message.setSubject("EP Report Dashboard Execution Status!!!");

				// This mail has 2 part, the BODY and the embedded image
				MimeMultipart multipart = new MimeMultipart("related");
				// first part (the html)
				BodyPart messageBodyPart = new MimeBodyPart();
				String htmlText = "<H1>EP Dash Board Results</H1><img src=\"cid:image\"><br>";
				messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
				// add it
				multipart.addBodyPart(messageBodyPart);
				// second part (the image)
				messageBodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(
						System.getProperty("user.dir") + "\\dashBoardImage.png");
				System.out.println("============="
						+ System.getProperty("user.dir")
						+ "\\dashBoardImage.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image>");
				// add image to the multipart
				multipart.addBodyPart(messageBodyPart);
				// put everything together
				message.setContent(multipart);
				// Send the actual HTML message, as big as you like
				Transport.send(message);
				System.out.println("Mail sent successfully!!!"
						+ message.getAllRecipients());

			} catch (MessagingException e1) {
				throw new RuntimeException(e1);
			}
		} catch (IOException e) {
			System.out.println("=========================IOE");
			e.printStackTrace();
			sendMail = false;
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			try {
				FileUtils.copyFile(scrFile,
						new File(System.getProperty("user.dir")
								+ "\\dashBoardImage.png"));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// driver.quit();
			Session session = Session.getDefaultInstance(properties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(fromEmailUserName,
									fromEmailPWD);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(System.getProperty("fromEMail")));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(System.getProperty("FailedRecipientList")));
				message.setSubject("EP Report Dashboard Execution Status!!!");

				// This mail has 2 part, the BODY and the embedded image
				MimeMultipart multipart = new MimeMultipart("related");
				// first part (the html)
				BodyPart messageBodyPart = new MimeBodyPart();
				String htmlText = "<H1>EP Dash Board Results</H1><img src=\"cid:image\"><br>";
				messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
				// add it
				multipart.addBodyPart(messageBodyPart);
				// second part (the image)
				messageBodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(
						System.getProperty("user.dir") + "\\dashBoardImage.png");
				System.out.println("============="
						+ System.getProperty("user.dir")
						+ "\\dashBoardImage.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image>");
				// add image to the multipart
				multipart.addBodyPart(messageBodyPart);
				// put everything together
				message.setContent(multipart);
				// Send the actual HTML message, as big as you like
				Transport.send(message);
				System.out.println("Mail sent successfully!!!"
						+ message.getAllRecipients());

			} catch (MessagingException e1) {
				throw new RuntimeException(e1);
			}

		} finally {
			if (connection != null) {
				connection.disconnect();
				driver.quit();
				// return;
			}
		}

		if (sendMail) {
			Session session = Session.getDefaultInstance(properties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(System.getProperty("fromEmail")));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(System.getProperty("PassedRecipientList")));
				message.setSubject("EP Report Dashboard Execution Status!!!");

				// This mail has 2 part, the BODY and the embedded image
				MimeMultipart multipart = new MimeMultipart("related");
				// first part (the html)
				BodyPart messageBodyPart = new MimeBodyPart();
				String htmlText = "<H1>EP Dash Board Results</H1><img src=\"cid:image\"><br>";
				messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
				// add it
				multipart.addBodyPart(messageBodyPart);
				// second part (the image)
				messageBodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(
						System.getProperty("user.dir") + "\\dashBoardImage.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image>");
				// add image to the multipart
				multipart.addBodyPart(messageBodyPart);
				// put everything together
				message.setContent(multipart);
				// Send the actual HTML message, as big as you like
				Transport.send(message);
				System.out.println("Mail sent successfully!!!");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		
		
	}

}
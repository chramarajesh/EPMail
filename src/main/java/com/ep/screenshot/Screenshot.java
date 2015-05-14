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

public class Screenshot {

	public static void main(String a[]) throws InterruptedException {

		/*boolean sendMail = true;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		final String username = System.getProperty("UserName");
		final String password = "kingraj@123";

		WebDriver driver = new FirefoxDriver();
		System.out.println("in method=============");
		System.out.println(driver);
		driver.get("http://20.20.20.25:8080/ReportServer/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		HttpURLConnection connection = null;
		try {
			URL dashBoardURL = new URL("http://20.20.20.25:8080/ReportServer/");
			connection = (HttpURLConnection) dashBoardURL.openConnection();
			connection.setRequestMethod("HEAD");
			System.out.println("connection : " + connection);
			int code = connection.getResponseCode();
			System.out.println("HTTP Status Code : " + code);
			// You can determine on HTTP return code received. 200 is success.
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
		} catch (MalformedURLException e) {
			System.out.println("=========================MFU");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("=========================IOE");
			e.printStackTrace();
			Properties props1 = new Properties();
			props1.put("mail.smtp.host", "smtp.gmail.com");
			props1.put("mail.smtp.socketFactory.port", "465");
			props1.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props1.put("mail.smtp.auth", "true");
			props1.put("mail.smtp.port", "465");

			final String username1 = "chramarajesh";
			final String password1 = "kingraj@123";
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
			Session session = Session.getDefaultInstance(props1,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username1,
									password1);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("chramarajesh@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("rchittavarjula@ep.com"));
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
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("chramarajesh@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("rchittavarjula@ep.com,nvishtamsetty@ep.com"));
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
		}*/
		
		System.out.println("============================"+System.getProperty("FromEmailUserName"));
	}

}
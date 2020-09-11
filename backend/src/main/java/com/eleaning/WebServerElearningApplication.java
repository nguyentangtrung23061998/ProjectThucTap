package com.eleaning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.eleaning.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WebServerElearningApplication extends SpringBootServletInitializer {

//	private static final String ACCESS_TOKEN = "SGMzFvLPi6kAAAAAAAAAAXoSeUX2ecPDfnZKAN_t5VMTrVP_--yy9DZxIMLPe_Y2";

	public static void main(String[] args) {
//		try {
//			DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
//			DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
//			FullAccount account;
//			account = client.users().getCurrentAccount();
//			System.out.println(account.getName().getDisplayName());
//
//			SpringApplication.run(WebServerElearningApplication.class, args);
//		} catch (DbxApiException e) {
//			e.printStackTrace();
//		} catch (DbxException e) {
//			e.printStackTrace();
//		}
		SpringApplication.run(WebServerElearningApplication.class, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.web.servlet.support.SpringBootServletInitializer#
	 * configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(WebServerElearningApplication.class);
	}
}

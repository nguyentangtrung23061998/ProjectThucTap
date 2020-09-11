package com.eleaning.util;

import java.io.File;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class Constant {
	Properties properties = new Properties();
	
	//public static final File file = ResourceUtils.getFile("classpath:static");
	
	public static final String CURRENCY_DEFAULT 			= "USD";
	
	public static long ACTIVE_ACCOUNT_KEY_TIME_LIMIT_CLIENT = 10 * 60000; /* 10 minutes */
	
	public static final String GUEST_USER 					= "Guest";
	
	public static final int TIME_OUT_CODE 					= 600;
	public static final int LENGHT_OF_TOKEN 			= 128;
	public static final int LENGHT_OF_PASSWORD 		= 5;
	
	public static String[] extensionImg = {"jpg","png","jpeg","gif","bmp"};
	public static String[] extensionVideo  = {"mp4","mwv","avi","mov","ts"};
	public static String[] extensionAudio = {"mp3" ,"mwa","wav","alac","aac"};
	public static String[] extensionDocument = {"doc" ,"xlxs"};

	public static final String SITE_URL_KEY 						= "cybinium.url";
	public static final String EMAIL_SENDER_KEY 			= "spring.mail.sender";
	public static final String EMAIL_NO_REPLAY_KEY 		= "spring.mail.no.reply";
	public static final String EMAIL_INFO_KEY 					= "spring.mail.info";
	
	public static final int SERVICE_MIN_MINUTE 				= 5;
	//public static final int MAXIMUM_END_HOUR_DAY 			  						= 23*60; /* 23 hours */
//	public static final int MINIMUM_START_HOUR_DAY 		  							= 8*60; /* 8 hours */
	
	public static final int SCHEDULE_UPDATE_BOOKING_SUCCESS_STATUS  	= 2*60;

	
	public static final String TIME_SLOT_TYPE_WORKTIME 		= "WORKTIME";
	public static final String TIME_SLOT_TYPE_WALKIN 			= "WALKIN";
	public static final String TIME_SLOT_TYPE_AVAILABLE 		= "AVAILABLE";
	public static final String TIME_SLOT_TYPE_CLOSEDTIME 	= "CLOSEDTIME";
	public static final String TIME_SLOT_TYPE_UNBOOKABLE 	= "UNBOOKABLE";
	
	public static final String UPLOAD_ROOT = "/WEB-INF";
	public static final String UPLOAD_IMG = "/upload/images";
	public static final String UPLOAD_VIDEO = "/upload/videos";
	public static final String UPLOAD_AUDIO = "/upload/audios";
	public static final String UPLOAD_DOCUMENT = "/upload/documents";
	
	public static final String[] role = {"ROLE_ADMIN","ROLE_TEACHER","ROLE_STUDENT"};
}

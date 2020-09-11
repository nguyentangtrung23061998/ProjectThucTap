package com.eleaning.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

public class Util {
	
	public static String getRealIpAddr() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		return getClientIpAddress(request);

	}

	private static final String[] IP_HEADER_CANDIDATES = { "X-FORWARDED-FOR", "X-Forwarded-For", "Proxy-Client-IP",
			"WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public static String getClientIpAddress(HttpServletRequest request) {

		for (String header : IP_HEADER_CANDIDATES) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {

				String[] tmp = ip.split(":");
				if (tmp.length == 2) {
					ip = tmp[0];
				}
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	/*
	 * public Util() {
	 * 
	 * }
	 * 
	 * public static void readJsonFile() {
	 * 
	 * JSONParser parser = new JSONParser(); try {
	 * 
	 * 
	 * ClassLoader classLoader = Util.class.getClass().getClassLoader();
	 * System.out.println("classLoader="+classLoader.toString());
	 * 
	 * Object obj = parser.parse(new FileReader("login_config.json"));
	 * 
	 * JSONObject jsonObject = (JSONObject) obj;
	 * 
	 * String name = (String) jsonObject.get("AUTH_TIMEOUT"); String author =
	 * (String) jsonObject.get("CAPTCHA_TIMEOUT"); JSONArray companyList =
	 * (JSONArray) jsonObject.get("BLOCK_IP_TIMEOUT");
	 * 
	 * System.out.println("Name: " + name); System.out.println("Author: " + author);
	 * System.out.println("\nCompany List:"); Iterator<String> iterator =
	 * companyList.iterator(); while (iterator.hasNext()) {
	 * System.out.println(iterator.next()); } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	/*
	 * 
	 */
	public static String tokenGenerating() {

		/*
		 * SecureRandom random = new SecureRandom(); byte bytes[] = new
		 * byte[Constant.LENGHT_OF_TOKEN]; // byte seed[] = random.generateSeed(32);
		 * random.nextBytes(bytes);
		 * 
		 * String hexString = Hex.encodeHexString(bytes); return hexString +
		 * DateUtil.currentTime();
		 */

		StringBuffer data = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		String szBuffer = "";
		int nPos = 0;

		while (szBuffer.length() < Constant.LENGHT_OF_TOKEN) {
			nPos = getRandom(0, data.length());
			szBuffer += data.charAt(nPos);
		}

		return szBuffer + DateUtil.currentTime();

	}

	/*
	 * 
	 */
	public static String encodePassword(String passwordPlainText) {

		/*
		 * , String stored_hash return BCrypt.checkpw(password_plaintext, stored_hash);
		 */
		if (passwordPlainText != null) {

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = bCryptPasswordEncoder.encode(passwordPlainText);
			return encodedPassword;

		}
		return null;

	}

	public static int getSession(Object session) {
		return Integer.parseInt(session != null ? session.toString() : "0");
	}

	/**************************************************/
	public static String generateLocationKey(Integer area_ID, Integer rack_ID) {
		return area_ID.toString() + "+" + (rack_ID != null ? rack_ID.toString() : "0");
	}

	/*
	 * 
	 */
	public static String generatePassword() {

		return generatePassword(Constant.LENGHT_OF_PASSWORD, true, true, true, true);

	}

	public static boolean checkPassword(String passwordPlainText, String encodedPassword) {

		/*
		 * , String stored_hash return BCrypt.checkpw(password_plaintext, stored_hash);
		 */
		if (encodedPassword != null) {

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			return bCryptPasswordEncoder.matches(passwordPlainText, encodedPassword);

		}
		return false;

	}

	/*
	 * min: inclusive max: exclusive ex: getRandom(0,3) => 0,1,2
	 */
	private static int getRandom(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}

	/*
	 * 
	 */
	private static String generatePassword(int nLength, boolean bLowercase, boolean bUppercase, boolean bNumbers,
			boolean bSymbols) {

		StringBuffer szLower = new StringBuffer("abcdefghijklmnopqrstuvwxyz");
		StringBuffer szUpper = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer szNumber = new StringBuffer("0123456789");
		StringBuffer szSymbols = new StringBuffer("!#$%&()*+,-./:;<=>?@[]^_{}~");

		String szBuffer = "";
		int nPos = 0;

		while (szBuffer.length() < Constant.LENGHT_OF_PASSWORD) {

			if (bLowercase && szBuffer.length() < Constant.LENGHT_OF_PASSWORD) {

				nPos = getRandom(0, szLower.length());
				szBuffer += szLower.substring(nPos, nPos + 1);
				szLower = szLower.replace(nPos, nPos + 1, "");
			}

			if (bUppercase && szBuffer.length() < Constant.LENGHT_OF_PASSWORD) {

				nPos = getRandom(0, szUpper.length());
				szBuffer += szUpper.substring(nPos, nPos + 1);
				szUpper = szUpper.replace(nPos, nPos + 1, "");

			}

			if (bNumbers && szBuffer.length() < Constant.LENGHT_OF_PASSWORD) {

				nPos = getRandom(0, szNumber.length());
				szBuffer += szNumber.substring(nPos, nPos + 1);
				szNumber = szNumber.replace(nPos, nPos + 1, "");
			}

			if (bSymbols && szBuffer.length() < Constant.LENGHT_OF_PASSWORD) {

				nPos = getRandom(0, szSymbols.length());
				szBuffer += szSymbols.substring(nPos, nPos + 1);
				szSymbols = szSymbols.replace(nPos, nPos + 1, "");
			}
		}

		String result = "";
		StringBuffer buf = new StringBuffer(szBuffer);

		while (buf.length() > 0) {

			nPos = getRandom(0, buf.length());
			result += buf.substring(nPos, nPos + 1);
			buf = buf.replace(nPos, nPos + 1, "");
		}

		return result;
	}

	/*
	 * for reference
	 */
	/*
	 * @SuppressWarnings("unchecked") public static <T> T toObject(String
	 * jsonSetting, Class<T> classOfT) { if (jsonSetting != null && classOfT !=
	 * null) { try { return (T) fromJson(jsonSetting, classOfT.getClass()); }
	 * catch(JsonSyntaxException ex) { System.out.println("error" +
	 * ex.getMessage()); } } return null; }
	 */
	/*
	 * 
	 */
	public static String isNull(String value, String replace) {
		return value != null ? value : replace;
	}

	/*
	 * 
	 */
	public static String createLocation(String location1, String location2) {
		if (location1 != null && location2 != null) {
			return location1.trim() + "-" + location2.trim();
		} else if (location1 != null) {
			return location1.trim();
		} else if (location2 != null) {
			return location2.trim();
		}
		return null;

	}

	public static Integer toMinutesUnit(Integer hour, Integer minute) {
		int minutes = 0;
		if (hour != null) {
			minutes += hour * 60;
		}
		if (minute != null) {
			minutes += minute;
		}
		return new Integer(minutes);
	}

	/*
	 * 
	 */
	public static String trim(String val) {
		if (val != null && !val.trim().equals("")) {
			return val.trim();
		}
		return null;
	}

	/*
	 * 
	 */
	public static String minutesToString(Integer minutes) {
		return minutes != null ? String.format("%02d:%02d", minutes / 60, minutes % 60) : "";
	}

	/*
	 * 
	 */
	public static String minutesToString(Integer minutes, boolean isTimesAMPM) {

		if (minutes != null) {

			if (isTimesAMPM) {

				int h = minutes / 60;
				int m = minutes % 60;

				if (h >= 13) {
					return String.format("%02d:%02d PM", h % 12, m);
				}

				return String.format("%02d:%02d AM", h, m);
			}
			return minutesToString(minutes);
		}
		return "";
	}

	public static boolean upload(MultipartFile file) throws IOException {
		URL url = Util.class
                .getClassLoader().getResource("static");
		String rootPath = String.valueOf(url);
		
		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		
		String orginalFile = file.getOriginalFilename();
		String extension = orginalFile.substring(orginalFile.lastIndexOf(".") + 1);
		
		String uploadRootPath = "";
		System.out.println("root path: " + rootPath);
		
		for (String extensionVideo : Constant.extensionVideo) {
			if (extension.equals(extensionVideo)) {
				uploadRootPath = rootPath + Constant.UPLOAD_VIDEO;
			}
		}
		for (String extensionImg : Constant.extensionImg) {
			if (extension.equals(extensionImg)) {
				uploadRootPath = rootPath + Constant.UPLOAD_IMG;
			}
		}
		for (String extensionAudio : Constant.extensionAudio) {
			if (extension.equals(extensionAudio)) {
				uploadRootPath = rootPath + Constant.UPLOAD_AUDIO;
			}
		}
		for (String extensionDocument : Constant.extensionDocument) {
			if (extension.equals(extensionDocument)) {
				uploadRootPath = rootPath + Constant.UPLOAD_DOCUMENT;
			}
		}
		
		System.out.println("path upload: " + uploadRootPath);
		
		uploadRootPath = uploadRootPath.replace(uploadRootPath,uploadRootPath.substring(6));
		File f2 = new File(uploadRootPath);
		if (!f2.exists()) {
			if (f2.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
		}
		String name = file.getOriginalFilename();
		if (name != null && name.length() > 0) {
			try {
				String fSV = uploadRootPath + "//" + name;
				System.out.println("fsv: " + fSV);
				File serverFile = new File(fSV);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());
				stream.flush();
				stream.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public static boolean uploadV2(MultipartFile file,String uploadRoot) throws IOException {
		String uploadRootPath = "";
		String orginalFile = file.getOriginalFilename();
		String extension = orginalFile.substring(orginalFile.lastIndexOf(".") + 1);
		
		for (String extensionVideo : Constant.extensionVideo) {
			if (extension.equals(extensionVideo)) {
				uploadRootPath = uploadRoot + Constant.UPLOAD_VIDEO;
			}
		}
		for (String extensionImg : Constant.extensionImg) {
			if (extension.equals(extensionImg)) {
				System.out.println("OK send file success");
				uploadRootPath = uploadRoot + Constant.UPLOAD_IMG;
			}
		}
		for (String extensionAudio : Constant.extensionAudio) {
			if (extension.equals(extensionAudio)) {
				uploadRootPath = uploadRoot + Constant.UPLOAD_AUDIO;
			}
		}
		for (String extensionDocument : Constant.extensionDocument) {
			if (extension.equals(extensionDocument)) {
				uploadRootPath = uploadRoot + Constant.UPLOAD_DOCUMENT;
			}
		}
		
		File fileUploadRoot = new File(uploadRootPath);
		if(!fileUploadRoot.exists()) {
			fileUploadRoot.mkdirs();
		}
		
		if(orginalFile != null && orginalFile.length()>0) {
			try {
				String linkFile =fileUploadRoot  + File.separator + orginalFile;
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(linkFile));
				stream.write(file.getBytes());
				stream.flush();
				stream.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[2048];
		try {
			while(!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer,0,count); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("outputStream.toByteArray(): " +outputStream.toByteArray());
		return outputStream.toByteArray();
	}
		
}

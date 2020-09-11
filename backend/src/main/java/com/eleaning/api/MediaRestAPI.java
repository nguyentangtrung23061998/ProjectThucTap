package com.eleaning.api;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eleaning.bean.MediaBean;
import com.eleaning.bean.ResponseBean;
import com.eleaning.entity.MediaEntity;
import com.eleaning.repository.IMediaRepository;
import com.eleaning.service.IMediaService;
import com.eleaning.util.Constant;
import com.eleaning.util.Util;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/medias")
public class MediaRestAPI {

	private static final Logger logger = LoggerFactory.getLogger(MediaRestAPI.class);

	@Autowired
	private IMediaService mediaService;

	@Autowired
	private IMediaRepository mediaRepository;

	@Autowired
	private ServletContext context;

	@PostMapping("/upload")
	public ResponseEntity<ResponseBean> uploadImage(@RequestParam("file") MultipartFile file) {
		ResponseBean responseBean = new ResponseBean();
		try {
//			String orginalFile = file.getOriginalFilename();
//			String extension = orginalFile.substring(orginalFile.lastIndexOf(".") + 1);
//			MediaEntity media = new MediaEntity(Calendar.getInstance().getTimeInMillis(), file.getOriginalFilename(), file.getContentType(),
//					Util.compressBytes(file.getBytes()));
			MediaEntity media = new MediaEntity(Calendar.getInstance().getTimeInMillis(), file.getOriginalFilename(),
					file.getContentType());
			MediaEntity mediaEntity = mediaRepository.save(media);
			responseBean.setData(mediaEntity);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = { "/name/{mediaName}" })
	public ResponseEntity<ResponseBean> getMedia(@PathVariable("mediaName") String mediaName) {
		ResponseBean responseBean = new ResponseBean();
		try {
			MediaEntity mediaEntity = mediaService.findByName(mediaName);
			responseBean.setData(mediaEntity);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value="/name/test/{mediaName}")
	public ResponseEntity<ResponseBean> getMediaV2(@PathVariable("mediaName") String mediaName) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String pathRoot = context.getRealPath(Constant.UPLOAD_ROOT);
			MediaEntity media = mediaService.findByName(mediaName);
			String rootPath = "";

			for (String extensionImg : Constant.extensionImg) {
				if (media.getType().equals(extensionImg)) {
					rootPath = pathRoot + Constant.UPLOAD_IMG + File.separator + mediaName
							+ Character.toString((char) 46) + media.getType();
				}
			}

			for (String extensionVideo : Constant.extensionVideo) {
				if (media.getType().equals(extensionVideo)) {
					rootPath = pathRoot + Constant.UPLOAD_VIDEO + File.separator + mediaName
							+ Character.toString((char) 46) + media.getType();
				}
			}

			for (String extensionAudio : Constant.extensionAudio) {
				if (media.getType().equals(extensionAudio)) {
					rootPath = pathRoot + Constant.UPLOAD_AUDIO + File.separator + mediaName
							+ Character.toString((char) 46) + media.getType();
				}
			}

			for (String extensionDument : Constant.extensionDocument) {
				if (media.getType().equals(extensionDument)) {
					rootPath = pathRoot + Constant.UPLOAD_DOCUMENT + File.separator + mediaName
							+ Character.toString((char) 46) + media.getType();
				}
			}

			File file = new File(rootPath);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			System.out.println("file content: " + fileContent);
			MediaBean mediaBean = new MediaBean(Calendar.getInstance().getTimeInMillis(), mediaName, media.getType(), Util.compressBytes(fileContent));
			mediaBean.setMediaByte(Util.decompressBytes(mediaBean.getMediaByte()));
			responseBean.setData(mediaBean);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
	}
}

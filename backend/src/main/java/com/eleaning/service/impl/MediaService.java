package com.eleaning.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleaning.entity.MediaEntity;
import com.eleaning.repository.IMediaRepository;
import com.eleaning.service.IMediaService;
import com.eleaning.util.Util;

@Service
public class MediaService implements IMediaService {

	@Autowired
	IMediaRepository mediaRepository;

	@Override
	public MediaEntity findByName(String name) {
		try {
			Optional<MediaEntity> mediaData = mediaRepository.findByName(name);
			if (mediaData.isPresent()) {
				return mediaData.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MediaEntity save(MediaEntity media) {
		try {
			mediaRepository.save(media);
			return media;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

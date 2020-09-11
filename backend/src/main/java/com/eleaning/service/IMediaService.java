package com.eleaning.service;

import com.eleaning.entity.MediaEntity;

public interface IMediaService {
	public MediaEntity findByName(String name);
	public MediaEntity save(MediaEntity media);
}

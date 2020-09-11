package com.eleaning.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eleaning.bean.ResponseBean;
import com.eleaning.entity.RoleEntity;
import com.eleaning.service.IRoleService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles/")
public class RoleRestAPI {
	@Autowired
	private IRoleService roleService;
	
	@GetMapping()
	public ResponseEntity<ResponseBean> getRoles(){
		List<RoleEntity> roles = roleService.getRoles();
		ResponseBean responseBean = new ResponseBean();
		responseBean.setData(roles);
		responseBean.setSuccess();
		return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
	}
}

package com.yundian.basic.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundian.basic.dao.ISysConfigDao;
import com.yundian.basic.domain.SysConfigModel;
import com.yundian.basic.service.SysConfigService;

/**
* 
*/
@Service
public class SysConfigServiceImpl implements SysConfigService {
	@Autowired
	ISysConfigDao service;

 

	@Override
	public List<SysConfigModel> getList() {
		return service.getList();
	}

	@Override
	public SysConfigModel getModel(int id) {
		return service.getModel(id);
	}
	
	@Override
	public SysConfigModel getModel(String nid) {
		return service.getModelByNid(nid);
	}

 
 
}

package com.jrwp.appr.service;

import com.jrwp.appr.entity.UserAppraise;

public interface UserAppraiseService {

	void saveAppr(UserAppraise userAppraise, Long appointmeId);
}

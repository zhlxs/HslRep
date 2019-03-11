package com.jrwp.core.help;

import org.apache.commons.lang3.StringUtils;

import com.jrwp.core.utils.JacksonUtil;

public class QueryInfoHelper {
	public static QueryInfo forWebJson(String queryinfo) {
		if (!StringUtils.isEmpty(queryinfo)) {
			QueryInfo queryInfo = JacksonUtil.readValue(queryinfo,
					QueryInfo.class);
			if (queryInfo != null) {
				return queryInfo;
			}
		}
		return new QueryInfo();
	}
}

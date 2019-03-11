package com.jrwp.wx.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.entity.CallNumParm;
import com.jrwp.wx.entity.PmVisitor;
import com.jrwp.wx.until.PmListUntil;
import com.jrwp.wx.until.Utils;

@Description(value = "预约信息查询接口", state = false)
@Controller
@RequestMapping("getSquenceAction")
public class PmAction {
	private final Logger logger=Logger.getLogger(PmAction.class);
	@RequestMapping(value = "getVistor", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getVistor(HttpServletRequest request, String parmString) {
        //parmString="{\"apiauthtoken\":\"DB848A594A30BB02BD80E20240D479EB\",\"deptid\":47,\"pageSize\":20,\"pageNum\":1}";
		
        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
        DoResult result = new DoResult();
        CallNumParm callNumParm = null;
        if (parmString != null) {
            callNumParm = JacksonUtil.readValue(parmString, CallNumParm.class);
            callNumParm.setDeptid(Integer.valueOf(Utils.getParamsByKey("deptid")));
        } else {
            result.setStateMsg("参数字符串是空");
            result.setStateType(DoResultType.fail);
            return JacksonUtil.toJson(result);
        }
        if (callNumParm.getApiauthtoken() != null && callNumParm.getApiauthtoken().equals(code)) {
            try {
                List<PmVisitor> taget = new ArrayList<>();
                List<PmVisitor> all = new ArrayList<>();
                //先取到 List
                List<PmVisitor> sequenceHlper = com.jrwp.wx.until.PmSquenceHelper.getInstance().dateChange(1, null);
                all = PmListUntil.listCopy(sequenceHlper);
                List<PmVisitor> gone = new ArrayList<>();
                List<PmVisitor> called = new ArrayList<>();
                List<PmVisitor> recalled = new ArrayList<>();
                List<PmVisitor> wait = new ArrayList<>();
                for (PmVisitor visitor : all) {
                    if (visitor.getDeptid().longValue() == callNumParm.getDeptid().longValue()) {
                        if (visitor.getState().equals("1")) {
                            if (wait.size() <= 10) {
                                wait.add(visitor); 
                            }
                        } else if (visitor.getState().equals("2")) {
                            if (called.size() <= all.size()) {
                                called.add(visitor);
                            }
                        } else if (visitor.getState().equals("3")) {
                            if (gone.size() <= 4) { 
                                gone.add(visitor);
                            }
                        } else if (visitor.getState().equals("5")) {
                            if (recalled.size() <= all.size()) {
                                recalled.add(visitor);
                            }
                        }
                    }
                }
                for (PmVisitor visitor : called) {
                    visitor.setState("1");
                    taget.add(visitor);
                }
                for (PmVisitor visitor : recalled) {
                    visitor.setState("5");
                    taget.add(visitor);
                }
                for (PmVisitor visitor : wait) {
                    visitor.setState("0");
                    taget.add(visitor);
                }
                for (PmVisitor visitor : gone) {
                    visitor.setState("3");
                    taget.add(visitor);
                }
                result.setUrl(String.valueOf(System.currentTimeMillis()));
                result.setStateType(DoResultType.success);
                result.setStateValue(JacksonUtil.toJson(taget));
                result.setStateMsg("获取成功");
            } catch (Exception e) {
            	result.setUrl(String.valueOf(System.currentTimeMillis()));
                result.setStateType(DoResultType.fail);
                result.setStateMsg("接口出现异常");
                e.printStackTrace();
            }
        } else {
        	result.setUrl(String.valueOf(System.currentTimeMillis()));
            result.setStateType(DoResultType.fail);
            result.setStateMsg("令牌错误");
        }
        System.out.println("返回值======" + JacksonUtil.toJson(result));
        //logger.info("返回给落地屏的数据="+JacksonUtil.toJson(result));
        return JacksonUtil.toJson(result);
    }
}

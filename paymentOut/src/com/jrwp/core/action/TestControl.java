package com.jrwp.core.action;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Action;
import com.jrwp.core.utils.ControlManager;

@Description(value = "测试管理器", state = false)
@Controller
@RequestMapping("testControl")
public class TestControl {
	@Description("测试方法")
	@RequestMapping("test")
	public String test(String str) {
		try {
			List<Action> controllers = ControlManager.getControllers();
			if (controllers != null) {
				for (Action controller : controllers) {
					System.out.println("ControllerName---->"
							+ controller.getName());
					System.out.println("ControllerUrl--->"
							+ controller.getUrl());
					List<Action> actions = controller.getActions();
					for (Action action : actions) {
						System.out.println("ActionName--->" + action.getName());
						System.out.println("ActionUrl--->" + action.getUrl());
					}
				}
			} else {
				System.out.println("null");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "Layout/default";
	}

	@Description("测试方法")
	@RequestMapping("/test1")
	@ResponseBody
	public String test1() {
		return "helloWorld";
	}

	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("123456").toCharArray());
	}
}

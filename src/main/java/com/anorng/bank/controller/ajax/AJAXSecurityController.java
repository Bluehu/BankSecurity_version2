package com.anorng.bank.controller.ajax;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anorng.bank.entity.BankUser;
import com.anorng.bank.entity.Link;
import com.anorng.bank.entity.SecurityUser;
import com.anorng.bank.service.SecurityService;

/**
 * 用于处理证券公司管理的一些AJAX请求
 * @author liuxun
 *
 */
@RestController
@RequestMapping("/securityAjax")
public class AJAXSecurityController {
	
	@Autowired
	SecurityService securityService;

	// ===========================================NEW version ==========================
	@RequestMapping(value="/query_link",method= {RequestMethod.GET})
	public Object queryAllLinkInfoByCompanyId(HttpSession session,HttpServletResponse response,Long id) {
		// 判断参数是否非空
		if (id==null) {
			response.setStatus(400);
			return "参数不能为空";
		}
		
		Object user = session.getAttribute("loginUser");
		if(user == null || !(user instanceof BankUser)) { // 注意此处是 银行管理员进行登录查询的
			response.setStatus(401);
			return "登录身份失效，请重新登录";
		}
		
		List<Link> links = securityService.getAllLinkByCompanyId(id);
		return links;
	}
	@RequestMapping(value="/disable_link",method= {RequestMethod.GET})
	public Object isDisableLink(HttpSession session,HttpServletResponse response,Long id,Boolean isEnable) {
		// 判断参数是否非空
		if (id==null || isEnable==null) {
			response.setStatus(400);
			return "参数不能为空";
		}
		
		Object user = session.getAttribute("loginUser");
		if(user == null || !(user instanceof BankUser)) { // 注意此处是 银行管理员进行登录查询的
			response.setStatus(401);
			return "登录身份失效，请重新登录";
		}
		
		securityService.isEnableLink(id,isEnable);
		return "修改成功";
	}
	
}

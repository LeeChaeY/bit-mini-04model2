package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class CheckDuplicationAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
																	new String[] {	"/config/commonservice.xml"	 }
									                                   );
		System.out.println("\n");

		//==> Bean/IoC Container ∑Œ ∫Œ≈Õ »πµÊ«— UserService ¿ŒΩ∫≈œΩ∫ »πµÊ
		UserService userService = (UserService)context.getBean("userServiceImpl");
		
		String userId=request.getParameter("userId");
		
//		UserService service=new UserServiceImpl();
		boolean result = userService.checkDuplication(userId);
		
		request.setAttribute("result",result);
		request.setAttribute("userId", userId);
		
		return "forward:/user/checkDuplication.jsp";
	}
}
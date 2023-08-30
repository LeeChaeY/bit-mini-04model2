package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

import com.model2.mvc.service.user.dao.UserDao;

import com.model2.mvc.service.domain.User;

//@Controller
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class LoginAction extends Action{
//	@Autowired
//	@Qualifier("userServiceImpl")
//	private UserService userService;
//	
//	@Autowired
//	@Qualifier("userDaoImpl")
//	private UserDao userDAO;
	
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
																	new String[] {	"/config/commonservice.xml"	 }
									                                   );
		System.out.println("\n");

//		==> Bean/IoC Container ∑Œ ∫Œ≈Õ »πµÊ«— UserService ¿ŒΩ∫≈œΩ∫ »πµÊ
		UserService userService = (UserService)context.getBean("userServiceImpl");
		
		User user=new User();
		user.setUserId(request.getParameter("userId"));
		user.setPassword(request.getParameter("password"));
		
//		UserService service=new UserServiceImpl();
		User dbUser = userService.loginUser(user);
		
		HttpSession session=request.getSession();
		session.setAttribute("user", dbUser);
		
		return "redirect:/index.jsp";
	}
}
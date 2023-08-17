package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title","Home-spring boot");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","About-spring boot");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title","Signup-spring boot");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	//handler for registration of user
	//do register accepts the submiited data,matched value with form tags accepts in modelatrribute else aggrement cannot match so it accepts through via request param
	//@PostMapping("/do_register")
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user")User user,BindingResult result1,@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model model,HttpSession session) {
		
		try {
			if(!agreement) {
				System.out.println("you have not agreed terms and conditions");
				//direct to exception
				throw new Exception("you have not agreed terms and conditions");
			}
			if(result1.hasErrors()) {
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			
			User result=userRepository.save(user);
			
			//doubt
			model.addAttribute("user", new User());
			
			session.setAttribute("message",new Message("Successfully Registered !!","alert-success"));
			
			return "signup";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			//doubt
			model.addAttribute("user",user);
			
			//adding message to seession
			session.setAttribute("message",new Message("Something went wrong !!"+e.getMessage(),"alert-danger"));
			return "signup";
			
		}
	}
}

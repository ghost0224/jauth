package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("test")
public class TestController {

	@RequestMapping("hello")
	public ModelAndView hello(String name) {
		ModelAndView mav = new ModelAndView("hi");
		mav.addObject("name", name);
		return mav;
	}
	
}

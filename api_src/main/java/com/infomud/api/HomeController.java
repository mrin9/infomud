package com.infomud.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.*;

@ApiIgnore
@Controller
public class HomeController {
	//@RequestMapping(value = "/")
	@RequestMapping({ "/", "/webui/home", "/webui/login", "/webui/**", "/webui/**/*"})
	public String index() {
		//return "redirect:swagger-ui.html"; //If you want to redirect to REST documentation
		return "forward:index.html";

	}
}

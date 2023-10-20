package com.dev.SangSeung02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

	@RequestMapping("/overall")
	public String overall() {
		
		return "front/overall";
	}
}

package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
		
		@GetMapping("/")
		public String index() {
			return "home.html";	
		}
}

package com.javatechie.spring.ajax.api.controller;

import com.javatechie.spring.ajax.api.cache.BooksCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@Autowired
	SqlService sqlService;

	@Autowired
	BooksCache booksCache;

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("bookNames",booksCache.getNames());
		return "home";
	}

}

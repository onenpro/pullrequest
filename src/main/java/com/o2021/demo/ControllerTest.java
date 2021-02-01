package com.o2021.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ControllerTest {

	@GetMapping("/")
    public String buscarUsuarios() {
		
        return "Hola Mundo";
        

    }
}

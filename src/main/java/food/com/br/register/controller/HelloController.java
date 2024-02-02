package food.com.br.register.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hellow")
public class HelloController {

    @GetMapping
    public String inicioDoProjeto(){
      return " Iniciando um projeto de futuro 01/02/2024 ";
    }
}

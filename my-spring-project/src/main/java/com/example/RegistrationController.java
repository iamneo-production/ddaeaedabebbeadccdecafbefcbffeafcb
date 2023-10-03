package com.example;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

  @RequestMapping("/registration")
  public String showRegistrationForm(Model model) {
    model.addAttribute("user", new User());
    return "formregistration";
  }

  @RequestMapping("/register")
  public String registerUser(
      @Valid @ModelAttribute("user") User user,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "formregistration";
    }

    return "registration-success";
  }

}

package com.itwill.springboot1.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwill.springboot1.domain.Author;
import com.itwill.springboot1.domain.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

   @GetMapping("/")
   public String home(Model model) {
      
      log.info("home() 호출");
      
      LocalDateTime now = LocalDateTime.now(); //서버 현재 시간
      model.addAttribute("currentTime",now); // 뷰에 전달되는 데이터
      
      Author author = Author.builder().firstName("강").lastName("한").build();
      Book book = Book.builder().id(100100).title("채식주의자").author(author).build();
      
      model.addAttribute("book", book);
      
      return "index";
      // 컨트롤러 메서드의 리턴 값(문자열) -> 뷰의 이름
      // 스프링 부트 프로젝트에서는 src/main/resources/templates/리턴값.html
   }
   
}

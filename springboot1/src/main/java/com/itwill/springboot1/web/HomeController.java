package com.itwill.springboot1.web;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
   
   @GetMapping("/book/list")
   public void bookList(Model model) {
	   log.info("bookList()");
	   
	   // 도서목록을 저장할 ArrayList:
	   ArrayList<Book> list = new ArrayList<Book>();
	   String[] titles = {"육식주의자", "채식주의자", "잡식주의자", "음식주의자", "지식주의자"};
	   
	   // list에 Book 타입 객체를 5개 저장
	   for (int i = 0; i < titles.length; i++) {
		   
		   Author author = Author.builder().firstName("강" + (i == 0 ? "" : i)).lastName("한").build();
		   
		   Book book = Book.builder().id(i + 1).title(titles[i]).author(author).build();
		   
		   list.add(book);
		   
	   }
	   
	   // author가 null인 book 객체를 리스트에 추가
	   list.add(Book.builder().id(123).title("구식주의자").build());
	   
	   // list를 뷰에 전달
	   model.addAttribute("book", list);
	   // 뷰(html) 작성
	   
	   
   }
   
   @GetMapping("/book/details")
   public void details(Integer id, Model model) {
	   // Window → preference → JAVA → Compiler → Store information about method parameters 체크 
	   // @RequestParm 애너테이션을 생략할 수 있음
	   log.info("details(id={})", id);
	   
	   Book book = Book.builder().id(id).title("무제").build();
	   model.addAttribute("book", book);
	   
   }
   
   @GetMapping("/book/details/{id}")
   public String bookDetails(@PathVariable Integer id, Model model) {
	   // Window → preference → JAVA → Compiler → Store information about method parameters 체크 
	   // @PathVariable 애너테이션에서 name속성을 생략할 수 있음
	   // ex) @@PathVariable(name ="id") -> @@PathVariable
	   log.info("bookDetails(id={})", id);
	   
	   Book book = Book.builder().id(id).title("채식주의자").build();
	   
	   model.addAttribute("book", book);
	   
	   return "book/details";
	   // -> 뷰 이름: src/main/resources/templates/book/details.html
   }
   
}

package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.dto.DepartmentDetailsDto;
import com.itwill.springboot3.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/dept")
public class DepartmentController {

   private final DepartmentService deptService;

   @GetMapping("/list")
   public void list(@RequestParam(name = "p", defaultValue = "0") int pageNo , Model model) {
      log.info("list(pageNo = {})", pageNo);

      Page<Department> list = deptService.read(pageNo, Sort.by("id"));
      model.addAttribute("page", list);

   }
   
   @GetMapping("/details/{id}")
   public String details(@PathVariable Integer id, Model model) {
      log.info("details(id={})", id);
      DepartmentDetailsDto dto = deptService.read(id);
      model.addAttribute("department",dto);
      
      return "dept/details";
   }
}

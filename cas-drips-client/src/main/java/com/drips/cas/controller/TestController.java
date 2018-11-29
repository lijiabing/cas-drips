//package com.drips.cas.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Created by Administrator on 2018\11\29 0029.
// */
//@RestController
//@RequestMapping("/o")
//public class TestController {
//
//    @GetMapping("/testpp")
//    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession httpSession=request.getSession(true);
//        httpSession.setAttribute("flag","flagcontent");
//        response.sendRedirect("http://127.0.0.1:8080/oop");
//    }
//}

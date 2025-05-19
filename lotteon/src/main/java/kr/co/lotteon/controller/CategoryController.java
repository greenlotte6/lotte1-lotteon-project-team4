package kr.co.lotteon.controller;

import kr.co.lotteon.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;



}

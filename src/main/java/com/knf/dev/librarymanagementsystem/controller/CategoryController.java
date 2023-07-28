package com.knf.dev.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knf.dev.librarymanagementsystem.entity.Category;
import com.knf.dev.librarymanagementsystem.service.CategoryService;

@Controller
public class CategoryController {

	final CategoryService categoryService;
	static final String ATTRIBUTE_NAME = "category";

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;

	}

	@GetMapping("/categories")
	public String findAllCategories(Model model) {

		model.addAttribute("categories", categoryService.findAllCategories());
		return "list-categories";
	}

	@GetMapping("/category/{id}")
	public String findCategoryById(@PathVariable("id") Long id, Model model) {

		model.addAttribute(ATTRIBUTE_NAME, categoryService.findCategoryById(id));
		return "list-category";
	}

	@GetMapping("/addCategory")
	public String showCreateForm(Category category) {
		return "add-category";
	}

	@PostMapping("/add-category")
	public String createCategory(Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-category";
		}

		categoryService.createCategory(category);
		model.addAttribute(ATTRIBUTE_NAME, categoryService.findAllCategories());
		return "redirect:/categories";
	}

	@GetMapping("/updateCategory/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute(ATTRIBUTE_NAME, categoryService.findCategoryById(id));
		return "update-category";
	}

	@PostMapping("/update-category/{id}")
	public String updateCategory(@PathVariable("id") Long id, Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			category.setId(id);
			return "update-category";
		}

		categoryService.updateCategory(category);
		model.addAttribute(ATTRIBUTE_NAME, categoryService.findAllCategories());
		return "redirect:/categories";
	}

	@GetMapping("/remove-category/{id}")
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		categoryService.deleteCategory(id);

		model.addAttribute(ATTRIBUTE_NAME, categoryService.findAllCategories());
		return "redirect:/categories";
	}

}

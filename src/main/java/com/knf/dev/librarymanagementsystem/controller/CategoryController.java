package com.knf.dev.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knf.dev.librarymanagementsystem.entity.Category;
import com.knf.dev.librarymanagementsystem.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CategoryController {

	final CategoryService categoryService;
	private static String A_CATEGORY = "category";
	private static String REDIR_CATEGORIES = "redirect:/categories";


	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;

	}

	@RequestMapping(path="/categories",method= RequestMethod.GET)
	public String findAllCategories(Model model) {
		model.addAttribute("categories", categoryService.findAllCategories());
		return "list-categories";
	}

	@RequestMapping(path="/category/{id}",method= RequestMethod.GET)
	public String findCategoryById(@PathVariable("id") Long id, Model model) {
		model.addAttribute(A_CATEGORY, categoryService.findCategoryById(id));
		return "list-category";
	}

	@GetMapping("/addCategory")
	public String showCreateForm(Category category) {
		return "add-category";
	}

	@RequestMapping(path="/add-category",method= RequestMethod.POST)
	public String createCategory(Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-category";
		}

		categoryService.createCategory(category);
		model.addAttribute(A_CATEGORY, categoryService.findAllCategories());
		return REDIR_CATEGORIES;
	}

	@GetMapping("/updateCategory/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute(A_CATEGORY, categoryService.findCategoryById(id));
		return "update-category";
	}

	@RequestMapping(path="/update-category/{id}",method= RequestMethod.POST)
	public String updateCategory(@PathVariable("id") Long id, Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			category.setId(id);
			return "update-category";
		}

		categoryService.updateCategory(category);
		model.addAttribute(A_CATEGORY, categoryService.findAllCategories());
		return REDIR_CATEGORIES;
	}

	@RequestMapping(path="/remove-category/{id}",method= RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		categoryService.deleteCategory(id);

		model.addAttribute(A_CATEGORY, categoryService.findAllCategories());
		return REDIR_CATEGORIES;
	}

}

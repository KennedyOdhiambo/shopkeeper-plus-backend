package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.category.NewCategoryDto;
import com.kennedy.shopkeeper_plus.dto.category.UpdateCategoryDto;
import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
	private final CategoryService categoryService;

	public CategoriesController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/create")
	public ResponseDto createCategory(
			@Valid @RequestBody NewCategoryDto newCategoryDto
	) {
		return categoryService.createCategory(newCategoryDto);
	}

	@PatchMapping("/update")
	public ResponseDto updateCategory(
			@Valid @RequestBody UpdateCategoryDto updateCategoryDto
	) {
		return categoryService.updateCategory(updateCategoryDto);
	}

	@GetMapping("/list")
	public ResponseDto listCategories() {
		return categoryService.listCategories();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseDto deleteCategory(
			@PathVariable("id") UUID id
	) {
		return categoryService.deleteCategory(id);
	}

}

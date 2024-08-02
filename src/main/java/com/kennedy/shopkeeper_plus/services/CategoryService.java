package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.category.CategoryListDto;
import com.kennedy.shopkeeper_plus.dto.category.NewCategoryDto;
import com.kennedy.shopkeeper_plus.dto.category.UpdateCategoryDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.Category;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.CategoryRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceAlreadyExistsException;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public ResponseDto createCategory(NewCategoryDto newCategoryDto) {

		if (categoryRepository.findByNameAndStatus(newCategoryDto.name(), EntityStatus.ACTIVE).isPresent()) {
			throw new ResourceAlreadyExistsException("Category with similar name already exists");
		}

		var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var category = new Category();
		category.setUser(userDetails);
		category.setName(newCategoryDto.name());
		category.setDescription(newCategoryDto.description());

		category = categoryRepository.save(category);

		return new ResponseDto(
				ResponseStatus.success,
				"Category successfully created",
				new CategoryListDto(
						category.getId(),
						category.getName(),
						category.getDescription()));

	}

	public ResponseDto updateCategory(UpdateCategoryDto updateCategoryDto) {

		var category = categoryRepository.findByIdAndStatus(updateCategoryDto.categoryId(), EntityStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		category.setName(updateCategoryDto.name());
		category.setDescription(updateCategoryDto.description());
		categoryRepository.save(category);

		return new ResponseDto(
				ResponseStatus.success,
				"Category successfully updated",
				null);

	}

	public ResponseDto listCategories() {
		var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var categories = categoryRepository.findByUserId(userDetails.getId());
		var categoriesListDtos = categories.stream()
				.map(category -> new CategoryListDto(category.getId(), category.getName(), category.getDescription()))
				.toList();

		return new ResponseDto(
				ResponseStatus.success,
				"categories:",
				categoriesListDtos);

		//
	}

	public ResponseDto deleteCategory(UUID categoryId) {

		var category = categoryRepository.findByIdAndStatus(categoryId, EntityStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));

		category.setStatus(EntityStatus.DELETED);
		categoryRepository.save(category);

		return new ResponseDto(
				ResponseStatus.success,
				"category successfully deleted",
				null);

	}

}

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
		try {
			if (categoryRepository.findByNameAndStatus(newCategoryDto.name(), EntityStatus.ACTIVE).isPresent()) {
				throw new ResourceAlreadyExistsException("Category with similar name already exists");
			}

			var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			var category = new Category();
			category.setUser(userDetails);
			category.setName(newCategoryDto.name());
			category.setDescription(newCategoryDto.description());

			return new ResponseDto(
					ResponseStatus.success,
					"Category successfully created",
					category
			);

		} catch (Exception e) {
			return new ResponseDto(
					ResponseStatus.fail,
					"error creating category" + e,
					null
			);
		}

	}

	public ResponseDto updateCategory(UpdateCategoryDto updateCategoryDto) {
		try {
			var category = categoryRepository.findByIdAndStatus(updateCategoryDto.categoryId(), EntityStatus.ACTIVE)
					               .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

			var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (category.getUser().getId() != userDetails.getId()) {
				throw new ResourceNotFoundException("Category not found");
			}

			category.setName(updateCategoryDto.name());
			category.setDescription(updateCategoryDto.description());
			categoryRepository.save(category);

			return new ResponseDto(
					ResponseStatus.success,
					"Category successfully updates",
					null
			);

		} catch (Exception e) {
			return new ResponseDto(
					ResponseStatus.fail,
					"error updating category" + e,
					null
			);
		}
	}

	public ResponseDto listCategories() {
		try {
			var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			var categories = categoryRepository.findByUserId(userDetails.getId());
			var categoriesListDtos = categories.stream()
					                         .map(category -> new CategoryListDto(category.getId(), category.getName(), category.getDescription()))
					                         .toList();

			return new ResponseDto(
					ResponseStatus.success,
					"categories:",
					categoriesListDtos
			);

		} catch (Exception e) {

			return new ResponseDto(
					ResponseStatus.fail,
					"error listing categories" + e,
					null
			);
		}

//
	}

	public ResponseDto deleteCategory(UUID categoryId) {
		try {
			var category = categoryRepository.findByIdAndStatus(categoryId, EntityStatus.ACTIVE)
					               .orElseThrow(() -> new ResourceNotFoundException("category not found"));

			category.setStatus(EntityStatus.DELETED);
			categoryRepository.save(category);

			return new ResponseDto(
					ResponseStatus.success,
					"category successfully deleted",
					null
			);

		} catch (Exception e) {
			return new ResponseDto(
					ResponseStatus.fail,
					"error deleting category" + e,
					null
			);
		}
	}


}

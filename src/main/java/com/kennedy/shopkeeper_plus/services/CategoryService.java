package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.category.NewCategoryDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.Category;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.CategoryRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceAlreadyExistsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}

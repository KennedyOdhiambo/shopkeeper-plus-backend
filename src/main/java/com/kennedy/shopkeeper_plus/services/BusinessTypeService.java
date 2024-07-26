package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.BusinessTypeResponseDto;
import com.kennedy.shopkeeper_plus.dto.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessTypeService {
	private final BusinessTypeRepository businessTypeRepository;

	public BusinessTypeService(BusinessTypeRepository businessTypeRepository) {
		this.businessTypeRepository = businessTypeRepository;
	}

	public BusinessTypeResponseDto createBusinessType(NewBusinessTypeDto businessTypeDto) {
		try {
			if (businessTypeDto.name() == null || businessTypeDto.name().trim().isEmpty()) {
				return new BusinessTypeResponseDto(
						"Business type name cannot be empty",
						null
				);
			}

			Optional<BusinessType> existingBusinessType = businessTypeRepository.findByName(businessTypeDto.name());
	
			if (existingBusinessType.isPresent()) {
				return new BusinessTypeResponseDto(
						"Business type with similar name already exists",
						null
				);
			}

			BusinessType businessType = new BusinessType();
			businessType.setName(businessTypeDto.name());
			BusinessType savedBusinessType = businessTypeRepository.save(businessType);

			return new BusinessTypeResponseDto(
					"Business type created successfully",
					savedBusinessType
			);

		} catch (Exception e) {
			return new BusinessTypeResponseDto(
					"An unexpected error occurred.PLease try again later",
					null
			);
		}

	}

	public List<BusinessType> getActiveBusinessTypes() {
		return businessTypeRepository.findByStatus(EntityStatus.ACTIVE);
	}
}

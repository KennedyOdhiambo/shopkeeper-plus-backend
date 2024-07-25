package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.BusinessTypeResponseDto;
import com.kennedy.shopkeeper_plus.dto.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTypeService {
	private final BusinessTypeRepository businessTypeRepository;

	public BusinessTypeService(BusinessTypeRepository businessTypeRepository) {
		this.businessTypeRepository = businessTypeRepository;
	}

	public BusinessTypeResponseDto createBusinessType(NewBusinessTypeDto businessTypeDto) {
		try {
			BusinessType businessType = new BusinessType();
			businessType.setName(businessTypeDto.name());
			BusinessType savedBusinessType = businessTypeRepository.save(businessType);

			return new BusinessTypeResponseDto(
					"Business type created successfully",
					savedBusinessType
			);

		} catch (DataIntegrityViolationException e) {
			return new BusinessTypeResponseDto(
					"Business type with similar name already exists",
					null
			);
		}
		
	}

	public List<BusinessType> getActiveBusinessTypes() {
		return businessTypeRepository.findByStatus("active");
	}
}

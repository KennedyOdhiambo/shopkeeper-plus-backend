package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.business_types.BusinessTypeResponseDto;
import com.kennedy.shopkeeper_plus.dto.business_types.BusinessTypeResponseMessage;
import com.kennedy.shopkeeper_plus.dto.business_types.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.dto.business_types.UpdateBusinessTypeDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusinessTypeService {
	private final BusinessTypeRepository businessTypeRepository;

	public BusinessTypeService(BusinessTypeRepository businessTypeRepository) {
		this.businessTypeRepository = businessTypeRepository;
	}

	public BusinessTypeResponseDto createBusinessType(NewBusinessTypeDto businessTypeDto) {
		try {
			

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

	public BusinessTypeResponseDto updateBusinessType(UpdateBusinessTypeDto updateBusinessTypeDto) {
		try {
			Optional<BusinessType> existingBusinessTypeOptional = businessTypeRepository.findById(updateBusinessTypeDto.id());
			if (existingBusinessTypeOptional.isEmpty()) {
				return new BusinessTypeResponseDto(
						"Business type does not exist",
						null
				);
			}

			BusinessType existingBusinessType = existingBusinessTypeOptional.get();
			if (!existingBusinessType.getName().equals(updateBusinessTypeDto.name())) {
				Optional<BusinessType> businessTypeWithNewName = businessTypeRepository.findByName(updateBusinessTypeDto.name());
				if (businessTypeWithNewName.isPresent() && !businessTypeWithNewName.get().getId().equals(updateBusinessTypeDto.id())) {
					return new BusinessTypeResponseDto(
							"Business type with this name already exists",
							null
					);
				}

				existingBusinessType.setName(updateBusinessTypeDto.name());
				BusinessType updatedBusinessType = businessTypeRepository.save(existingBusinessType);

				return new BusinessTypeResponseDto(
						"Business type updated successfully",
						updatedBusinessType
				);
			} else {
				return new BusinessTypeResponseDto(
						"No changes were made to the business type",
						existingBusinessType
				);
			}
		} catch (Exception e) {
			return new BusinessTypeResponseDto(
					"An unexpected error occurred.PLease try again later",
					null
			);
		}
	}

	public BusinessTypeResponseMessage deleteBusinessType(UUID businessTypeId) {
		try {
			Optional<BusinessType> existingBusinessTypeOptional = businessTypeRepository.findById(businessTypeId);
			if (existingBusinessTypeOptional.isEmpty() || existingBusinessTypeOptional.get().getStatus() != EntityStatus.ACTIVE) {
				return new BusinessTypeResponseMessage("Business type not found");
			}

			var existingBusinessType = existingBusinessTypeOptional.get();
			existingBusinessType.setStatus(EntityStatus.DELETED);
			businessTypeRepository.save(existingBusinessType);

			return new BusinessTypeResponseMessage("Business type successfully deleted");

		} catch (Exception e) {
			return new BusinessTypeResponseMessage("An unexpected error occurred.Please try again later");
		}
	}

}

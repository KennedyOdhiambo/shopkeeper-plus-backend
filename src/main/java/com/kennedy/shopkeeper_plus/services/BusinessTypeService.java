package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.business_types.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.dto.business_types.UpdateBusinessTypeDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceAlreadyExistsException;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BusinessTypeService {
	private final BusinessTypeRepository businessTypeRepository;


	public BusinessTypeService(BusinessTypeRepository businessTypeRepository) {
		this.businessTypeRepository = businessTypeRepository;
	}

	public ResponseDto createBusinessType(NewBusinessTypeDto businessTypeDto) {
		Optional<BusinessType> existingBusinessType = businessTypeRepository.findByName(businessTypeDto.name());

		if (existingBusinessType.isPresent()) {
			throw new ResourceAlreadyExistsException("Business type with similar name already exists");
		}

		BusinessType businessType = new BusinessType();
		businessType.setName(businessTypeDto.name());
		BusinessType savedBusinessType = businessTypeRepository.save(businessType);

		return new ResponseDto(
				ResponseStatus.success,
				"Business type created successfully",
				savedBusinessType
		);
	}


	public ResponseDto getActiveBusinessTypes() {
		var businessTypes = businessTypeRepository.findByStatus(EntityStatus.ACTIVE);
		return new ResponseDto(
				ResponseStatus.success,
				"Business Types",
				businessTypes
		);
	}


	public ResponseDto updateBusinessType(UpdateBusinessTypeDto updateBusinessTypeDto) {
		Optional<BusinessType> existingBusinessTypeOptional = businessTypeRepository.findById(updateBusinessTypeDto.id());
		if (existingBusinessTypeOptional.isEmpty()) {
			throw new ResourceNotFoundException("Business type does not exist");
		}

		BusinessType existingBusinessType = existingBusinessTypeOptional.get();
		if (!existingBusinessType.getName().equals(updateBusinessTypeDto.name())) {
			Optional<BusinessType> businessTypeWithNewName = businessTypeRepository.findByName(updateBusinessTypeDto.name());
			if (businessTypeWithNewName.isPresent() && !businessTypeWithNewName.get().getId().equals(updateBusinessTypeDto.id())) {
				throw new ResourceAlreadyExistsException("Business type with this name already exists");
			}

			existingBusinessType.setName(updateBusinessTypeDto.name());
			BusinessType updatedBusinessType = businessTypeRepository.save(existingBusinessType);

			return new ResponseDto(
					ResponseStatus.success,
					"Business type updated successfully",
					updatedBusinessType
			);
		} else {
			throw new ResourceAlreadyExistsException("No changes were made to the business type");
		}
	}

	public ResponseDto deleteBusinessType(UUID businessTypeId) {
		Optional<BusinessType> existingBusinessTypeOptional = businessTypeRepository.findById(businessTypeId);
		if (existingBusinessTypeOptional.isEmpty() || existingBusinessTypeOptional.get().getStatus() != EntityStatus.ACTIVE) {
			throw new ResourceNotFoundException("Business type not found");
		}

		var existingBusinessType = existingBusinessTypeOptional.get();
		existingBusinessType.setStatus(EntityStatus.DELETED);
		businessTypeRepository.save(existingBusinessType);

		return new ResponseDto(
				ResponseStatus.success,
				"Business type successfully deleted",
				null
		);
	}

}

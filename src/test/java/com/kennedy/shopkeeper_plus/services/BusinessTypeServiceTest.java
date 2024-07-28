package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class BusinessTypeServiceTest {

	@Mock
	private BusinessTypeRepository businessTypeRepository;

	@Mock
	private BusinessTypeService businessTypeService;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		businessTypeService = new BusinessTypeService(businessTypeRepository);
	}

//	@Test
//	public void test_create_business_type_duplicate_name() {
//		NewBusinessTypeDto newBusinessTypeDto = new NewBusinessTypeDto("DuplicateName");
//		BusinessType existingBusinessType = new BusinessType();
//		existingBusinessType.setName("DuplicateName");
//
//
//		when(businessTypeRepository.findByName("DuplicateName")).thenReturn(Optional.of(existingBusinessType));
//
//		BusinessTypeResponseDto response = businessTypeService.createBusinessType(newBusinessTypeDto);
//
//		assertEquals("Business type with similar name already exists", response.message());
//		assertNull(response.businessType());
//	}

//	@Test
//	public void should_return_active_business_types() {
//		var activeBusinessType = new BusinessType();
//		activeBusinessType.setName("active business 1");
//		activeBusinessType.setStatus(EntityStatus.ACTIVE);
//
//		var deletedBusinessType = new BusinessType();
//		deletedBusinessType.setName("active business 2");
//		deletedBusinessType.setStatus(EntityStatus.ACTIVE);
//
//		List<BusinessType> businessTypesLIst = Arrays.asList(activeBusinessType, deletedBusinessType);
//
//		when(businessTypeRepository.findByStatus(EntityStatus.ACTIVE)).thenReturn(businessTypesLIst);
//
//		List<BusinessType> result = businessTypeService.getActiveBusinessTypes();
//
//		assertEquals(2, result.size());
//		assertEquals("active business 1", result.get(0).getName());
//		assertEquals("active business 2", result.get(1).getName());
//
//	}

//	@Test
//	public void should_return_error_if_business_type_does_not_exist() {
//		UpdateBusinessTypeDto dto = new UpdateBusinessTypeDto(UUID.randomUUID(), "New name");
//		when(businessTypeRepository.findById(dto.id())).thenReturn(Optional.empty());
//
//		BusinessTypeResponseDto response = businessTypeService.updateBusinessType(dto);
//
//		assertEquals("Business type does not exist", response.message());
//		assertEquals(null, response.businessType());
//	}

}
package com.contact.calculator.model.mapper;

import com.contact.calculator.model.domain.CustomerDetails;
import com.contact.calculator.model.entities.CustomerDetailsEntity;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserDetailsMapper {

	@Mapping(target = "roles", ignore = true)
	@Mapping(source = "created", target = "created")
	CustomerDetailsEntity map(CustomerDetails customerDetails);

	@Mapping(source = "created", target = "created")
	CustomerDetails map(CustomerDetailsEntity customerDetailsEntity);

}

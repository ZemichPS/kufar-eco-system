package by.zemich.userservice.domain.repository;

import by.zemich.userservice.domain.dto.OrganizationDto;
import by.zemich.userservice.domain.dto.OrganizationFullDto;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import lombok.Data;

import java.util.List;


public interface OrganizationQueryRepository {

    OrganizationDto findById(OrganizationId id);

    OrganizationDto findByOwnerId(UserId id);

    List<OrganizationFullDto> findAll();
}

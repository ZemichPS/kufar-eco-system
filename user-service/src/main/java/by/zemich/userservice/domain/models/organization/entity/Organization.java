package by.zemich.userservice.domain.models.organization.entity;

import by.zemich.userservice.domain.models.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.models.organization.vo.Address;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.organization.vo.OrganizationType;
import by.zemich.userservice.domain.models.organization.vo.PhoneNumber;
import by.zemich.userservice.domain.models.user.vo.UserId;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Organization {
    private OrganizationId organizationId;
    private String name;
    private OrganizationType organizationType;
    private Address address;
    private PhoneNumber phoneNumber;
    private List<UserId> staff = new ArrayList<>();

    public Organization(CreateOrganizationCommand command) {
        this.organizationId = command.organizationId();
        this.name = command.name();
        this.organizationType = OrganizationType.valueOf(command.organizationType());
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.staff.add(command.ownerId());
        this.address = Address.builder()
                .city(command.city())
                .street(command.street())
                .postalCode(command.postalCode())
                .houseNumber(command.houseNumber())
                .district(command.district())
                .apartmentNumber(command.apartmentNumber())
                .region(command.region())
                .build();
    }


    public Organization(OrganizationId organizationId,
                        String name,
                        OrganizationType organizationType,
                        Address address,
                        PhoneNumber phoneNumber,
                        List<UserId> staff
    ) {
        this.organizationId = organizationId;
        this.name = name;
        this.organizationType = organizationType;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.staff = staff;
    }

    public boolean addStaff(UserId id) {
        return staff.add(id);
    }
}


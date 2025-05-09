package by.zemich.userservice.domain.model.organization.entity;

import by.zemich.userservice.domain.model.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.model.organization.vo.Address;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.organization.vo.OrganizationType;
import by.zemich.userservice.domain.model.organization.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.UserId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Organization {
    private OrganizationId organizationId;
    private UserId ownerId;
    private String name;
    private String specialization;
    private OrganizationType organizationType;
    private PhoneNumber phoneNumber;
    private Address address;
    private List<UserId> staff = new ArrayList<>();

    public Organization(CreateOrganizationCommand command) {
        this.organizationId = command.organizationId();
        this.ownerId = command.ownerId();
        this.name = command.name();
        this.specialization = command.specialization();
        this.organizationType = command.organizationType();
        this.phoneNumber = command.phoneNumber();
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


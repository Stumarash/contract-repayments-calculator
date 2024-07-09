package com.contact.calculator.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class RoleEntity extends AbstractBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true)
    private String name;

    //mapped by is a keyword to let know compiler that there is a bidirectional mapping between user and role entities.
    @ManyToMany(mappedBy = "roles")
    private List<CustomerDetailsEntity> user = new ArrayList<>();
}

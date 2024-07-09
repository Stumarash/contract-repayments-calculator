package com.contact.calculator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBaseDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long lockVersion;
    private LocalDateTime created;
    private LocalDateTime updated;

    protected AbstractBaseDomain(AbstractBaseDomain abstractBaseDomain) {
        this.id = abstractBaseDomain.getId();
        this.lockVersion = abstractBaseDomain.getLockVersion();
        this.created = abstractBaseDomain.getCreated();
        this.updated = abstractBaseDomain.updated;
    }
}

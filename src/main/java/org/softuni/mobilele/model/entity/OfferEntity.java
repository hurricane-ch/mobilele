package org.softuni.mobilele.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.JdbcTypeCode;
import org.softuni.mobilele.enums.EngineEnum;
import org.softuni.mobilele.enums.Transmission;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class OfferEntity extends BaseEntity {

    @Column
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    @Column
    private String description;
    @ManyToOne
    private ModelEntity model;
    @Enumerated(EnumType.STRING)
    @Column
    private EngineEnum engine;
    @Enumerated(EnumType.STRING)
    @Column
    private Transmission transmission;
    @Column
    private String imageUrl;
    @Column
    private Long mileage;
    @Column
    private BigDecimal price;
    @Column
    private Integer year;


}

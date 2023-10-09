package org.softuni.mobilele.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.softuni.mobilele.enums.EngineEnum;
import org.softuni.mobilele.enums.TransmissionEnum;

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
    @NotNull
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    @Column
    @NotEmpty
    private String description;
    @NotNull
    @ManyToOne
    private ModelEntity model;
    @Enumerated(EnumType.STRING)
    @Column
    private EngineEnum engine;
    @Enumerated(EnumType.STRING)
    @Column
    private TransmissionEnum transmission;
    @Column
    @NotEmpty
    private String imageUrl;
    @Column
    @Positive
    private long mileage;
    @Column
    @NotNull
    private BigDecimal price;
    @Column
    @Min(1950)
    private Integer year;


}

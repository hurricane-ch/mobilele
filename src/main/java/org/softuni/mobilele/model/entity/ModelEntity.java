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
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.softuni.mobilele.enums.ModelCategoryEnum;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "models")
public class ModelEntity extends BaseEntity {

    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    @Column
    private ModelCategoryEnum category;
    @ManyToOne
    private BrandEntity brand;

}

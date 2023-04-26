package com.capsule.capsuleassessment.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@ApiModel(description = "Description about MedicineMaster model")
public class MedicineMaster {

    @Id
    @GeneratedValue
    private int id;
    @ApiModelProperty(notes = "It can not be null and duplicated")
    private String productId;
    private String medicineName;
    private String composition;
    private String manufacturer;
    private String packing;


}

package com.capsule.capsuleassessment.models;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class MedicineMaster {

    @Id
    @GeneratedValue
    private int id;
    private String productId;
    private String medicineName;
    private String composition;
    private String manufacturer;
    private String packing;


}

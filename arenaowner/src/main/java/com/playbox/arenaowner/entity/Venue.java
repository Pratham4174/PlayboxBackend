package com.playbox.arenaowner.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "venue",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"name", "location", "city", "state", "pincode"}
    )
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String city;
    private String state;
    private String pincode;
    private String coordinates; // can be lat,long string
    private String contactNumber;
    private String description;
    private String operationTime;

    @ManyToOne
@JoinColumn(name = "owner_id")
private ArenaOwner owner;

    @ElementCollection
    private List<String> amenities;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SportPrice> sportPrices;

@OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval= true)
@JsonManagedReference
private List<VenueImage> images;
    // Store S3/GCS/local URLs after upload
}

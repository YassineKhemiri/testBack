package com.javachinna.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String contactNumber;
    private String email;
    private String category;
    private String subject;
    private String description;
    @Lob
    @Column(name = "file_data", columnDefinition="LONGBLOB")
    private byte[] fileData;  // Renamed to clarify its usage
    @ManyToOne(optional = true)
    private User user;

}

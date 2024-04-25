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

    private String NomPrenom;
    private String Telephone;
    private String email;
    private String categorie;
    private String sujet;
    private String description;
    @Lob
    @Column(name = "file_data", columnDefinition="LONGBLOB")
    private byte[] fileData;  // Renamed to clarify its usage
    @ManyToOne(optional = true)
    private User user;

}

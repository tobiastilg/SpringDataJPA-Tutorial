package at.itkollegimst.spring.data.jpa.tutorial.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "course") //aufgrund der Testklasse printAllCourseMaterials()
public class CourseMaterial {

    @Id
    @SequenceGenerator(
            name = "course_material_sequence",
            sequenceName = "course_material_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_material_sequence")
    private Long courseMaterialId;
    private String url;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false) //mapping
    //(CascadeType.ALL erstellt die Elemente für einen Befehl, falls diese noch nicht in der DB gespeichert sind
    @JoinColumn(name = "course_id", referencedColumnName = "courseId") //Referenz (Datenfeld - Spalte)
    private Course course;

}
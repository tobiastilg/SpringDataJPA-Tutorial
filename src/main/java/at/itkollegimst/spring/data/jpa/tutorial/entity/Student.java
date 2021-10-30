package at.itkollegimst.spring.data.jpa.tutorial.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_student", //Tabellenname mitgeben (alter table wird nicht gelöscht -> ddl-auto=update )
       uniqueConstraints = @UniqueConstraint( //UniqueKeys festlegen
               name = "emailid_unique",
               columnNames = "email_address"))
public class Student {

    @Id //auto-increment/PrimaryKey
    @SequenceGenerator(name = "student_sequence", //definiert den PrimaryKey-Generator
            sequenceName = "student_sequence",
            allocationSize = 1)
    /*SEQUENCE erzeugt den Wert schon vor der Erstellung der Entity (Vorteil gegenüber AUTO oder IDENTITY),
    wichtig sollte der PrimaryKey schon zuvor benötigt wird (bessere Lösung)
    Dokumentation: https://www.objectdb.com/java/jpa/entity/generated#the_sequence_strategy*/
    @GeneratedValue(strategy = GenerationType.SEQUENCE, //Spezifizierung von PrimaryKey Generierung
            generator = "student_sequence")
    private Long studentId;
    private String firstName;
    private String lastName;

    @Column(name = "email_address", //Spaltenname mitgeben (sollte bei jedem datenfeld gemacht werden)
            nullable = false) //darf nicht null sein
    private String emailId;

    @Embedded //übernimmt die Eigenschaften aus Guardian durch @AttributeOverrides
    private Guardian guardian;
}
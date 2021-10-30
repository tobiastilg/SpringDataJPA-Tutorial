package at.itkollegimst.spring.data.jpa.tutorial.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Diese Datenfelder dieser Klasse sind Attribute der Entity Student.
 * Da Guardian ist ein eigenes Objekt, soll aber kein Entity sein, das
 * es nicht in der Datenbank aufscheinen soll.
 * Daher: @Embeddable
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AttributeOverrides({ //Mapping der Datenfelder auf die Spalten in Student
        @AttributeOverride(
                name = "name",
                column = @Column(name = "guardian_name")
        ),
        @AttributeOverride(
                name = "email",
                column = @Column(name = "guardian_email")
        ),
        @AttributeOverride(
                name = "mobile",
                column = @Column(name = "guardian_mobile")
        )
})
public class Guardian {

    private String name;
    private String email;
    private String mobile;
}
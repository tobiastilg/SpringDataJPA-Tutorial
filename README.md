# Spring Data JPA Tutorial

Vertiefung von Spring Boot JPA (Data Access)

- [Spring Data JPA Tutorial](#spring-data-jpa-tutorial)
    - [JPA](#jpa)
    - [Datenbankanbindung](#datenbankanbindung)
    - [Repositories](#repositories)
    - [Objekte ohne Tabelle](#objekte-ohne-tabelle)
- [Relationships](#relationships)
    - [One-To-One](#one-to-one)
    - [One-To-Many](#one-to-many)
        - [Many-To-One](#many-to-one)
    - [Many-To-Many](#many-to-many)
    
## JPA

__JDBC__ ist eine von Java bereitgestellte API (Datenbakschnittstelle). Da Datenbanken aber mit Tabellen und Java mit Objekten (Klassen) arbeitet, müsste man die Daten aus der Datenbank lesen und in Objekte umwandeln.

__ORM__ (Object Relationship Mapping) ist, wie es der Name schon verrät, die Funktion, ein Objekt auf ein relationales Datenbankschema zu mappen (die Klasse/das Objekt entspricht der Tabelle). In Java gibt es unterschiedliche Frameworks, die diese Funkionalität zur Verfügung stellen (zB `Hibernate` oder `Ibatis`).

Die unterschiedlichen Frameworks bieten unterschiedliche Implementierungen, weshalb das Wechseln eines Frameworks in einer Applikation recht schwierig und zeitaufwendig sein kann. Deshalb gibt es Standardspezifikationen, die die API Anbieter verwenden, um den Programmierern, die das Framework nutzen, einen Austausch wesentlich einfacher zu ermöglichen.

Die Standardspezifikation von Java nennt sich __JPA__ (Java Persistence API) und ermöglicht ein entwicklerfreundliches Benutzen und Austauschen von __ORM__ Implementierungen. Wichitg zu verstehen ist, JPA kann man nur in Verbindung mit einem ORM-Framework verwenden, JPA stellt nur die Spezifikation bereit.

## Datenbankanbindung

In der `application.properties` wird die Anbindung zur Datenbank konfiguriert. Die folgende Zeile beschreibt, wie sich Änderungen in unserer Apüplikation auf die Datenbank auswirkt. In diesem Fall wird bei jedem Update in unserer Applikation direkt der Datenbankeintrag bearbeitet. Wenn beispielsweise ein neues Datenfeld einer Klasse hinzugefügt wird, wird auch direkt das Attribut dem Entity hinzugefügt.

```properties
spring.jpa.hibernate.ddl-auto=update
```

## Repositories

Repositories sind dazu da, zwischen der Datenbank und der Applikation zu kommunizieren (Data Access -> richtige und saubere Kopplung siehe Bild Standardarchitektur).

Durch die JPA Spezifikationen müsen die Mehtoden in Repos nur Keywords entsprechen, damit automatisch eine Implementierung zur Verfügung gestellt werden kann (siehe Doku: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation).

Ist es aber nicht möglich eine automatische Methodenimplementierung durch die Keywords zu erzeugen, dann muss die Query selbst geschrieben werden. Mit der `@Query` Notation kann eine JPQL Query mitgegeben werden. JPQL bezieht sich, im Gegensatz zu SQL, nicht auf Tabellen und Spalten(namen), sondern auf Klassen und Attribute (Datenfelder).

```java
@Query("select s from Student s where s.emailId = ?1")
Student getStudentByEmailAddress(String EmailId);
```

Es kann aber auch eine native SQL-Query mitgegeben werden. Mitgegebene Parameter können bei Bedarf auch benannt werden, um eine verständlichere Query zu schreiben.

```java
@Query(value = "select * from tbl_student s where s.email_address = :emailId", nativeQuery = true)
Student getStudentByEmailAddressNativeNamedParam(@Param("emailId") String emailId);
```

Für eine update Query muss zusätzlich noch die `@Modifying` Notation angegeben werden. Da es sich um einen Befehl handelt, der Daten bearbeitet (bzw. eine Transaktion ausführt wie zB update, insert, usw.), muss auch noch der `@Transactional` Tag verwendet werden (@Transactional sollte eigentlich immer im Servicelayer benutzt werden!).

## Objekte ohne Tabelle

Es kann vorkommen, dass Entities Eigenschaften von anderen Objekten besitzen, die aber nicht in der Datenbank stehen (zB Student & Guardian). In einer sauberen Applikation sollte eine eigene Klasse für jedes Objekt erstellt und nicht nur die Objekteigenschaften in die Entity Klasse gespeichert werden.

Dazu lassen sich Datenfelder einer `@Embeddable` Klasse, mithilfe von `@AttributeOverrides` mappen und in der Tabelle festlegen. Das jeweilige Entity hält einfach das Objekt als `@Embedded` Attribut.

# Relationships

Beziehungen zwischen mehreren Entities können mithilfe von JPA einfach programmiert werden. Wichtig sind zwei Notationen: Einmal die Notation für die jeweilige Beziehung und einmal `@JoinColumn`, um PK und FK abzubilden und das Attribut mit der Spalte referenizeren. In den folgenden Unterpunkten werden Beispiele für die verschiedenen Beziehungen aufgezeigt.

Bei Datenabfragen wird zwischen zwei `Fetch Types` unterschieden: Eager und Lazy. Wird ein Tabelleneintrag mit Fremdschlüssel abgefragt so wird der Typ Lazy nur den antsprechenden Eintrag zurückliefern, Eager hingegen liefert auch den Referenzeintrag zurück (bei gleichem fetch Aufruf - wird natürlich expliziet auch nach dem referenzierten Eintrag gefragt ist der Fetch Typ egal).

Sollte bei einer Abfrage keine Referenz zu Verfügung stehen und man möchte trotzdem den dazugehörigen Eintrag bzw. die dazugehörigen Einträge, so benötigt man ein `bidirectional relationship`. Durch die eine `@OneToOne` Notation in der Klasse, die nicht den FK hält, kann das erreicht werden.

```java
@OneToOne(mappedBy = "course")
private CourseMaterial courseMaterial;
```

## One-To-One

```java
@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false) //mapping
//CascadeType.ALL erstellt die Elemente für einen Befehl, falls diese noch nicht in der DB gespeichert sind
@JoinColumn(name = "course_id", referencedColumnName = "courseId") //Referenz (Datenfeld - Spalte)
private Course course;
```

## One-To-Many

```java
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "teacher_id", referencedColumnName = "teacherId")
//Output: alter table course add column teacher_id bigint & erstellen eines FK auf teacher_id in der teacher Tabelle
private List<Course> courses;
```

### Many-To-One

```java
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "teacher_id", referencedColumnName = "teacherId")
private Teacher teacher;
```

Auch wenn der Output der gleiche ist, ist diese Lösung eleganter und verständlicher bzw. leserlicher.

## Many-To-Many

Many-To-Many Relationships werden immer durch Intersection Entities aufgelöst.

```java
@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(name = "student_course_map", 
          joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "courseId"),
          inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName
          "studentId")
)
private List<Student> students;

public void addStudents(Student student) {
     if (students == null) students = new ArrayList<>();
     students.add(student);
}
```

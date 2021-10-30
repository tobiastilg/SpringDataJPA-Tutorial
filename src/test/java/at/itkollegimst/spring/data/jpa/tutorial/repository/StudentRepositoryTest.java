package at.itkollegimst.spring.data.jpa.tutorial.repository;

import at.itkollegimst.spring.data.jpa.tutorial.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
/*@DataJpaTest -> normalerweise um ein Repo zu testen, da wir aber die DB-Einträge behalten wollen,
verwenden wir @SpringBootTest*/
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .emailId("hans@gmail.com")
                .firstName("Hans")
                .lastName("Jörg")
                .guardianName("Peter")
                .guardianEmail("peter@gmail.com")
                .guardianMobile("0660123456789")
                .build();

        studentRepository.save(student);
    }

    @Test
    public void printAllStudent() {
        List<Student> studentList = studentRepository.findAll();
        System.out.println("studentList = " + studentList);
    }
}
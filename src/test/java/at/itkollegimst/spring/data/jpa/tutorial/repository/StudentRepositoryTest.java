package at.itkollegimst.spring.data.jpa.tutorial.repository;

import at.itkollegimst.spring.data.jpa.tutorial.entity.Guardian;
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
                //.guardianName("Peter")
                //.guardianEmail("peter@gmail.com")
                //.guardianMobile("0660123456789")
                .build();

        studentRepository.save(student);
    }

    @Test
    public void saveStudentWithGuardian() {

        Guardian guardian = Guardian.builder()
                .name("Peter")
                .email("peter@gmail.com")
                .mobile("0660123456789")
                .build();

        Student student = Student.builder()
                .emailId("fritz@gmail.com")
                .firstName("Fritz")
                .lastName("Amann")
                .guardian(guardian)
                .build();

        studentRepository.save(student);
    }

    @Test
    public void printAllStudent() {
        List<Student> studentList = studentRepository.findAll();
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void printStudentByFirstName(){
        List<Student> students = studentRepository.findByFirstName("Fritz");

        System.out.println("students = " + students);
    }

    @Test
    public void printStudentByFirstNameContaining(){
        List<Student> students = studentRepository.findByFirstNameContaining("tz");

        System.out.println("students = " + students);
    }

    @Test
    public void printStudentBasedOnGuardianName(){
        List<Student> students = studentRepository.findByGuardianName("Peter");

        System.out.println("students = " + students);
    }

    @Test
    public void printgetStudentByEmailAddress(){
        Student student = studentRepository.getStudentByEmailAddress("hans@gmail.com");

        System.out.println("student = " + student);
    }

    @Test
    public void printgetStudentFirstNameByEmailAddress(){
        String firstName = studentRepository.getStudentFirstNameByEmailAddress("hans@gmail.com");

        System.out.println("firstName = " + firstName);
    }

    @Test
    public void printgetStudentByEmailAddressNative(){
        Student student = studentRepository.getStudentByEmailAddressNative("hans@gmail.com");

        System.out.println("student = " + student);
    }

    @Test
    public void printgetStudentByEmailAddressNativeNamedParam(){
        Student student = studentRepository.getStudentByEmailAddressNativeNamedParam("hans@gmail.com");

        System.out.println("student = " + student);
    }

    @Test
    public void updateStudentNameByEmailIdTest(){
        studentRepository.updateStudentNameByEmailId("Hubert", "hans@gmail.com");
    }
}
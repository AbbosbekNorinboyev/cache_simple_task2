package uz.pdp.cache_simple_task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cache_simple_task2.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
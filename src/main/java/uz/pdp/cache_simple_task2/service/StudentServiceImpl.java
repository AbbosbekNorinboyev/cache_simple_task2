package uz.pdp.cache_simple_task2.service;

import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.cache_simple_task2.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task2.dto.StudentUpdateDTO;
import uz.pdp.cache_simple_task2.entity.Student;
import uz.pdp.cache_simple_task2.mapper.StudentMapper;
import uz.pdp.cache_simple_task2.repository.StudentRepository;

import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CacheManager cacheManager;
    private final Cache cache;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, CacheManager cacheManager) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getCache("students");
    }

    @Override
    @Transactional
    public Student createStudent(@NonNull StudentCreateDTO studentCreateDTO) {
        Student student = studentMapper.toEntity(studentCreateDTO);
        return studentRepository.save(student);
    }

    @Override
    @SneakyThrows
    public Student getStudent(@NonNull Long id) {
        assert cache != null;
        Student cachedStudent = cache.get(id, Student.class);
        if (cachedStudent != null) {
            return cachedStudent;
        }
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        TimeUnit.SECONDS.sleep(1);
        cache.put(id, student);
        return student;
    }

    @Override
    public void updateStudent(@NonNull StudentUpdateDTO studentUpdateDTO) {
        Student student = getStudent(studentUpdateDTO.getId());
        if (studentUpdateDTO.getName() != null) {
            student.setName(studentUpdateDTO.getName());
        }
        student.setAge(studentUpdateDTO.getAge());
        studentRepository.save(student);
        assert cache != null;
        cache.put(studentUpdateDTO.getId(), student);
    }

    @Override
    public void deleteStudent(@NonNull Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        studentRepository.deleteById(student.getId());
        assert cache != null;
        cache.evict(id);
    }
}

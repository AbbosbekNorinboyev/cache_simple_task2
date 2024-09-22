package uz.pdp.cache_simple_task2.mapper;

import org.mapstruct.*;
import uz.pdp.cache_simple_task2.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task2.entity.Student;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    Student toEntity(StudentCreateDTO studentCreateDTO);

    StudentCreateDTO toDto(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student partialUpdate(StudentCreateDTO studentCreateDTO, @MappingTarget Student student);
}
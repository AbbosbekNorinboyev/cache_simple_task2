package uz.pdp.cache_simple_task2.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StudentUpdateDTO {
    private Long id;
    private String name;
    private int age;
}

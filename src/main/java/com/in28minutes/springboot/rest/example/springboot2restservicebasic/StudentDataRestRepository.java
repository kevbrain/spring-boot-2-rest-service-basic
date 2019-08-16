package com.in28minutes.springboot.rest.example.springboot2restservicebasic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "students", collectionResourceRel = "students")
@CrossOrigin(origins = "http://localhost:4200")
public interface StudentDataRestRepository extends JpaRepository<Student, Long>{

}

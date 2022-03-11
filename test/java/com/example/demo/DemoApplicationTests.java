package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestEntityManager
class DemoApplicationTests {

	@Autowired
	EmployeeRepo repo;

	@Autowired
	EntityManager entityManager;

	@Test
	void contextLoads() {
	}
//Perform Create Operation on Entity using Spring Data JPA
	@Test
	public void testCreate(){
		Employee e = new Employee();
		e.setId(1);
		e.setName("somesh");
		e.setAge(23);
		e.setLocation("bihar");
		repo.save(e);
	}

	//Perform Read Operation on Entity using Spring Data JPA

	@Test
	public void testRead(){
		Employee e = repo.findById(1).get();
		assertNotNull(e);
		assertEquals("somesh" , e.getName());
	}

	//Perform update Operation on Entity using Spring Data JPA

	@Test
	public void testUpdate(){
		Employee e = repo.findById(1).get();
		e.setAge(24);
		repo.save(e);
	}

	//Perform delete Operation on Entity using Spring Data JPA

	@Test
	public void testDelete(){
		boolean b = repo.existsById(2);
		if(b){
			repo.deleteById(2);
		}
	}

	//Perform count Operation on Entity using Spring Data JPA

	@Test
	public void testCount(){
		System.out.println(repo.count());
	}

	//Implement Pagination and Sorting on the bases of Employee Age

	@Test
	public void testPagingandSorting(){
		//Pageable pageable = PageRequest.of(0,2, Sort.by("age"));
		Pageable pageable = PageRequest.of(0,2,Sort.by("age"));
		Page<Employee> all = repo.findAll(pageable);
		all.forEach(p->System.out.println(p.getAge()));
	}

	//Create and use finder to find Employee by Name

	@Test
	public void testFindByName(){
		List<Employee> e = repo.findByName("tushar");
		e.forEach(p->System.out.println(p.getName()+" "+p.getLocation()));
	}

	//Create and use finder to find Employees starting with A character

	@Test
	public void testFindByNameLike(){
		List<Employee> e = repo.findByNameLike("A%");
		e.forEach(p->System.out.println(p.getName()+" "+p.getLocation()));
	}

	//Create and use finder to find Employees Between the age of 28 to 32
	@Test
	public void testFindByAgeBetween(){
		List<Employee> e = repo.findByAgeBetween(28 , 32);
		e.forEach(p->System.out.println(p.getName()+" "+p.getLocation()));
	}


}

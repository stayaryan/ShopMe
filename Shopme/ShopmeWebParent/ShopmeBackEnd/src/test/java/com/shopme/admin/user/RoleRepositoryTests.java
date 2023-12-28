package com.shopme.admin.user;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class RoleRepositoryTests {

	@Autowired
	private RoleRepository roleRepository;


	@Test
	public void testCreateFirstRole(){
		Role roleAdmin = new Role("Admin","manage Everything");
		Role savedRole = roleRepository.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateRestRoles(){
		Role roleOther = new Role("Assistant","manage questions and reviews");
		Role savedRole = roleRepository.save(roleOther);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

}

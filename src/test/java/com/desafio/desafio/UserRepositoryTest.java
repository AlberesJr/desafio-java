package com.desafio.desafio;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.repositories.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

	private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createShouldPersistData(){
        final LocalDate now = LocalDate.now();
        final Date date = new Date();
        final User user = new User("FirstName", "LastName", "email", now,
                            "login", "password", "phone", date);
        this.userRepository.save(user);
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getFirstName()).isEqualTo("FirstName");
        Assertions.assertThat(user.getLastName()).isEqualTo("LastName");
    }

    @Test
    public void DeleteShouldRemoveData(){
        final LocalDate now = LocalDate.now();
        final Date date = new Date();
        final User user = new User("FirstName", "LastName", "email", now,
                            "login", "password", "phone", date);
        this.userRepository.save(user);
        userRepository.delete(user);
        Optional<User> usr = userRepository.findById(user.getId());
        Assertions.assertThat(usr.get()).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        final LocalDate now = LocalDate.now();
        final Date date = new Date();
        final User user = new User("FirstName", "LastName", "email", now,
                            "login", "password", "phone", date);
        this.userRepository.save(user);
        user.setFirstName("FirstNameUpdate");
        this.userRepository.save(user);
        Assertions.assertThat(user.getFirstName()).isEqualTo("FirstNameUpdate");
    }

    @Test
    public void findByLoginShouldPersistData(){
        final LocalDate now = LocalDate.now();
        final Date date = new Date();
        final User user = new User("FirstName", "LastName", "email", now,
                            "login", "password", "phone", date);
        this.userRepository.save(user);
        Optional<User> obj = this.userRepository.findByLogin(user.getLogin());
        Assertions.assertThat(obj.get().getLogin()).isEqualTo("login");
    }

    @Test
	public void spectedUserLogged() throws Exception {
        final LocalDate now = LocalDate.now();
        final Date date = new Date();
        final User user = new User("FirstName", "LastName", "email", now,
                            "login", "password", "phone", date);
        this.userRepository.save(user);
		ResultMatcher statusOk = MockMvcResultMatchers.status().isOk();
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{" + 
						"	\"login\": \"login\"," + 
						"	\"password\": \"password\"\n" + 
						"}");

		
		this.mockMvc.perform(builder).andExpect(statusOk);
		
	}

    
}

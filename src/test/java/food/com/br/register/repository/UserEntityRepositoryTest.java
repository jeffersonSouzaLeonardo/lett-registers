package food.com.br.register.repository;

import food.com.br.register.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserEntityRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnUserForFieldLogin(){
        var user = new UserEntity(null, "userTest", "123425");
        userRepository.saveAndFlush(user);

        assertThat(userRepository.findByLogin("userTest")).isEqualTo(user);

    }

}
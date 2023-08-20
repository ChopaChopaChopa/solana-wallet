package io.github.chopachopachopa.solanawallet;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


class SolanaWalletApplicationTests {

	@Test
	void contextLoads() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(8);

	}

}

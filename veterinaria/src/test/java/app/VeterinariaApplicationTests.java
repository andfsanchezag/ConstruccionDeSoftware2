package app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import app.infrastructure.persistence.repository.UserRepository;
import app.infrastructure.persistence.repository.PetRepository;
import app.infrastructure.persistence.repository.ClinicalOrderRepository;
import app.infrastructure.persistence.repository.ClinicalRecordRepository;

@SpringBootTest
class VeterinariaApplicationTests {

	@MockBean
	private UserRepository userRepository;
	@MockBean
	private PetRepository petRepository;
	@MockBean
	private ClinicalOrderRepository clinicalOrderRepository;
	@MockBean
	private ClinicalRecordRepository clinicalRecordRepository;

	@Test
	void contextLoads() {
	}

}

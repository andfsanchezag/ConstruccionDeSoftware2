package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.adapter.in.client.SellerClient;

@SpringBootApplication
public class VeterinariaApplication implements CommandLineRunner  {
	
	@Autowired
	private SellerClient client;
	
	public static void main(String[] args) {
		SpringApplication.run(VeterinariaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		client.session();
		
	}

	
	

}

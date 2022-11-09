package com.guro.kokeetea_project;

import com.guro.kokeetea_project.controller.RequestController;
import com.guro.kokeetea_project.dto.RequestFormDTO;
import com.guro.kokeetea_project.entity.Ingredient;
import com.guro.kokeetea_project.entity.Request;
import com.guro.kokeetea_project.entity.Store;
import com.guro.kokeetea_project.repository.IngredientRepository;
import com.guro.kokeetea_project.repository.RequestRepository;
import com.guro.kokeetea_project.repository.StoreRepository;
import com.guro.kokeetea_project.service.RequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class KokeeteaAppTests {
	@Autowired
	RequestService requestService;
	@Autowired
	RequestRepository requestRepository;
	@Autowired
	IngredientRepository ingredientRepository;
	@Autowired
	StoreRepository storeRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Make Request")
	void createRequest() {
		Ingredient ingredient = new Ingredient();
		ingredient.setName("Mint");
		ingredient.setPrice(1000L);
		ingredientRepository.save(ingredient);

		Store store = new Store();
		store.setName("마포1호점");
		store.setLocation("마포구 이순신로");
		store.setEmail("honey123@email.com");
		store.setPhone("010-2345-7712");
		storeRepository.save(store);

		RequestFormDTO dto = new RequestFormDTO();
		dto.setAmount(200L);
		dto.setIngredientId(ingredient.getId());
		dto.setStoreId(store.getId());

		Long id = requestService.create(dto);
		Request result = requestRepository.findById(id).orElseThrow();
		Long amount = result.getAmount();
		String ingredientName = result.getIngredient().getName();
		assert Objects.equals(amount, 200L);
		assert Objects.equals(ingredientName, "Mint");
	}
}

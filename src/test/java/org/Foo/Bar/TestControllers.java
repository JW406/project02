package org.Foo.Bar;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.RestObjects.SuccessResponse;
import org.Foo.Bar.RestObjects.UpdateUserInfo;
import org.Foo.Bar.Security.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(locations = "classpath:springmvc.xml", resourcePath = "src/main/resources")
public class TestControllers {

  private MockMvc mockMvc;
  @Autowired
  private TokenManager tokenManager;
  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private UserDao userDao;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void testGetAllPokemonController() {
    try {
      mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/get-all-pokemons"))
          .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetUserInfo() {
    User user = TestUtils.newUser(userDao);
    String token = tokenManager.generateJwtToken(
        new org.springframework.security.core.userdetails.User(user.getEmail(), "", new ArrayList<>()),
        new HashMap<>());
    try {
      mockMvc.perform(MockMvcRequestBuilders.get("/api/get-user-info").header("Authorization", "Bearer " + token))
          .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    } catch (Exception e) {
      e.printStackTrace();
    }
    TestUtils.deleteUser(userDao, user);
  }

  @Test
  public void testPatchUserInfo() {
    User user = TestUtils.newUser(userDao);
    String token = tokenManager.generateJwtToken(
        new org.springframework.security.core.userdetails.User(user.getEmail(), "", new ArrayList<>()),
        new HashMap<>());
    try {
      UpdateUserInfo updateUserInfo = new UpdateUserInfo();
      updateUserInfo.setName("hello@world" + new Random().nextInt(5555));
      updateUserInfo.setZipCode("12345");
      MvcResult result = mockMvc
          .perform(MockMvcRequestBuilders.patch("/api/update-user-info").header("Authorization", "Bearer " + token)
              .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updateUserInfo)))
          .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
      SuccessResponse successResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
          SuccessResponse.class);
      assertTrue(successResponse.getSuccess());
    } catch (Exception e) {
      e.printStackTrace();
    }
    TestUtils.deleteUser(userDao, user);
  }
}

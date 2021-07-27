package org.Foo.Bar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.Foo.Bar.Entities.PokeTransaction;
import org.Foo.Bar.Entities.TokenTxLog;
import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.TokenTxLogDao;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.RestObjects.CheckoutModel;
import org.Foo.Bar.RestObjects.GitHubAccessToken;
import org.Foo.Bar.RestObjects.GoogleAccessToken;
import org.Foo.Bar.RestObjects.MakeTransactionBody;
import org.Foo.Bar.RestObjects.SuccessResponse;
import org.Foo.Bar.RestObjects.TokenQueryResponse;
import org.Foo.Bar.RestObjects.TransactionResponse;
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
  @Autowired
  private TokenTxLogDao tokenTxLogDao;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void testUntestablePojo() {
    CheckoutModel checkoutModel = new CheckoutModel();
    checkoutModel.setAmount(1L);
    checkoutModel.getAmount();
    checkoutModel.getItemName();
    checkoutModel.getQuantities();
    GitHubAccessToken gitHubAccessToken = new GitHubAccessToken();
    gitHubAccessToken.setAccess_token("1");
    gitHubAccessToken.getAccess_token();
    gitHubAccessToken.getScope();
    gitHubAccessToken.getToken_type();
    GoogleAccessToken googleAccessToken = new GoogleAccessToken();
    googleAccessToken.setAccess_token("1");
    googleAccessToken.getAccess_token();
    googleAccessToken.getExpires_in();
    googleAccessToken.getId_token();
    googleAccessToken.getScope();
    googleAccessToken.getToken_type();
    TokenTxLog tokenTxLog = new TokenTxLog();
    tokenTxLog.getCustomer();
    tokenTxLog.getTransaction();
    PokeTransaction pokeTransaction = new PokeTransaction();
    pokeTransaction.getTxCreatedTime();
    pokeTransaction.getTxSourceType();
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

  @Test
  public void testUserTransactions() {
    User user = TestUtils.newUser(userDao);
    String token = tokenManager.generateJwtToken(
        new org.springframework.security.core.userdetails.User(user.getEmail(), "", new ArrayList<>()),
        new HashMap<>());
    try {
      MakeTransactionBody makeTransactionBody = new MakeTransactionBody();
      makeTransactionBody.setTxAmnt(123L);
      MvcResult result = mockMvc
          .perform(MockMvcRequestBuilders.post("/api/shop/make-transaction").header("Authorization", "Bearer " + token)
              .contentType(MediaType.APPLICATION_JSON)
              .content(new ObjectMapper().writeValueAsString(makeTransactionBody)))
          .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
      TransactionResponse successResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
          TransactionResponse.class);
      assertTrue(successResponse.getSuccess());
      tokenTxLogDao.deleteById(successResponse.getTxId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    TestUtils.deleteUser(userDao, user);
  }

  @Test
  public void testUserTokenQuery() {
    User user = TestUtils.newUser(userDao);
    Long tokenBefore = user.getPokeToken();
    String token = tokenManager.generateJwtToken(
        new org.springframework.security.core.userdetails.User(user.getEmail(), "", new ArrayList<>()),
        new HashMap<>());
    try {
      MvcResult result = mockMvc
          .perform(MockMvcRequestBuilders.get("/api/user/tokens").header("Authorization", "Bearer " + token))
          .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
      TokenQueryResponse successResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
          TokenQueryResponse.class);
      assertEquals(successResponse.getPokeToken(), tokenBefore);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      mockMvc.perform(MockMvcRequestBuilders.get("/api/user/tokens"))
          .andExpect(MockMvcResultMatchers.status().is(401)).andDo(MockMvcResultHandlers.print()).andReturn();
    } catch (Exception e) {
      e.printStackTrace();
    }
    TestUtils.deleteUser(userDao, user);
  }
}

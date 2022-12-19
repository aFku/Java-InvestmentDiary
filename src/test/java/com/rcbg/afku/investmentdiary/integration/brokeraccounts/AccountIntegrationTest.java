//package com.rcbg.afku.investmentdiary.integration.brokeraccounts;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rcbg.afku.investmentdiary.brokeraccounts.controllers.AccountController;
//import com.rcbg.afku.investmentdiary.brokeraccounts.controllers.BrokerAccountControllerAdvisor;
//import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
//import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
//import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:integrationTests.properties")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class AccountIntegrationTest {
//
//    private MockMvc mvc;
//
//    @Autowired
//    public AccountRepository repo;
//
//    @Autowired
//    public AccountController accountController;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    public AccountManagementService managementService;
//
//    @Autowired
//    public  BrokerAccountControllerAdvisor controllerAdvisor;
//
//    private ResponseAccountDTO createAccount(String provider, String accountId){
//        Account account = new Account();
//        account.setAccountId(accountId);
//        account.setProvider(provider);
//        repo.save(account);
//        return new ResponseAccountDTO(account);
//    }
//
//    @BeforeEach
//    public void setUp() throws Exception{
//        this.mvc = MockMvcBuilders.standaloneSetup(accountController).setControllerAdvice(controllerAdvisor).build();
//    }
//
//    @Test
//    public void TestAccountCreationSuccessful() throws Exception{
//        // given
//        RequestAccountDTO payload = new RequestAccountDTO("XTB", "HDG547");
//
//        // when - then
//        mvc.perform(post("/${api.version}/accounts")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsBytes(payload))
//                ).andExpect(status().is(200))
//                .andExpect(jsonPath("data.provider").value("XTB"))
//                .andExpect(jsonPath("data.accountId").value("HDG547"));
//    }
//
//    @Test
//    public void TestAccountCreationEmptyProviderFailure() throws Exception{
//        // given
//        RequestAccountDTO payload = new RequestAccountDTO("", "HDG547");
//
//        // when - then
//        mvc.perform(post("/${api.version}/accounts")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsBytes(payload))
//                ).andExpect(status().is(400))
//                .andExpect(jsonPath("message").value("'provider' field cannot be empty"));
//    }
//
//    @Test
//    public void TestGetAccountThatExists() throws Exception {
//        // given
//        ResponseAccountDTO account1DTO = createAccount("XTB", "NFA638");
//        int id = account1DTO.getId();
//
//        // when - then
//        mvc.perform(get("/${api.version}/accounts/" + id))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("data.id").value(id))
//                .andExpect(jsonPath("data.accountId").value(account1DTO.getAccountId()))
//                .andExpect(jsonPath("data.provider").value(account1DTO.getProvider()));
//    }
//
//    @Test
//    public void TestGetAccountThatNotExists() throws Exception {
//        // given
//        int id = 102;
//
//        // when - then
//        mvc.perform(get("/${api.version}/accounts/" + id))
//                .andExpect(status().is(404))
//                .andExpect(jsonPath("message").value("Account with id: " + id + " not found"));
//    }
//
//    @Test
//    public void TestUpdateAccountThatNotExists() throws Exception {
//        // given
//        int id = 103;
//        RequestAccountDTO payload = new RequestAccountDTO("XTB", "HQU631");
//
//        // when - then
//        mvc.perform(put("/${api.version}/accounts/" + id)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsBytes(payload)))
//                .andExpect(status().is(404))
//                .andExpect(jsonPath("message").value("Account with ID: " + id + " does not exist"));
//    }
//
//    @Test
//    public void TestUpdateAccountThatExistsAllData() throws Exception {
//        // given
//        ResponseAccountDTO account1DTO = createAccount("XTB", "NFA638");
//        RequestAccountDTO payload = new RequestAccountDTO("Trader500", "HQU631");
//        int id = account1DTO.getId();
//
//        // when - then
//        mvc.perform(put("/${api.version}/accounts/" + id)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsBytes(payload)))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("data.id").value(id))
//                .andExpect(jsonPath("data.accountId").value(payload.getAccountId()))
//                .andExpect(jsonPath("data.provider").value(payload.getProvider()));
//
//        mvc.perform(get("/${api.version}/accounts/" + id))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("data.id").value(id))
//                .andExpect(jsonPath("data.accountId").value(payload.getAccountId()))
//                .andExpect(jsonPath("data.provider").value(payload.getProvider()));
//    }
//
//    @Test
//    public void TestUpdateAccountThatExistsSingleAttribute() throws Exception {
//        // given
//        ResponseAccountDTO account1DTO = createAccount("XTB", "NFA638");
//        RequestAccountDTO payload = new RequestAccountDTO("", "HQU631");
//        int id = account1DTO.getId();
//
//        // when - then
//        mvc.perform(put("/${api.version}/accounts/" + id)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsBytes(payload)))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("data.id").value(id))
//                .andExpect(jsonPath("data.accountId").value(payload.getAccountId()))
//                .andExpect(jsonPath("data.provider").value(account1DTO.getProvider()));
//
//        mvc.perform(get("/${api.version}/accounts/" + id))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("data.id").value(id))
//                .andExpect(jsonPath("data.accountId").value(payload.getAccountId()))
//                .andExpect(jsonPath("data.provider").value(account1DTO.getProvider()));
//
//    }
//
//    @Test
//    public void TestDeleteAccountThatNotExists() throws Exception {
//        // given
//        int id = 112;
//
//        // when - then
//        mvc.perform(delete("/${api.version}/accounts/" + id))
//                .andExpect(status().is(404))
//                .andExpect(jsonPath("message").value("Account with ID: " + id + " does not exist"));
//    }
//
//    @Test
//    public void TestDeleteAccountThatExists() throws Exception {
//        // given
//        ResponseAccountDTO account1DTO = createAccount("XTB", "NFA638");
//        int id = account1DTO.getId();
//
//        // when - then
//        mvc.perform(delete("/${api.version}/accounts/" + id))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("data.id").value(id))
//                .andExpect(jsonPath("data.type").value("account"))
//                .andExpect(jsonPath("data.deleted").value(true));
//
//        mvc.perform(get("/${api.version}/accounts/" + id))
//                .andExpect(status().is(404))
//                .andExpect(jsonPath("message").value("Account with id: " + id + " not found"));
//    }
//
//    @Test
//    public void TestGetAllAccounts() throws Exception {
//        // given
//        ResponseAccountDTO account1DTO = createAccount("XTB", "NFA638");
//        ResponseAccountDTO account2DTO = createAccount("Trader212", "FUB523");
//        ResponseAccountDTO account3DTO = createAccount("TMS", "OPA974");
//
//        // when - then
//        //mvc.perform(get("/${api.version}/accounts"))
//        //        .andExpect(status().is(200))
//        //        .andExpect(jsonPath("id").value(id))
//        //        .andExpect(jsonPath("type").value("account"))
//        //        .andExpect(jsonPath("deleted").value(true));
//
//
//    }
//    // Need Browse endpoints tests
//    // Get all
//    // Get by field
//    // Get byy datarange
//
//}

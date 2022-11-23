//package com.rcbg.afku.investmentdiary.unit.brokeraccounts;
//
//import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.RequestAccountDTO;
//import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
//import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
//import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountCreationException;
//import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
//import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountSearchException;
//import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
//import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
//import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.junit.jupiter.api.Assertions;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(MockitoExtension.class)
//public class AccountServicesTest {
//
//    @Mock
//    AccountRepository repo;
//
//    @InjectMocks
//    AccountBrowseService serviceBrowse;
//
//    @InjectMocks
//    AccountManagementService serviceManagement;
//
//    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//
//    void assertResponseAccountDTOWithAccountInstance(ResponseAccountDTO dto1, Account acc){
//        Assertions.assertEquals(dto1.getId(), acc.getId());
//        Assertions.assertEquals(dto1.getCreationDate(), acc.getCreationDate());
//        Assertions.assertEquals(dto1.getProvider(), acc.getProvider());
//        Assertions.assertEquals(dto1.getAccountId(), acc.getAccountId());
//    }
//
//    @Test
//    void testFindOneAccountByIdExist(){
//        Account testAccount = new Account();
//        testAccount.setAccountId("XCQ175");
//        testAccount.setProvider("testProvider");
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(testAccount));
//
//        ResponseAccountDTO dto = serviceBrowse.findOneAccountById(1);
//        assertResponseAccountDTOWithAccountInstance(dto, testAccount);
//    }
//
//    @Test
//    void testFindOneAccountByIdNotExist(){
//        Mockito.when(repo.findById(2)).thenReturn(Optional.empty());
//        Assertions.assertThrows(AccountNotFoundException.class, () -> serviceBrowse.findOneAccountById(2));
//    }
//
//    @Test
//    void testFindAllAccountsWithContent(){
//        List<Account> accountList = new ArrayList<>();
//        Account testAccount1 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider1");
//        accountList.add(testAccount1);
//        Account testAccount2 = new Account();
//        testAccount1.setAccountId("KJL785");
//        testAccount1.setProvider("testProvider2");
//        accountList.add(testAccount2);
//        Account testAccount3 = new Account();
//        testAccount1.setAccountId("WNS564");
//        testAccount1.setProvider("testProvider3");
//        accountList.add(testAccount3);
//        Mockito.when(repo.findAll()).thenReturn(accountList);
//
//        List<ResponseAccountDTO> accountDTOList = serviceBrowse.findAllAccounts();
//        Assertions.assertEquals(accountDTOList.size(), accountList.size());
//        accountDTOList.sort(Comparator.comparing(ResponseAccountDTO::getId));
//        accountDTOList.sort(Comparator.comparing(ResponseAccountDTO::getId));
//        for(int i=0; i < accountDTOList.size(); i++){
//            assertResponseAccountDTOWithAccountInstance(accountDTOList.get(i), accountList.get(i));
//        }
//    }
//
//    @Test
//    void testFindAllAccountsEmpty(){
//        Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
//        Assertions.assertEquals(serviceBrowse.findAllAccounts().size(), 0);
//    }
//
//    @Test
//    void testFindAllByFieldProviderExist(){
//        List<Account> accountList = new ArrayList<>();
//        Account testAccount1 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider1");
//        accountList.add(testAccount1);
//        Account testAccount2 = new Account();
//        testAccount1.setAccountId("KJL785");
//        testAccount1.setProvider("testProvider2");
//        accountList.add(testAccount2);
//        Mockito.when(repo.findAllByProvider("testProvider1")).thenReturn(accountList);
//
//        List<ResponseAccountDTO> accountDTOList = serviceBrowse.findAllByField("provider", "testProvider1");
//        Assertions.assertEquals(accountList.size(), accountDTOList.size());
//    }
//
//    @Test
//    void testFindAllByFieldProviderNotExist(){
//        Mockito.when(repo.findAllByProvider("testProvider2")).thenReturn(new ArrayList<>());
//        Assertions.assertEquals(serviceBrowse.findAllByField("provider", "testProvider2").size(), 0);
//    }
//
//    @Test
//    void testFindAllByFieldAccountIdExist(){
//        List<Account> accountList = new ArrayList<>();
//        Account testAccount1 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider1");
//        accountList.add(testAccount1);
//        Account testAccount2 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider2");
//        accountList.add(testAccount2);
//        Mockito.when(repo.findAllByAccountId("XCQ175")).thenReturn(accountList);
//
//        List<ResponseAccountDTO> accountDTOList = serviceBrowse.findAllByField("accountId", "XCQ175");
//        Assertions.assertEquals(accountList.size(), accountDTOList.size());
//    }
//
//    @Test
//    void testFindAllByFieldAccountIdNotExist(){
//        Mockito.when(repo.findAllByAccountId("XCQ175")).thenReturn(new ArrayList<>());
//        Assertions.assertEquals(serviceBrowse.findAllByField("accountId", "XCQ175").size(), 0);
//    }
//
//    @Test
//    void testFindAllByFieldCreationDateExist() throws ParseException {
//        String dateString = "2022-05-15";
//        Date newCreationDate = dateFormatter.parse(dateString);
//        List<Account> accountList = new ArrayList<>();
//        Account testAccount1 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider1");
//        ReflectionTestUtils.setField(testAccount1, "creationDate", new java.sql.Date(newCreationDate.getTime()));
//        accountList.add(testAccount1);
//        Account testAccount2 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider2");
//        ReflectionTestUtils.setField(testAccount2, "creationDate", new java.sql.Date(newCreationDate.getTime()));
//        accountList.add(testAccount2);
//        Mockito.when(repo.findAllByCreationDate(newCreationDate)).thenReturn(accountList);
//
//        List<ResponseAccountDTO> accountDTOList = serviceBrowse.findAllByField("creationDate", dateString);
//        Assertions.assertEquals(accountList.size(), accountDTOList.size());
//        for(ResponseAccountDTO dto : accountDTOList){
//            Assertions.assertEquals(dto.getCreationDate(), newCreationDate);
//        }
//
//    }
//
//    @Test
//    void testFindAllByFieldCreationDateNotExist() throws ParseException {
//        String dateString = "2022-05-15";
//        Date newCreationDate = dateFormatter.parse(dateString);
//        Mockito.when(repo.findAllByCreationDate(newCreationDate)).thenReturn(new ArrayList<>());
//        Assertions.assertEquals(serviceBrowse.findAllByField("creationDate", dateString).size(), 0);
//    }
//
//    @Test
//    void testFindAllByFieldCreationDateWrongFormat(){
//        String dateString = "15.05.2022";
//        Assertions.assertThrows(AccountSearchException.class, () -> serviceBrowse.findAllByField("creationDate", dateString));
//    }
//
//    @Test
//    void testFindAllByFieldInvalidField(){
//        Assertions.assertThrows(AccountSearchException.class, () -> serviceBrowse.findAllByField("invalidField", "testValue"));
//    }
//
//    @Test
//    void testFindAllByFieldEmptyField(){
//        Assertions.assertThrows(AccountSearchException.class, () -> serviceBrowse.findAllByField("", "testValue"));
//    }
//
//    @Test
//    void testFindAllCreatedBetweenWithContent() throws ParseException {
//        String startString = "2022-01-01";
//        String stopString = "2022-10-07";
//        Date startDate = dateFormatter.parse(startString);
//        Date stopDate = dateFormatter.parse(stopString);
//        List<Account> accountList = new ArrayList<>();
//        Account testAccount1 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider1");
//        ReflectionTestUtils.setField(testAccount1, "creationDate", new java.sql.Date(dateFormatter.parse("2022-05-15").getTime()));
//        accountList.add(testAccount1);
//        Account testAccount2 = new Account();
//        testAccount1.setAccountId("XCQ175");
//        testAccount1.setProvider("testProvider2");
//        ReflectionTestUtils.setField(testAccount1, "creationDate", new java.sql.Date(dateFormatter.parse("2022-10-07").getTime()));
//        accountList.add(testAccount2);
//        Mockito.when(repo.findAllByCreationDateBetween(startDate, stopDate)).thenReturn(accountList);
//        Assertions.assertEquals(serviceBrowse.findAllCreatedBetween(startDate, stopDate).size(), accountList.size());
//    }
//
//    @Test
//    void testFindAllCreatedBetweenEmpty() throws ParseException {
//        String startString = "2021-01-01";
//        String stopString = "2021-10-07";
//        Date startDate = dateFormatter.parse(startString);
//        Date stopDate = dateFormatter.parse(stopString);
//        Mockito.when(repo.findAllByCreationDateBetween(startDate, stopDate)).thenReturn(new ArrayList<>());
//        Assertions.assertEquals(serviceBrowse.findAllCreatedBetween(startDate, stopDate).size(), 0);
//    }
//
//    @Test
//    void testCreateAccountEmptyFields(){
//        RequestAccountDTO emptyProviderDTO = new RequestAccountDTO("", "XCQ175");
//        RequestAccountDTO emptyAccountIdDTO = new RequestAccountDTO("testProvider1", "");
//        Assertions.assertThrows(AccountCreationException.class, () -> serviceManagement.createAccount(emptyProviderDTO));
//        Assertions.assertThrows(AccountCreationException.class, () -> serviceManagement.createAccount(emptyAccountIdDTO));
//    }
//
//    @Test
//    void testCreateAccountSuccessful(){
//        RequestAccountDTO createRequestDTO = new RequestAccountDTO("testProvider1", "XCQ175");
//        Mockito.when(repo.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
//        Mockito.when(repo.existsById(any(Integer.class))).thenReturn(true);
//        ResponseAccountDTO createdAccountDTO = serviceManagement.createAccount(createRequestDTO);
//        Assertions.assertEquals(createRequestDTO.getAccountId(), createdAccountDTO.getAccountId());
//        Assertions.assertEquals(createRequestDTO.getProvider(), createdAccountDTO.getProvider());
//    }
//
//    @Test
//    void testDeleteAccountThatNotExist(){
//        int id = 10;
//        Mockito.when(repo.existsById(id)).thenReturn(false);
//        Assertions.assertThrows(AccountNotFoundException.class, () -> serviceManagement.deleteAccount(id));
//    }
//
//    @Test
//    void testDeleteAccountSuccessful(){
//        int id = 20;
//        Mockito.when(repo.existsById(id)).thenReturn(true, false);
//        Assertions.assertTrue(serviceManagement.deleteAccount(id));
//    }
//
//    @Test
//    void testUpdateAccountThatNotExist(){
//        int id = 30;
//        Mockito.when(repo.findById(id)).thenReturn(Optional.empty());
//        Assertions.assertThrows(AccountNotFoundException.class, () -> serviceManagement.updateAccount(id, new RequestAccountDTO("", "")));
//    }
//
//    @Test
//    void testUpdateAccountSuccessfulFully(){
//        int id = 31;
//        Account originalAccount = new Account();
//        originalAccount.setAccountId("XGD105");
//        originalAccount.setProvider("testProvider1");
//        RequestAccountDTO requestDTO = new RequestAccountDTO("testProvider2", "KLQ743");
//        Mockito.when(repo.findById(id)).thenReturn(Optional.of(originalAccount));
//        Mockito.when(repo.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
//        ResponseAccountDTO responseDTO = serviceManagement.updateAccount(id, requestDTO);
//        Assertions.assertEquals(responseDTO.getId(), originalAccount.getId());
//        Assertions.assertEquals(responseDTO.getAccountId(), "KLQ743");
//        Assertions.assertEquals(responseDTO.getProvider(), "testProvider2");
//    }
//
//    @Test
//    void testUpdateAccountSuccessfulEmptyRequest(){
//        int id = 32;
//        Account originalAccount = new Account();
//        originalAccount.setAccountId("XGD105");
//        originalAccount.setProvider("testProvider1");
//        RequestAccountDTO requestDTO = new RequestAccountDTO("", "");
//        Mockito.when(repo.findById(id)).thenReturn(Optional.of(originalAccount));
//        Mockito.when(repo.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
//        ResponseAccountDTO responseDTO = serviceManagement.updateAccount(id, requestDTO);
//        Assertions.assertEquals(responseDTO.getId(), originalAccount.getId());
//        Assertions.assertEquals(responseDTO.getAccountId(), originalAccount.getAccountId());
//        Assertions.assertEquals(responseDTO.getProvider(), originalAccount.getProvider());
//    }
//
//    @Test
//    void testUpdateAccountSuccessfulPartially(){
//        int id = 33;
//        Account originalAccount = new Account();
//        originalAccount.setAccountId("XGD105");
//        originalAccount.setProvider("testProvider1");
//        RequestAccountDTO requestDTO = new RequestAccountDTO("", "WFP481");
//        Mockito.when(repo.findById(id)).thenReturn(Optional.of(originalAccount));
//        Mockito.when(repo.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
//        ResponseAccountDTO responseDTO = serviceManagement.updateAccount(id, requestDTO);
//        Assertions.assertEquals(responseDTO.getId(), originalAccount.getId());
//        Assertions.assertEquals(responseDTO.getAccountId(), "WFP481");
//        Assertions.assertEquals(responseDTO.getProvider(), originalAccount.getProvider());
//    }
//}

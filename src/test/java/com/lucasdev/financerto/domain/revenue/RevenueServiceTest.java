package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RevenueServiceTest {

    @InjectMocks
    private RevenueService revenueService;

    @Mock
    private RevenueRepository revenueRepository;

    @Mock
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    @Mock
    private User currentUser;

    @Mock
    private User otherUser;

    @Mock
    private Revenue revenue;

    @Mock
    private Authentication authentication;

    @Captor
    private ArgumentCaptor<Revenue> argumentCaptor;

    @Mock
    private Page<Revenue> listRevenue;

    @Mock
    private RevenueUpdateDTO revenueUpdateDTO;

    @Test
    void shouldRegisterRevenue() {
        RevenueDTO revenueRegister = new RevenueDTO(1000.0, "Description", LocalDate.now().plusDays(2), Methods.CHEQUE, CategoryRevenue.OUTRA);

        revenueService.registerRevenue(revenueRegister, authentication);

        BDDMockito.then(revenueRepository).should().save(argumentCaptor.capture());
        Revenue revenueExpected = new Revenue(currentUser, revenueRegister);
        Assertions.assertEquals(revenueExpected, argumentCaptor.getValue());
    }

    @Test
    void shouldFindRevenueById() {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.getReferenceById(idRevenue)).willReturn(revenue);
        BDDMockito.given(revenue.getUser()).willReturn(currentUser);

        revenueService.findRevenueById(idRevenue, authentication);

        BDDMockito.then(revenueRepository).should().getReferenceById(idRevenue);
    }

    @Test
    void shouldNoFindRevenueByIdThatBelongsToAnotherUser() {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.getReferenceById(idRevenue)).willReturn(revenue);
        BDDMockito.given(revenue.getUser()).willReturn(otherUser);

        Assertions.assertThrows(EntityNotFoundException.class, () -> revenueService.findRevenueById(idRevenue, authentication));
    }

    @Test
    void shouldListRevenuesPerUser() {
        //BDDMockito.given(currentUser.getId()).willReturn("1");
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.findAllByUserId(currentUser.getId(), Pageable.unpaged())).willReturn(listRevenue);

        revenueService.listRevenues(Pageable.unpaged(), authentication);

        BDDMockito.then(revenueRepository).should().findAllByUserId(currentUser.getId(), Pageable.unpaged());
    }

    @Test
    void shouldUpdateRevenue () {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.getReferenceById(idRevenue)).willReturn(revenue);
        BDDMockito.given(revenue.getUser()).willReturn(currentUser);
        BDDMockito.given(revenueUpdateDTO.id()).willReturn(idRevenue);

        revenueService.updateRevenue(revenueUpdateDTO, authentication);

        BDDMockito.then(revenue).should().update(revenueUpdateDTO);
    }

    @Test
    void shouldNotUpdateRevenueBelongsToAnotherUser () {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.getReferenceById(idRevenue)).willReturn(revenue);
        BDDMockito.given(revenue.getUser()).willReturn(otherUser);
        BDDMockito.given(revenueUpdateDTO.id()).willReturn(idRevenue);

        Assertions.assertThrows(EntityNotFoundException.class, () -> revenueService.updateRevenue(revenueUpdateDTO, authentication));
    }

    @Test
    void shouldDeleteRevenue() {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.findById(idRevenue)).willReturn(Optional.of(revenue));
        BDDMockito.given(revenue.getUser()).willReturn(currentUser);

        revenueService.deleteRevenue(idRevenue, authentication);

        BDDMockito.then(revenueRepository).should().deleteById(idRevenue);
    }

    @Test
    void shouldNotDeleteWhenRevenueIsNull() {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.findById(idRevenue)).willReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> revenueService.deleteRevenue(idRevenue, authentication));
    }

    @Test
    void shouldNotDeleteWhenRevenueBelongsToAnotherUser() {
        String idRevenue = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(currentUser);
        BDDMockito.given(revenueRepository.findById(idRevenue)).willReturn(Optional.of(revenue));
        BDDMockito.given(revenue.getUser()).willReturn(otherUser);

        Assertions.assertThrows(EntityNotFoundException.class, () -> revenueService.deleteRevenue(idRevenue, authentication));
    }
}
package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.RequestRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.*;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import com.containerdepot.metcon.model.enums.UserRole;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RequestServiceImplTest {
    private RequestServiceImpl toTest;
    @Captor
    private ArgumentCaptor<Request> requestCaptor;
    @Mock
    private RequestRepository mockRequestRepository;
    @Mock
    private UserRepository mockUserRepository;
    private UserEntity testUserEntity;
    private Request testRequest;
    private RequestAddDto testRequestAddDto;

    private Company testCompany = new Company("Test_0", "Тест_0",
                                      "BG154689941", "Varna", "Address 0",
                                      "test0@mail", "+35952 150 000");
    @BeforeEach
    void setUp() {
        toTest = new RequestServiceImpl(new ModelMapper(), mockRequestRepository, mockUserRepository);
        testUserEntity = new UserEntity("test", "test123456", Set.of(new Role(UserRole.ADMIN)),
                "test", "test", "test@mail", new Company());
        testRequest = new Request();
        testRequest.setType(RequestEnum.RECEIVE);
        testRequest.setContainerNumber("TEST0000001");
        testRequest.setContainerType(ContainerIsoType.FORTY_FT_HC);
        testRequest.setTruck("B0000HH");
        testRequest.setCompany(testCompany);

        testRequestAddDto = new RequestAddDto();
        testRequestAddDto.setType(RequestEnum.RECEIVE);
        testRequestAddDto.setContainerNumber("TEST0000001");
        testRequestAddDto.setContainerType(ContainerIsoType.FORTY_FT_HC);
        testRequestAddDto.setTruck("B0000HH");
    }
    @Test
    void testAddRequestSuccess() {
        when(this.mockRequestRepository.existsByTypeAndContainerNumber(this.testRequestAddDto.getType(), this.testRequestAddDto.getContainerNumber()))
                .thenReturn(false);
        when(this.mockUserRepository.findByUsername(testUserEntity.getUsername())).thenReturn(Optional.of(testUserEntity));

        this.toTest.add(this.testRequestAddDto, testUserEntity.getUsername());

        verify(this.mockRequestRepository).save(this.requestCaptor.capture());

        Request actualSavedRequest = this.requestCaptor.getValue();

        Assertions.assertNotNull(actualSavedRequest);
        Assertions.assertEquals(this.testRequestAddDto.getType(), actualSavedRequest.getType());
        Assertions.assertEquals(this.testRequestAddDto.getContainerNumber(), actualSavedRequest.getContainerNumber());
        Assertions.assertEquals(this.testRequestAddDto.getContainerType(), actualSavedRequest.getContainerType());
        Assertions.assertEquals(this.testRequestAddDto.getTruck(), actualSavedRequest.getTruck());
    }
    @Test
    void testEditRequestSuccess() {
        when(this.mockRequestRepository.findById(0L)).thenReturn(Optional.of(testRequest));

        this.toTest.edit(0L, testRequestAddDto);

        verify(this.mockRequestRepository).save(this.requestCaptor.capture());

        Request actualSavedRequest = this.requestCaptor.getValue();

        Assertions.assertNotNull(actualSavedRequest);
        Assertions.assertEquals(this.testRequestAddDto.getType(), actualSavedRequest.getType());
        Assertions.assertEquals(this.testRequestAddDto.getContainerNumber(), actualSavedRequest.getContainerNumber());
        Assertions.assertEquals(this.testRequestAddDto.getContainerType(), actualSavedRequest.getContainerType());
        Assertions.assertEquals(this.testRequestAddDto.getTruck(), actualSavedRequest.getTruck());
    }
    @Test
    void testDeleteCompanyDeletes() {
        Long id = 100L;
        when(this.mockRequestRepository.existsById(id)).thenReturn(true);
        toTest.delete(id);
        verify(this.mockRequestRepository, times(1)).existsById(id);
        verify(this.mockRequestRepository, times(1)).deleteById(id);
    }
}

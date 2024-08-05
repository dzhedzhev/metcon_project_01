package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.ContainerRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;
import com.containerdepot.metcon.service.dtos.imports.ContainerEditDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ContainerServiceImplTest {
    private ContainerServiceImpl toTest;
    @Captor
    private ArgumentCaptor<Container> containerCaptor;

    @Mock
    private CompanyRepository mockCompanyRepository;
    @Mock
    private ContainerRepository mockContainerRepository;
    private Company testCompany;
    private Container testContainer;
    private ContainerAddDto testContainerAddDto0;
    private ContainerAddDto testContainerAddDto1;
    @BeforeEach
    void setUp() {
        toTest = new ContainerServiceImpl(
                new ModelMapper(),
                mockContainerRepository,
                mockCompanyRepository);
        testCompany = new Company("Test_0", "Тест_0",
                "BG154689941", "Varna", "Address 0",
                "test0@mail", "+35952 150 000");
        testContainer = new Container( "TEST4000000",
                ContainerIsoType.FORTY_FT_HC, false, testCompany,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                LocalDateTime.of(2024, 1, 1, 18, 0, 0),
                "B0000HH", "B0000HH"
        );
        testContainerAddDto0 = new ContainerAddDto( "TEST4000000",
                ContainerIsoType.FORTY_FT_HC, false, "Test_0",
                LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                "B0000HH",
                LocalDateTime.of(2024, 1, 1, 18, 0, 0),
                "B0000HH"
        );
        testContainerAddDto1 = new ContainerAddDto("TEST4000001",
                ContainerIsoType.FORTY_FT_HC, true, "Test_0",
                LocalDateTime.of(2024, 1, 2, 10, 0, 0),
                "B0001HH",
                LocalDateTime.of(2024, 1, 2, 18, 0, 0),
                "B0001HH"
        );
    }
    @Test
    void testAddContainerSuccess() {
        when(this.mockContainerRepository.findByNumberAndReleasedNotNull(this.testContainerAddDto0.getNumber())).thenReturn(Optional.empty());
        when(this.mockCompanyRepository.findByNameEn(this.testContainerAddDto0.getOwner())).thenReturn(Optional.of(this.testCompany));

        this.toTest.add(this.testContainerAddDto0);

        verify(this.mockContainerRepository).save(containerCaptor.capture());

        Container actualSavedContainer = this.containerCaptor.getValue();

        Assertions.assertNotNull(actualSavedContainer);
        Assertions.assertEquals(this.testContainerAddDto0.getNumber(), actualSavedContainer.getNumber());
        Assertions.assertEquals(this.testContainerAddDto0.getType(), actualSavedContainer.getType());
        Assertions.assertEquals(this.testContainerAddDto0.getDamaged(), actualSavedContainer.getDamaged());
        Assertions.assertEquals(this.testContainerAddDto0.getOwner(), actualSavedContainer.getOwner().getNameEn());
        Assertions.assertEquals(this.testContainerAddDto0.getReceived(), actualSavedContainer.getReceived());
        Assertions.assertEquals(this.testContainerAddDto0.getReceivedByTruck(), actualSavedContainer.getReceivedByTruck());
        Assertions.assertEquals(this.testContainerAddDto0.getReleased(), actualSavedContainer.getReleased());
        Assertions.assertEquals(this.testContainerAddDto0.getReleasedToTruck(), actualSavedContainer.getReleasedToTruck());
    }
    @Test
    void testEditContainerSuccess() {
        ContainerEditDto testContainerEditDto = new ContainerEditDto();
        testContainerEditDto.setId(1L);
        testContainerEditDto.setNumber("TEST4000002");
        testContainerEditDto.setType(ContainerIsoType.FORTY_FT_HC);
        testContainerEditDto.setDamaged(true);
        testContainerEditDto.setOwner("Test_0");
        testContainerEditDto.setReceived(LocalDateTime.of(2024, 1, 1, 15,0,0));
        testContainerEditDto.setReceivedByTruck("B0002HH");
        testContainerEditDto.setReleased(LocalDateTime.of(2024, 1, 2, 15,0,0));
        testContainerEditDto.setReleasedToTruck("B0002HH");


        when(this.mockContainerRepository.findById(testContainerEditDto.getId())).thenReturn(Optional.of(this.testContainer));
        when(this.mockCompanyRepository.findByNameEn(testContainerEditDto.getOwner())).thenReturn(Optional.of(this.testCompany));

        this.toTest.edit(testContainerEditDto);

        verify(this.mockContainerRepository).save(containerCaptor.capture());

        Container actualSavedContainer = this.containerCaptor.getValue();

        Assertions.assertNotNull(actualSavedContainer);
        Assertions.assertEquals(testContainerEditDto.getId(), actualSavedContainer.getId());
        Assertions.assertEquals(testContainerEditDto.getNumber(), actualSavedContainer.getNumber());
        Assertions.assertEquals(testContainerEditDto.getType(), actualSavedContainer.getType());
        Assertions.assertEquals(testContainerEditDto.getDamaged(), actualSavedContainer.getDamaged());
        Assertions.assertEquals(testContainerEditDto.getOwner(), actualSavedContainer.getOwner().getNameEn());
        Assertions.assertEquals(testContainerEditDto.getReceived(), actualSavedContainer.getReceived());
        Assertions.assertEquals(testContainerEditDto.getReceivedByTruck(), actualSavedContainer.getReceivedByTruck());
        Assertions.assertEquals(testContainerEditDto.getReleased(), actualSavedContainer.getReleased());
        Assertions.assertEquals(testContainerEditDto.getReleasedToTruck(), actualSavedContainer.getReleasedToTruck());
    }

    @Test
    void testDeleteCompanyDeletes() {
        Long id = 100L;
        when(this.mockContainerRepository.existsById(id)).thenReturn(true);
        toTest.delete(id);
        verify(this.mockContainerRepository, times(1)).existsById(id);
        verify(this.mockContainerRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAllByCompanyIdReturnsCorrectList() {
        this.testCompany.setId(1L);
        this.testContainer.setId(0L);
        Container testContainer1 = new Container( "TEST4000001",
                ContainerIsoType.FORTY_FT_HC, true, testCompany,
                LocalDateTime.of(2024, 1, 2, 10, 0, 0),
                LocalDateTime.of(2024, 1, 2, 18, 0, 0),
                "B0001HH",
                "B0001HH");
        testContainer1.setId(1L);
        this.testContainerAddDto0.setId(0L);
        this.testContainerAddDto1.setId(1L);

        when(this.mockContainerRepository.findAllByOwnerId(testCompany.getId())).thenReturn(List.of(testContainer, testContainer1));

        List<ContainerAddDto> expectedContainers = List.of(this.testContainerAddDto0, this.testContainerAddDto1);
        List<ContainerAddDto> actualContainers = toTest.findAllByCompanyId(testCompany.getId());

        Assertions.assertEquals(expectedContainers.size(), actualContainers.size());
        Assertions.assertEquals(expectedContainers.get(0).getId(), actualContainers.get(0).getId());
        Assertions.assertEquals(expectedContainers.get(0).getNumber(), actualContainers.get(0).getNumber());
        Assertions.assertEquals(expectedContainers.get(0).getType(), actualContainers.get(0).getType());
        Assertions.assertEquals(expectedContainers.get(0).getDamaged(), actualContainers.get(0).getDamaged());
        Assertions.assertEquals(expectedContainers.get(0).getOwner(), actualContainers.get(0).getOwner());
        Assertions.assertEquals(expectedContainers.get(0).getReceived(), actualContainers.get(0).getReceived());
        Assertions.assertEquals(expectedContainers.get(0).getReceivedByTruck(), actualContainers.get(0).getReceivedByTruck());
        Assertions.assertEquals(expectedContainers.get(0).getReleased(), actualContainers.get(0).getReleased());
        Assertions.assertEquals(expectedContainers.get(0).getReleasedToTruck(), actualContainers.get(0).getReleasedToTruck());

        Assertions.assertEquals(expectedContainers.get(1).getId(), actualContainers.get(1).getId());
        Assertions.assertEquals(expectedContainers.get(1).getNumber(), actualContainers.get(1).getNumber());
        Assertions.assertEquals(expectedContainers.get(1).getType(), actualContainers.get(1).getType());
        Assertions.assertEquals(expectedContainers.get(1).getDamaged(), actualContainers.get(1).getDamaged());
        Assertions.assertEquals(expectedContainers.get(1).getOwner(), actualContainers.get(1).getOwner());
        Assertions.assertEquals(expectedContainers.get(1).getReceived(), actualContainers.get(1).getReceived());
        Assertions.assertEquals(expectedContainers.get(1).getReceivedByTruck(), actualContainers.get(1).getReceivedByTruck());
        Assertions.assertEquals(expectedContainers.get(1).getReleased(), actualContainers.get(1).getReleased());
        Assertions.assertEquals(expectedContainers.get(1).getReleasedToTruck(), actualContainers.get(1).getReleasedToTruck());
    }
}

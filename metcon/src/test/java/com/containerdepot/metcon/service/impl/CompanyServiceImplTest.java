package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.dtos.exports.CompanyDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyAddDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyEditDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {

    private CompanyServiceImpl toTest;
    @Captor
    private ArgumentCaptor<Company> companyCaptor;

    @Mock
    private CompanyRepository mockCompanyRepository;
    private Company testCompany0 = new Company("Test_0", "МЕТ",
            "BG154689941", "Varna", "Address 0",
            "test0@mail", "+35952 150 000");
    private Company testCompany1 = new Company("Test_1", "CMA",
            "BG154688899", "Burgas", "Address 1",
            "test1@mail", "+35952 150 001");

    @BeforeEach
    void setUp() {
        toTest = new CompanyServiceImpl(mockCompanyRepository, new ModelMapper());
    }
    @Test
    void allCompaniesNamesReturnsCorrectList() {

        when(this.mockCompanyRepository.findAll()).thenReturn(List.of(this.testCompany0, this.testCompany1));

        List<String> actualCompaniesNames = this.toTest.allCompaniesNames();
        List<String> expectedCompaniesNames = List.of("Test_0", "Test_1");

        Assertions.assertEquals(expectedCompaniesNames.size(), actualCompaniesNames.size());
        Assertions.assertEquals(expectedCompaniesNames.get(0), actualCompaniesNames.get(0));
        Assertions.assertEquals(expectedCompaniesNames.get(1), actualCompaniesNames.get(1));
        verify(this.mockCompanyRepository, times(1)).findAll();
    }
    @Test
    void addCompanySuccess() {
        CompanyAddDto companyAddDto = new CompanyAddDto();
        companyAddDto.setNameEn("Test");
        companyAddDto.setNameBg("Тест");
        companyAddDto.setVatNumber("BG123456789");
        companyAddDto.setCity("Varna");
        companyAddDto.setAddress("Test address");
        companyAddDto.setEmail("test@mail");
        companyAddDto.setPhoneNumber("+35952 000 001");

        toTest.add(companyAddDto);

        verify(this.mockCompanyRepository).save(companyCaptor.capture());

        Company actualSavedCompany = this.companyCaptor.getValue();

        Assertions.assertNotNull(actualSavedCompany);
        Assertions.assertEquals(companyAddDto.getNameEn(), actualSavedCompany.getNameEn());
        Assertions.assertEquals(companyAddDto.getNameBg(), actualSavedCompany.getNameBg());
        Assertions.assertEquals(companyAddDto.getVatNumber(), actualSavedCompany.getVatNumber());
        Assertions.assertEquals(companyAddDto.getCity(), actualSavedCompany.getCity());
        Assertions.assertEquals(companyAddDto.getAddress(), actualSavedCompany.getAddress());
        Assertions.assertEquals(companyAddDto.getEmail(), actualSavedCompany.getEmail());
        Assertions.assertEquals(companyAddDto.getPhoneNumber(), actualSavedCompany.getPhoneNumber());
    }
    @Test
    void allCompaniesReturnsCorrectList() {
        this.testCompany0.setId(0L);
        this.testCompany1.setId(1L);

        when(this.mockCompanyRepository.findAll()).thenReturn(List.of(this.testCompany0, this.testCompany1));

        CompanyDto companyDto = new CompanyDto(0L, "Test_0", "МЕТ",
                "BG154689941", "Varna", "Address 0",
                "test0@mail", "+35952 150 000");
        CompanyDto companyDto1 = new CompanyDto(1L,"Test_1", "CMA",
                "BG154688899", "Burgas", "Address 1",
                "test1@mail", "+35952 150 001");


        List<CompanyDto> expectedCompanyList = List.of(companyDto, companyDto1);

        List<CompanyDto> actualCompanyList = toTest.allCompanies();

        Assertions.assertEquals(expectedCompanyList.size(), actualCompanyList.size());
        Assertions.assertEquals(expectedCompanyList.get(0).getId(), actualCompanyList.get(0).getId());
        Assertions.assertEquals(expectedCompanyList.get(0).getNameEn(), actualCompanyList.get(0).getNameEn());
        Assertions.assertEquals(expectedCompanyList.get(0).getNameBg(), actualCompanyList.get(0).getNameBg());
        Assertions.assertEquals(expectedCompanyList.get(0).getVatNumber(), actualCompanyList.get(0).getVatNumber());
        Assertions.assertEquals(expectedCompanyList.get(0).getCity(), actualCompanyList.get(0).getCity());
        Assertions.assertEquals(expectedCompanyList.get(0).getAddress(), actualCompanyList.get(0).getAddress());
        Assertions.assertEquals(expectedCompanyList.get(0).getEmail(), actualCompanyList.get(0).getEmail());
        Assertions.assertEquals(expectedCompanyList.get(0).getPhoneNumber(), actualCompanyList.get(0).getPhoneNumber());

        Assertions.assertEquals(expectedCompanyList.get(1).getId(), actualCompanyList.get(1).getId());
        Assertions.assertEquals(expectedCompanyList.get(1).getNameEn(), actualCompanyList.get(1).getNameEn());
        Assertions.assertEquals(expectedCompanyList.get(1).getNameBg(), actualCompanyList.get(1).getNameBg());
        Assertions.assertEquals(expectedCompanyList.get(1).getVatNumber(), actualCompanyList.get(1).getVatNumber());
        Assertions.assertEquals(expectedCompanyList.get(1).getCity(), actualCompanyList.get(1).getCity());
        Assertions.assertEquals(expectedCompanyList.get(1).getAddress(), actualCompanyList.get(1).getAddress());
        Assertions.assertEquals(expectedCompanyList.get(1).getEmail(), actualCompanyList.get(1).getEmail());
        Assertions.assertEquals(expectedCompanyList.get(1).getPhoneNumber(), actualCompanyList.get(1).getPhoneNumber());
        verify(this.mockCompanyRepository, times(1)).findAll();

    }
    @Test
    void editCompanySuccess() {
        CompanyEditDto companyEditDto = new CompanyEditDto();
        companyEditDto.setId(1L);
        companyEditDto.setNameEn("Test");
        companyEditDto.setNameBg("Тест");
        companyEditDto.setVatNumber("BG123456789");
        companyEditDto.setCity("Varna");
        companyEditDto.setAddress("Test address");
        companyEditDto.setEmail("test@mail");
        companyEditDto.setPhoneNumber("+35952 000 001");

        Company company = new Company();
        company.setId(1L);
        company.setNameEn("TestOriginal");
        company.setNameBg("Тест");
        company.setVatNumber("BG123456789");
        company.setCity("Varna");
        company.setAddress("Test address");
        company.setEmail("test@mail");
        company.setPhoneNumber("+35952 000 001");

        when(this.mockCompanyRepository.findById(1L)).thenReturn(Optional.of(company));

        toTest.edit(companyEditDto);

        verify(this.mockCompanyRepository).save(companyCaptor.capture());

        Company actualSavedCompany = this.companyCaptor.getValue();

        Assertions.assertNotNull(actualSavedCompany);
        Assertions.assertEquals(companyEditDto.getNameEn(), actualSavedCompany.getNameEn());
        Assertions.assertEquals(companyEditDto.getNameBg(), actualSavedCompany.getNameBg());
        Assertions.assertEquals(companyEditDto.getVatNumber(), actualSavedCompany.getVatNumber());
        Assertions.assertEquals(companyEditDto.getCity(), actualSavedCompany.getCity());
        Assertions.assertEquals(companyEditDto.getAddress(), actualSavedCompany.getAddress());
        Assertions.assertEquals(companyEditDto.getEmail(), actualSavedCompany.getEmail());
        Assertions.assertEquals(companyEditDto.getPhoneNumber(), actualSavedCompany.getPhoneNumber());
    }
    @Test
    void testDeleteCompanyDeletes() {
        Long id = 100L;
        when(this.mockCompanyRepository.existsById(id)).thenReturn(true);
        toTest.delete(id);
        verify(this.mockCompanyRepository, times(1)).existsById(id);
        verify(this.mockCompanyRepository, times(1)).deleteById(id);
    }


}

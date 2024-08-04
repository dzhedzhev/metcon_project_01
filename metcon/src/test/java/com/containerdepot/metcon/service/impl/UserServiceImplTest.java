package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.RoleRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Role;
import com.containerdepot.metcon.model.entities.UserEntity;
import com.containerdepot.metcon.model.enums.UserRole;
import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserServiceImpl toTest;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private CompanyRepository mockCompanyRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private RoleRepository mockRoleRepository;

    @BeforeEach
    void setUp() {

        toTest = new UserServiceImpl(mockUserRepository, new ModelMapper(), mockCompanyRepository, 
                mockPasswordEncoder, mockRoleRepository);
    }

    @Test
    void testUserSignUpSuccess() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("Test");
        signUpDto.setPassword("123456789");
        signUpDto.setConfirmPassword("123456789");
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setEmail("Test@mail");
        signUpDto.setCompany("Test COMPANY");
        signUpDto.setRoles(Set.of(UserRole.ADMIN, UserRole.USER));

        Company company = new Company("Test COMPANY", "МЕТ", "BG154689941", "Varna", "Ivan Shishman 30 str.", "met-depot@metcontainer.com", "+35952 150 148");

        Role role = new Role(UserRole.USER);
        Role role1 = new Role(UserRole.ADMIN);

        when(mockPasswordEncoder.encode(signUpDto.getPassword()))
                .thenReturn(signUpDto.getPassword() + "Test");

        when(mockCompanyRepository.findByNameEn("Test COMPANY"))
                .thenReturn(Optional.of(company));
        when(mockRoleRepository.findByRole(UserRole.ADMIN)).thenReturn(role1);
        when(mockRoleRepository.findByRole(UserRole.USER)).thenReturn(role);

        toTest.signUp(signUpDto);

        verify(mockUserRepository).save(userEntityCaptor.capture());

        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        Assertions.assertEquals(signUpDto.getUsername(), actualSavedEntity.getUsername());
        Assertions.assertEquals(signUpDto.getPassword() + "Test", actualSavedEntity.getPassword());
        Assertions.assertEquals(signUpDto.getFirstName(), actualSavedEntity.getFirstName());
        Assertions.assertEquals(signUpDto.getLastName(), actualSavedEntity.getLastName());
        Assertions.assertEquals(signUpDto.getEmail(), actualSavedEntity.getEmail());
        Assertions.assertEquals(signUpDto.getCompany(), actualSavedEntity.getCompany().getNameEn());
        Assertions.assertEquals(signUpDto.getRoles().size(), actualSavedEntity.getRoles().size());

    }
    @Test
    void testUserSignUpThrowsWhenPasswordsNotMatch() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("Test");
        signUpDto.setPassword("123456789");
        signUpDto.setConfirmPassword("0000000000");
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setEmail("Test@mail");
        signUpDto.setCompany("Test COMPANY");
        signUpDto.setRoles(Set.of(UserRole.ADMIN, UserRole.USER));

        Assertions.assertThrows(IllegalArgumentException.class, () -> toTest.signUp(signUpDto), "Passwords do not match! Please try again!");
    }
    @Test
    void testUserSignUpThrowsWhenUsernameOrEmailExist() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("Test");
        signUpDto.setPassword("123456789");
        signUpDto.setConfirmPassword("123456789");
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setEmail("Test@mail");
        signUpDto.setCompany("Test COMPANY");
        signUpDto.setRoles(Set.of(UserRole.ADMIN, UserRole.USER));

        when(mockUserRepository.existsByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail())).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> toTest.signUp(signUpDto), "There is registered user with this username! Please try again!");
    }

    @Test
    void testUserSignUpThrowsWhenNoSuchCompany() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("Test");
        signUpDto.setPassword("123456789");
        signUpDto.setConfirmPassword("123456789");
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setEmail("Test@mail");
        signUpDto.setCompany("Test COMPANY");
        signUpDto.setRoles(Set.of(UserRole.ADMIN, UserRole.USER));

        when(mockCompanyRepository.findByNameEn("Test COMPANY"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> toTest.signUp(signUpDto), "There is registered user with this username! Please try again!");
    }
    @Test
    void testDeleteUserThrowsWhenNoUser() {
        Long id = 100L;

        when(mockUserRepository.existsById(id)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> toTest.delete(id), "There is registered user with this username! Please try again!");
        verify(mockUserRepository, times(1)).existsById(id);
    }
    @Test
    void testDeleteUserDeletes() {
        Long id = 100L;
        when(mockUserRepository.existsById(id)).thenReturn(true);
        toTest.delete(id);
        verify(mockUserRepository, times(1)).existsById(id);
        verify(mockUserRepository, times(1)).deleteById(id);
    }
}

package org.softuni.mobilele.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.model.entity.UserRoleEntity;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.service.MobileleUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MobileleUserDetailsServiceTest {

  private MobileleUserDetailsService serviceToTest;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void setUp() {
    serviceToTest = new MobileleUserDetailsService(
      mockUserRepository
    );
  }

  @Test
  void testUserNotFound() {
    assertThrows(
        UsernameNotFoundException.class,
        () -> serviceToTest.loadUserByUsername("pesho@softuni.bg")
    );
  }

  @Test
  void testUserFoundException() {
    // Arrange
    UserEntity testUserEntity = createTestUser();
    when(mockUserRepository.findByEmail(testUserEntity.getEmail()))
        .thenReturn(Optional.of(testUserEntity));

    // Act
    UserDetails userDetails =
        serviceToTest.loadUserByUsername(testUserEntity.getEmail());

    // Assert
    assertNotNull(userDetails);
    assertEquals(
        testUserEntity.getEmail(),
        userDetails.getUsername(),
        "Username is not mapped to email.");

    assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
    assertEquals(2, userDetails.getAuthorities().size());
    assertTrue(
        containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
        "The user is not admin");
    assertTrue(
        containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER),
        "The user is not user");
  }

  private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
   return userDetails
        .getAuthorities()
        .stream()
        .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
  }

  private static UserEntity createTestUser() {
    UserEntity user = new UserEntity();

    user.setFirstName("firstName");
    user.setLastName("lastName");
    user.setEmail("pesho@softuni.bg");
    user.setActive(false);
    user.setPassword("topsecret");

    UserRoleEntity adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    UserRoleEntity userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);

    user.setRoles(List.of(adminRole, userRole));

    return user;
  }
}

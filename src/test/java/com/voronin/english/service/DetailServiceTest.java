package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.domain.User;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * DetailService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class DetailServiceTest {

    /**
     * Mock UserService.
     */
    private UserService userService = mock(UserService.class);

    /**
     * The class object under test.
     */
    private DetailService detailService = new DetailService(userService);


    /**
     * When you call the getUserByEmail method with a user who is not in
     * the database, should throw a DisabledException.
     */
    @Test(expected = DisabledException.class)
    public void whenUserEqualsNullShouldThrowException() {
        when(userService.getUserByEmail("test")).thenReturn(null);

        detailService.loadUserByUsername("test");
    }

    /**
     * When you call the getUserByEmail method with a user who is in the database, should return user.
     */
    @Test
    public void whenValidUserShouldReturnUser() {
        Role role = new Role();
        role.setRole("user");
        User user = new User("test@test.ru", "password", new HashSet<>(Lists.newArrayList(role)));
        user.setActive(true);
        when(userService.getUserByEmail("test@test.ru")).thenReturn(user);

        UserDetails userDetails = detailService.loadUserByUsername("test@test.ru");

        assertThat(userDetails.getUsername(), is(user.getEmail()));
        verify(userService, times(1)).save(user);
    }

    /**
     * When user is not activated throws exception.
     *
     * @throws Exception DisabledException.
     */
    @Test(expected = DisabledException.class)
    public void whenUserNotActivatedShouldThrowException() throws Exception {
        when(userService.getUserByEmail("test")).thenReturn(new User());

        detailService.loadUserByUsername("test");
    }
}

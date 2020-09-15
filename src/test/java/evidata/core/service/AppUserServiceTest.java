package evidata.core.service;

import evidata.core.model.AppRole;
import evidata.core.model.AppUser;
import evidata.core.repository.AppRoleRepository;
import evidata.core.repository.AppUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;


import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Nicolle on 10.09.2018
 */
public class AppUserServiceTest {

    private AppUserRepository appUserRepositoryMock;
    private AppRoleRepository appRoleRepositoryMock;
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    private AppUserServiceImpl appUserService;


    @Before
    public void setUp() throws Exception {
        appUserService = new AppUserServiceImpl();

        appUserRepositoryMock = Mockito.mock(AppUserRepository.class);
        appRoleRepositoryMock = Mockito.mock(AppRoleRepository.class);
        bCryptPasswordEncoderMock = Mockito.mock(BCryptPasswordEncoder.class);

        appUserService.setAppUserRepository(appUserRepositoryMock);
        appUserService.setAppRoleRepository(appRoleRepositoryMock);
        appUserService.setbCryptPasswordEncoder(bCryptPasswordEncoderMock);
    }

    @Test
    public void testGetAllAppUsersEmpty() {
        List<AppUser> appUserList = appUserService.getAllAppUsers();
        Assert.assertTrue(CollectionUtils.isEmpty(appUserList));
    }

    @Test
    public void testGetAllAppUsers() {
        AppUser appUserMock = Mockito.mock(AppUser.class);

        List<AppUser> mockList = Arrays.asList(appUserMock);
        Mockito.doReturn(mockList).when(appUserRepositoryMock).findAll();

        List<AppUser> appUserList = appUserService.getAllAppUsers();
        Assert.assertTrue(appUserList.size() == 1);

        Assert.assertSame(appUserMock, appUserList.get(0));

    }

    @Test
    public void testFindAppUserByIdNull() {
        Optional<AppUser> appUserOptional = appUserService.findAppUserById(null);
        Assert.assertNull(appUserOptional);
    }

    @Test
    public void testFindAppUserByIdEmpty() {
        Long id = 10L;
        Optional<AppUser> optionalAppUser = Optional.empty();
        Mockito.doReturn(optionalAppUser).when(appUserRepositoryMock).findById(Mockito.anyLong());
        Optional<AppUser> appUserOptional = appUserService.findAppUserById(id);
        Assert.assertFalse(appUserOptional.isPresent());
    }

    @Test
    public void testFindAppUserById() {
        Long id = 10L;
        String name = "my type";
        String userName = "userName";

        AppUser appUser = new AppUser();
        appUser.setId(id);

        appUser.setUsername(userName);
        appUser.setFirstName(name);

        Optional<AppUser> optionalAppUser = Optional.of(appUser);
        Mockito.doReturn(optionalAppUser).when(appUserRepositoryMock).findById(id);

        Optional<AppUser> appUserOptional = appUserService.findAppUserById(id);
        Assert.assertTrue(appUserOptional.isPresent());

        AppUser appUserResult = appUserOptional.get();
        Assert.assertNotNull(appUserResult);
        Assert.assertEquals(userName, appUserResult.getUsername());
        Assert.assertEquals(name, appUserResult.getFirstName());
    }

    @Test
    public void testFindAppUserByEmail() {
        String email = "aa@aa.com";
        String name = "my type";
        String userName = "userName";

        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail(email);

        appUser.setUsername(userName);
        appUser.setFirstName(name);


        Mockito.doReturn(appUser).when(appUserRepositoryMock).findByEmail(email);

        AppUser appUserResult = appUserService.findUserByEmail(email);

        Assert.assertNotNull(appUserResult);
        Assert.assertEquals(userName, appUserResult.getUsername());
        Assert.assertEquals(name, appUserResult.getFirstName());
    }

    @Test
    public void testCreateNewUser() {
        String email = "aa@aa.com";
        String name = "my type";
        String userName = "userName";
        String passwordEncoded="###";

        AppUser appUser = new AppUser();
        appUser.setEmail(email);

        appUser.setUsername(userName);
        appUser.setFirstName(name);
        appUser.setPassword("aa");

        Mockito.doReturn(passwordEncoded).when(bCryptPasswordEncoderMock).encode(Mockito.anyString());
        AppRole userAppRole = new AppRole();
        Mockito.doReturn(userAppRole).when(appRoleRepositoryMock).findByRole("RESPONSIBLE");

        appUserService.createNewUser(appUser);

        Assert.assertEquals(passwordEncoded, appUser.getPassword());
        Assert.assertTrue(appUser.getAppRoles().contains(userAppRole));
        verify(appUserRepositoryMock, times(1)).save(appUser);
    }

    @Test
    public void testUpdateUser() {
        String userName = "userName";
        String passwordEncoded="###";

        AppUser appUser = new AppUser();
        appUser.setUsername(userName);
        appUser.setPassword("aa");

        Mockito.doReturn(passwordEncoded).when(bCryptPasswordEncoderMock).encode(Mockito.anyString());

        appUserService.updateUser(appUser);

        Assert.assertEquals(passwordEncoded, appUser.getPassword());
        verify(appUserRepositoryMock, times(1)).save(appUser);
    }

    @Test
    public void testUpdateUserEmptyPassword() {
        String userName = "userName";
        String originalPassword="##";
        Long id = 1L;

        AppUser appUser = new AppUser();

        appUser.setId(id);
        appUser.setUsername(userName);


        AppUser origUser= new AppUser();
        origUser.setPassword(originalPassword);

        Mockito.doReturn(origUser).when(appUserRepositoryMock).findOne(id);

        appUserService.updateUser(appUser);

        Assert.assertEquals(originalPassword, appUser.getPassword());
        verify(appUserRepositoryMock, times(1)).save(appUser);
    }

}

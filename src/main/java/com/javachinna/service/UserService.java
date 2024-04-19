package com.javachinna.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.javachinna.dto.NewPasswordRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.javachinna.dto.LocalUser;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.User;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {

	public List<User> findAllUsers() ;

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	User ChangePassword (NewPasswordRequest newPasswordRequest);

	User findByEmailAndCin(String email,Long cin);

	User findUserByNum(String num);

	User getUserById (Long id);

	Optional<User> findUserById(Long id);

	/*LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);*/
	
	void createVerificationTokenForUser(User user, String token);

	boolean resendVerificationToken(String token);

	String validateVerificationToken(String token);

	boolean existbynum(String num);
}

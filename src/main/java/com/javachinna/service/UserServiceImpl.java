package com.javachinna.service;

import java.io.IOException;
import java.util.*;

import java.util.Calendar;
import java.util.List;


import com.javachinna.dto.NewPasswordRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javachinna.config.AppConstants;
import com.javachinna.dto.LocalUser;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.dto.SocialProvider;
import com.javachinna.exception.OAuth2AuthenticationProcessingException;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Role;
import com.javachinna.model.User;
import com.javachinna.model.VerificationToken;
import com.javachinna.repo.RoleRepository;
import com.javachinna.repo.UserRepository;
import com.javachinna.repo.VerificationTokenRepository;
import com.javachinna.security.oauth2.user.OAuth2UserInfo;
import com.javachinna.util.GeneralUtils;

import dev.samstevens.totp.secret.SecretGenerator;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SecretGenerator secretGenerator;

	@Autowired
	MailService mailService;

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) /*throws UserAlreadyExistAuthenticationException */{
		/*if ((signUpRequest.getUserID() == null && !userRepository.existsById(signUpRequest.getUserID()))) {
			throw new UserAlreadyExistAuthenticationException("member with User id :" + signUpRequest.getUserID() + "not exist");
		}else*/
		/* else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}else */
			/*if (!userRepository.existsByNum(signUpRequest.getNum()) ){
				throw new UserAlreadyExistAuthenticationException("member with User num  : "+signUpRequest.getNum() + " not exist");
			}*/


		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		userRepository.flush();
		return user;



	}
	public boolean existbynum(String num) {
		// Assuming you have a memberRepository
		return userRepository.existsByNum(num);
	}



	private User buildUser(final SignUpRequest formDTO) {
		User user = findUserByNum(formDTO.getNum());
		user.setNum(formDTO.getNum());
		user.setEmail(formDTO.getEmail());
		user.setCin(formDTO.getCin());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setEnabled(user.isEnabled());
		user.setVerified((byte)1);
		user.setProviderUserId(formDTO.getProviderUserId());
		if (formDTO.isUsing2FA()) {
			user.setUsing2FA(true);
			user.setSecret(secretGenerator.generate());
		}
		return user;
	}

	@Override
	@Transactional(value = "transactionManager")
	public User ChangePassword (final NewPasswordRequest newPasswordRequest){
		User user = findUserByNum(newPasswordRequest.getNum());
		user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
		Date now = Calendar.getInstance().getTime();
		user.setModifiedDate(now);
		user = userRepository.save(user);
		userRepository.flush();
		return user;
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByEmailAndCin(String email, Long cin) {
		return userRepository.findByEmailAndCin(email,cin);
	}

	@Override
	public User findUserByNum(final String num) {
		return userRepository.findByNum(num);
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> o= userRepository.findById(id);
		return o.isPresent() ? o.get() :null;
	}


	/*@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}*/

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	@Transactional
	public boolean resendVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		if(vToken != null) {
			vToken.updateToken(UUID.randomUUID().toString());
			vToken = tokenRepository.save(vToken);
			mailService.sendVerificationToken(vToken.getToken(), vToken.getUser());
			return true;
		}
		return false;
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return AppConstants.TOKEN_INVALID;
		}

		final User user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return AppConstants.TOKEN_EXPIRED;
		}
		if (user.getVerified() == 1) {
			user.setEnabled(true);
			user.setVerified((byte) 2);  // Email verified, update status

		}


		tokenRepository.delete(verificationToken);
		userRepository.save(user);
		return AppConstants.TOKEN_VALID;
	}
}

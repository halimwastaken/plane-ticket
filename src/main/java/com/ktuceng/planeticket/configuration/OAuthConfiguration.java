package com.ktuceng.planeticket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Value("${oauth2.client.username}")
	private String oauth2User;
	
	@Value("${oauth2.client.password}")
	private String oauth2Password;
	
	@Value("${oauth2.client.accesstoken.validity}")
	private Integer accessValidity;
	
	@Value("${oauth2.client.refreshtoken.validity}")
	private Integer refreshValidity;
	
	@Value("${oauth2.JwtAccessTokenConverter.key}")
	private String JwtConvKey;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient(oauth2User).secret(passwordEncoder.encode(oauth2Password))
			.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read", "write")
			.authorities("User", "Admin")
			.autoApprove(true)
			.accessTokenValiditySeconds(accessValidity)
			.refreshTokenValiditySeconds(refreshValidity);
	}
	
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
			.authenticationManager(authenticationManager)
			.accessTokenConverter(defaultAccessTokenConverter())
			.userDetailsService(userDetailsService);
	}
	
	@Bean 
	public TokenStore tokenStore() {
		return new JwtTokenStore(defaultAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter defaultAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(JwtConvKey);
		return converter;
	}
}
package com.atro.oauth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableAuthorizationServer
public class AuthorizationServerConfig implements AuthorizationServerConfigurer  {

	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(

            User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .authorities("ROLE_USER")
                .build()

        );
    }

	/**
	 * This fixes the problem that authorization server expects passwords in default {@link PasswordEncoder} format, which is
	 * {@code "{encoder}encodedPass"} for {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}. We don't want our internal
	 * client to encode password before sends it, because it makes no sense. This default encoder for
	 * {@link AuthorizationServerSecurityConfigurer} looks like a spring security oauth2 glitch.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		clients.inMemory()
        .withClient("internal")
        .secret("internal_secret")
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("account", "contacts", "internal")
        .resourceIds()
        .autoApprove(true)
        .accessTokenValiditySeconds(10*60)
        .refreshTokenValiditySeconds(30*60);
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		endpoints
		.authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService());
		
	}
}

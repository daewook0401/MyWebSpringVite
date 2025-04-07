package com.myspring.myproject.configuration;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.myspring.myproject.configuration.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {
    private final JwtFilter filter;
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.formLogin(AbstractHttpConfigurer::disable) //=> formLogin 비활성화
							.httpBasic(AbstractHttpConfigurer::disable)
							.csrf(AbstractHttpConfigurer::disable)
							.cors(Customizer.withDefaults())
							.authorizeHttpRequests(requests -> {
								// 로그인·토큰 갱신·회원가입은 인증 없이 허용
								requests.requestMatchers(HttpMethod.POST,
									"/auth/login",
									"/auth/refresh",
									"/users"
								).permitAll();
					
								// 관리자 경로
								requests.requestMatchers("/admin/**").hasRole("ADMIN");
					
								// 조회(READ) 작업은 모두 허용
								requests.requestMatchers(HttpMethod.GET,
									"/uploads/**",
									"/boards/**",
									"/comments/**",
									"/categories/**"
								).permitAll();
					
								// 수정·삭제·생성(WRITE) 작업은 인증 필요
								requests.requestMatchers(HttpMethod.POST,
									"/boards",
									"/comments",
									"/categories"
								).authenticated();
								requests.requestMatchers(HttpMethod.PUT,
									"/users",
									"/boards/**",
									"/comments/**",
									"/categories/**"
								).authenticated();
								requests.requestMatchers(HttpMethod.DELETE,
									"/users",
									"/boards/**",
									"/comments/**",
									"/categories/**"
								).authenticated();
					
								// 그 외 모든 요청도 인증 필요
								requests.anyRequest().authenticated();
							})
							/*
							 * sessionManagement : 세션을 어떻게 관리할 것인지 지정할 수 있음
							 * sessionCreationPolicy : 세션 사용 정책을 결정
							 */
							.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
							.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
							.build(); 
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource(){
		CorsConfiguration configuration = new CorsConfiguration();
		// configuration.setAllowedOrigins(Arrays.asList("192.168.219.**:**"));
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

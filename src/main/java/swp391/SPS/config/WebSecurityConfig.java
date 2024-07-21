package swp391.SPS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new LoginServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        author ->
                                author
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .requestMatchers("/admin-dashboard/**", "/save-role/", "/save-active/", "/search", "/user_detail/**")
                                        .hasAuthority("ADMIN")
                                        .requestMatchers("/manager", "/api/phones", "/api/change-status",
                                                "/searchorder", "/order-detail-manager/**", "/order-detail",
                                                "/approve/**", "/reject/**", "/complete/**", "/manageReport",
                                                "/report/report-detail/**", "/manageProduct/**", "/searchStatus/json",
                                                "/add-product", "/edit-product", "/add-brand", "/edit-brand", "/user_detail/**", "/refund/**", "/filterOrdersByDate/**").hasAuthority("MANAGER")
                                        .requestMatchers("/cart/*", "/checkout", "/detail", "/userorder", "/place-order",
                                                "/cancel-order/**", "/orderDetail/**", "/report/*", "/submit-report",
                                                "/delete-report", "/respond", "/cart-single/*", "/checkout/update", "/user_detail/**").hasAnyAuthority("USER")
                                        .requestMatchers("/forgot-password", "/register", "/register-new", "/", "/page/login", "/reset-password", "/shop/**", "/shop/brand/**", "/single-product/**", "/cart", "/about", "/profile/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .formLogin(
                        login ->
                                login
                                        .loginPage("/page/login")
                                        .loginProcessingUrl("/do-login")
                                        .successHandler(new MySimpleUrlAuthenticationSuccessHandler())
                                        .permitAll())
                .logout(
                        logout ->
                                logout
                                        .invalidateHttpSession(true)
                                        .clearAuthentication(true)
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/")
                                        .permitAll())
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
        return http.build();
    }
}

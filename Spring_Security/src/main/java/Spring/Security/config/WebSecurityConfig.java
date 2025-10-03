package Spring.Security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Dùng đúng bean UserDetailsService bạn đang xài (đổi qualifier nếu khác)
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            @Qualifier("customUserDetailsService") UserDetailsService uds,
            PasswordEncoder encoder) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider(); // deprecated ctor: OK
        p.setUserDetailsService(uds); // cũng deprecated: OK trong 6.x
        p.setPasswordEncoder(encoder);
        return p;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider provider) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(provider)
            .authorizeHttpRequests(auth -> auth
                // Cho phép mở public các tài nguyên & auth API
                .requestMatchers("/api/auth/**", "/login", "/403",
                                 "/css/**", "/js/**", "/images/**").permitAll()

                // Xem danh sách/sản phẩm (GET) cho USER và ADMIN
                .requestMatchers(HttpMethod.GET, "/products", "/products/", "/products/**")
                    .hasAnyRole("USER","ADMIN")

                // Tạo/sửa/xoá chỉ ADMIN
                .requestMatchers("/products/new", "/products/edit/**", "/products/delete/**")
                    .hasRole("ADMIN")

                // Mọi thứ khác cần login
                .anyRequest().authenticated()
            )
            .formLogin(f -> f
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/products", true)
                .failureUrl("/login?error")
            )
            .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
            .exceptionHandling(e -> e.accessDeniedPage("/403"))
            .httpBasic(b -> {}); // để API trả 401 thay vì redirect

        return http.build();
    }
}

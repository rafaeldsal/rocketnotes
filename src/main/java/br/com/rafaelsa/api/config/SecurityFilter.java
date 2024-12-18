package br.com.rafaelsa.api.config;

import br.com.rafaelsa.api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {
    var header = this.recoverToken(request);

    if(header != null) {
      var token = tokenService.validateToken(header);
      UserDetails user = userRepository.findById(Long.valueOf(token.getSubject())).get();

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          token.getSubject(),
          null,
          user.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }


  private String recoverToken(HttpServletRequest request) {
    var header = request.getHeader("Authorization");
    if(header == null) return null;

    return header.replace("Bearer ", "");
  }
}

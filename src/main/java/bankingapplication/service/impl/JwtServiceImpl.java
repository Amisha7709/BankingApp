package bankingapplication.service.impl;

import bankingapplication.entity.Customer;
import bankingapplication.repo.CustomerRepo;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class JwtServiceImpl {

  @Service
  public static class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      Customer user = userRepository.findByEmailId(userName);
      if (user == null) {
        throw new UsernameNotFoundException("Invalid username or password.");
      }
      return new org.springframework.security.core.userdetails.User(userName, user.getPassword(),
          new ArrayList<>());
    }
  }

}

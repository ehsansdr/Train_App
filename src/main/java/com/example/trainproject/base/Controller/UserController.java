package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Dto.AuthAccessToken;
import com.example.trainproject.base.Dto.LoginUserRequest;
import com.example.trainproject.base.Dto.LoginUserResponse;
import com.example.trainproject.base.Event.UserEvent;
import com.example.trainproject.base.Model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-controller")
public class UserController extends BaseController {

  @Autowired
  private ApplicationEventPublisher eventPublisher;
  @Autowired
  private MessageSource messageSource;


  @GetMapping("/hello-user")
  public String helloUser() {
    UserEvent userEvent = new UserEvent(this, "event call");
    eventPublisher.publishEvent(userEvent);

    return "Event triggered with message: event call";
  }

  @GetMapping("/get-message")
  public String getMessage(
      @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    // Default to Locale.ENGLISH if locale is not provided
    locale = locale == null ? Locale.ENGLISH : locale;
    return messageSource.getMessage("error.exception.card.cvv2.mismatch", null, locale);
  }
//
//  public LoginUserResponse authenticate(LoginUserRequest request) {
//    var authToken = new UsernamePasswordAuthenticationToken(request.getUsername(),
//        request.getPassword());
//    try {
//      Authentication authenticate = authenticationManager.authenticate(authToken);
//      User user = (User) authenticate.getPrincipal();
//
////      if ((user.getDeletedAt() != null)) {
////        throw new UserIsDisabledException();
////      }
//
//      AuthAccessToken authAccessToken = tokenService.generateAuthAccessToken(user, Plans.WITH_OUT_PLAN);
//      return new LoginUserResponse(user.getUsername(), authAccessToken.getBearerToken(),
//          authAccessToken.getRefreshToken(), tokenService.getAccessTokenExpirationInSeconds());
//    } catch (AuthenticationException e) {
//      log.error("wrong username and password authentication for {}", request.getUsername());
//      throw new LoginWrongException();
//    }
//  }

}

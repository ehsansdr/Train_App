# How to Create, Configure, and Use `messages_fa.properties` in a Spring Boot Application

## Introduction

In this guide, we will walk through the steps required to create and use a Farsi-encoded properties file (`messages_fa.properties`) in a Spring Boot application. The purpose of this file is to store localized strings in Farsi, which can be used for internationalization (i18n) in your Spring Boot application.

### Requirements
- A Spring Boot application.
- A `messages_fa.properties` file with Farsi text encoded in the ISO-8859-1 format.
- The use of Spring's `MessageSource` for internationalization.

---

## Step 1: Create the `messages_fa.properties` File

First, you need to create a properties file named `messages_fa.properties` that will contain the key-value pairs for your localized messages in Farsi.

### Example `messages_fa.properties`:

Create the file under the `src/main/resources/i18n/` directory of your Spring Boot project.

```
src/
 └─ main/
     └─ resources/
         └─ i18n/
             └─ messages_fa.properties
```

In the `messages_fa.properties` file, you should store your messages in the Farsi language, but the content should be URL-encoded (or encoded in ISO-8859-1), as shown below:

```properties
third.party.invalid.accountNumber=ÙØ§Ø±Ø¯ Ú©Ø±Ø¯Ù Ø´ÙØ§Ø±Ù Ø­Ø³Ø§Ø¨ Ø§ÙØ²Ø§ÙÛ Ø§Ø³Ø
```

This example contains an encoded version of the Farsi phrase for "Invalid account number". You can add more keys and values as needed.

---

## Step 2: Configure Spring Boot for Internationalization (i18n)

Next, you need to configure your Spring Boot application to use the `messages_fa.properties` file for localization. Spring Boot provides an easy way to handle this using the `MessageSource` bean.

### Create a `MessageConfig` Class

Create a configuration class where you will define the `MessageSource` bean. This bean will be responsible for loading the `messages_fa.properties` file.

```java
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.Locale;

@Configuration
public class MessageConfig {

    // Define the MessageSource bean
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages_fa");  // Specify path to messages_fa.properties
        messageSource.setDefaultEncoding("UTF-8");  // Ensure UTF-8 encoding is used
        return messageSource;
    }

    // Define the default locale (Farsi)
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("fa", "IR"));  // Set default locale to Farsi (fa_IR)
        return localeResolver;
    }
}
```

### Explanation:
- The `MessageSource` bean loads the properties from `classpath:i18n/messages_fa`, which is the path where we have stored the `messages_fa.properties` file.
- The default encoding is set to `UTF-8` to properly handle non-ASCII characters like Farsi.
- The `SessionLocaleResolver` ensures that the default locale is set to Farsi (`fa_IR`).

---

## Step 3: Use the Localized Messages in Your Application

Now that your `MessageSource` is set up, you can inject it into your services or controllers to use the localized messages in your Spring Boot application.

### Example Service

Here’s an example of a service that fetches a localized message from the `messages_fa.properties` file:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.context.i18n.LocaleContextHolder;

@Service
public class AccountService {

    @Autowired
    private MessageSource messageSource;

    public void printInvalidAccountMessage() {
        // Fetch the message using the key from messages_fa.properties
        String message = messageSource.getMessage("third.party.invalid.accountNumber", null, LocaleContextHolder.getLocale());
        System.out.println(message);  // This will print the Farsi message
    }
}
```

### Explanation:
- The `messageSource.getMessage()` method is used to retrieve the message corresponding to the key `"third.party.invalid.accountNumber"`.
- `LocaleContextHolder.getLocale()` is used to automatically determine the current locale (in this case, Farsi).

### Example Controller

You can also expose the messages in a RESTful API or as part of a web controller. Here’s an example of a controller:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/account/invalid")
    public String getInvalidAccountMessage() {
        // Fetch and return the localized message
        return messageSource.getMessage("third.party.invalid.accountNumber", null, LocaleContextHolder.getLocale());
    }
}
```

This controller endpoint returns the localized Farsi message when accessed.

---

## Step 4: Run the Application

Finally, you can run your Spring Boot application. The application should load the Farsi messages from `messages_fa.properties` and display them wherever they are used.

If you navigate to `/account/invalid`, the application will return the localized message from the `messages_fa.properties` file.

### Sample Output:

```
ÙØ§Ø±Ø¯ Ú©Ø±Ø¯Ù Ø´ÙØ§Ø±Ù Ø­Ø³Ø§Ø¨ Ø§ÙØ²Ø§ÙÛ Ø§Ø³Ø
```

---

## Step 5: Additional Configuration (Optional)

### Locale Switching

If you want to allow users to switch locales (for example, between English and Farsi), you can create an endpoint or mechanism for changing the locale.

Here’s a basic example of how to switch the locale:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LocaleController {

    @Autowired
    private SessionLocaleResolver localeResolver;

    @GetMapping("/set-locale")
    public String setLocale(@RequestParam("lang") String lang, HttpServletRequest request) {
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, null, locale);
        return "Locale set to " + lang;
    }
}
```

You can now switch the locale by providing a query parameter like `?lang=fa` or `?lang=en`.

---

## Conclusion

In this guide, we created and configured a `messages_fa.properties` file to handle Farsi localization in a Spring Boot application. We also demonstrated how to configure Spring Boot to use this file and how to retrieve localized messages using the `MessageSource` bean.

By following these steps, you can easily add internationalization support to your Spring Boot application and use localized messages in any language, including Farsi.

--- 

This Markdown document gives you a full step-by-step guide, and you can save it in a file named `How-to-use-messages-fa-properties.md` for future reference.

Let me know if you need any further help!
package com.example.electrowayfinal.Validation;

import com.example.electrowayfinal.models.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, User> {

    @Override
    public void initialize(final ValidPassword arg0) {
    }

    @SneakyThrows
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {

        Properties props = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");
        props.load(inputStream);
        MessageResolver resolver = new PropertiesMessageResolver(props);


        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
                new LengthRule(8, 60),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule(),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
        ));
        RuleResult result = validator.validate(new PasswordData(user.getPassword()));
        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

}
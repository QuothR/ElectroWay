package com.example.electrowayfinal.Validation;

import com.example.electrowayfinal.user.User;
import lombok.SneakyThrows;
import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, User> {


    @Override

    public void initialize(final ValidPassword arg0) {


    }

    @SneakyThrows

    @Override

    public boolean isValid(User user, ConstraintValidatorContext context) {


        //customizing validation messages

        Properties props = new Properties();

        InputStream inputStream = getClass()

                .getClassLoader().getResourceAsStream("passay.properties");

        props.load(inputStream);

        MessageResolver resolver = new PropertiesMessageResolver(props);


        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(


                // length between 8 and 16 characters

                new LengthRule(8, 16),


                // at least one upper-case character

                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character

                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character

                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)

                new CharacterRule(EnglishCharacterData.Special, 1),


                // no whitespace

                new WhitespaceRule(),


                // rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)

                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),

                // rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345)

                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)

        ));
        System.out.println("COOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOCKASDASDASDAS");
        RuleResult result = validator.validate(new PasswordData(user.getPasswordHash()));


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
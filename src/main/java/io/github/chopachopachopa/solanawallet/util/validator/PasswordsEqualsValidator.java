package io.github.chopachopachopa.solanawallet.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordsEqualsValidator implements ConstraintValidator<PasswordsEquals, Object> {
    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordsEquals constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String passwordValue = (String) new BeanWrapperImpl(value).getPropertyValue(password);
        String confirmPasswordValue = (String) new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

        return Objects.equals(passwordValue, confirmPasswordValue);
    }
}

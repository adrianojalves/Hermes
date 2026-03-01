package br.com.ajasoftware.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value={ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfCnpjValidation.class)
public @interface CpfCnpj {
	String message() default "CNPJ/CPF inválido.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
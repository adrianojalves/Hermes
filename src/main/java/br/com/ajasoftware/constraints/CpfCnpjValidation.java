package br.com.ajasoftware.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class CpfCnpjValidation implements ConstraintValidator<CpfCnpj, String>{

	@Override
	public void initialize(CpfCnpj constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String cpfCnpj, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(cpfCnpj)){
			return true;
		}
		
		cpfCnpj = cpfCnpj.replace(".","").replace("/", "").replace("-", "");
		
		if(cpfCnpj.length()<11){
			return false;
		}
		if(cpfCnpj.length()>11 && cpfCnpj.length()<14){
			return false;
		}
		if(cpfCnpj.length()==11)
			return ValidaCpfCnpj.isCPF(cpfCnpj);
		else
			return ValidaCpfCnpj.isCNPJ(cpfCnpj);
	}
}

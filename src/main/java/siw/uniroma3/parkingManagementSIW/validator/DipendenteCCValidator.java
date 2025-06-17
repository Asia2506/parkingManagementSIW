package siw.uniroma3.parkingManagementSIW.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.service.DipendenteCCService;

@Component
public class DipendenteCCValidator implements Validator{
	
	@Autowired
	private DipendenteCCService dipendenteCCService;
	
	@Override
	 public void validate(Object o, Errors errors) {
	    DipendenteCC d = (DipendenteCC)o;
	    if (d.getNome()!=null && d.getCognome()!=null && dipendenteCCService.existsByNomeAndCognome(d.getNome(), d.getCognome())) {
	      errors.reject("d.duplicate");
	    }
	  }

	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return DipendenteCC.class.equals(aClass);
	}


	

}

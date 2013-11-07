package pe.com.jx_market.validacion.spring;

import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import pe.com.jx_market.domain.DTO_Solicitud;


public class ValidaRegistroSolicitud extends ValidadorSpringTemplate {

	@Override
    public void doValida(final Object obj, final Errors errors) {

		final DTO_Solicitud solicitud = (DTO_Solicitud) obj;

		ValidationUtils.rejectIfEmpty(errors, "razon", "none", "Debe llenar su razon social");
		ValidationUtils.rejectIfEmpty(errors, "ruc", "none", "Debe llenar su Número de Ruc");
		ValidationUtils.rejectIfEmpty(errors, "correo", "none", "Debe llenar su e-mail");

		final EmailValidator evalid = EmailValidator.getInstance();
		if(! evalid.isValid(solicitud.getCorreo())) {
			errors.rejectValue("correo", "none", "La dirección de email no es válida");
		}
	}

}

package pe.com.jx_market.validacion.spring;

import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import pe.com.jx_market.domain.DTO_Cliente;


public class ValidaRegistroCliente extends ValidadorSpringTemplate {

	@Override
    public void doValida(final Object obj, final Errors errors) {

		final DTO_Cliente cliente = (DTO_Cliente) obj;

		ValidationUtils.rejectIfEmpty(errors, "ciudad", "none", "Debe llenar su nacionalidad");
		ValidationUtils.rejectIfEmpty(errors, "dni", "none", "Debe llenar su Número de Documento");
		ValidationUtils.rejectIfEmpty(errors, "apellido", "none", "Debe llenar sus apellidos");
		ValidationUtils.rejectIfEmpty(errors, "nombre", "none", "Debe llenar sus nombres");
		ValidationUtils.rejectIfEmpty(errors, "fecNac", "none", "Debe llenar su fecha de nacimiento");
		ValidationUtils.rejectIfEmpty(errors, "direccion", "none", "Debe llenar su dirección");
		ValidationUtils.rejectIfEmpty(errors, "ciudad", "none", "Debe seleccionar el pais de domicilio");
		ValidationUtils.rejectIfEmpty(errors, "telefono", "none", "Debe indicar el número telefónico de su domicilio");
		ValidationUtils.rejectIfEmpty(errors, "email", "none", "Debe llenar su e-mail");

		final EmailValidator evalid = EmailValidator.getInstance();
		if(! evalid.isValid(cliente.getEmail())) {
			errors.rejectValue("email", "none", "La dirección de email no es válida");
		}
	}

}

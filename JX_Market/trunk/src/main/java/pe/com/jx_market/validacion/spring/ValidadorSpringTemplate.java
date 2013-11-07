package pe.com.jx_market.validacion.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import pe.com.jx_market.validacion.Validador;

public abstract class ValidadorSpringTemplate
    implements Validador
{

    @Override
    public List<String> valida(final Object obj)
    {
        final Errors e = new BeanPropertyBindingResult(obj, "err");
        doValida(obj, e);
        final List<String> ans = new ArrayList<String>();
        // List<FieldError> l = e.getFieldErrors();
        final List<ObjectError> l = e.getAllErrors();
        for (final ObjectError f : l) {
            ans.add(f.getDefaultMessage());
        }
        return ans;
    }

    public abstract void doValida(Object obj,
                                  Errors e);
}

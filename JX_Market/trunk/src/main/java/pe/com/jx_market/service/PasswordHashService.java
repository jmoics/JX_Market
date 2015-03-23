/*
 * (C) 2010 JX_MARKET TODOS LOS DERECHOS RESERVADOS
 */

package pe.com.jx_market.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 *
 * @author jorge
 */
@Service
public class PasswordHashService
    implements BusinessService<String>
{

    static Log logger = LogFactory.getLog(PasswordHashService.class);
    private MessageDigest algorithm = null;

    public PasswordHashService()
    {
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException nsae) {
            logger.error("Cannot find digest algorithm MD5");
            throw new RuntimeException(nsae);
        }
    }

    @Override
    public DTO_Output<String> execute(final DTO_Input<String> e)
    {
        final String arg = e.getObject();
        final byte[] defaultBytes = arg.getBytes();
        algorithm.reset();
        algorithm.update(defaultBytes);
        final byte messageDigest[] = algorithm.digest();
        final StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            final String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        final String answer = hexString.toString();
        final DTO_Output<String> output = new DTO_Output<String>();
        output.setObject(answer.substring(0, 32));
        output.setErrorCode(Constantes.OK);
        return output;
    }
}

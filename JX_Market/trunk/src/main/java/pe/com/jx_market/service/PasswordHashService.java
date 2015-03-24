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
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

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
    public ServiceOutput<String> execute(final ServiceInput<String> e)
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
        final ServiceOutput<String> output = new ServiceOutput<String>();
        output.setObject(answer.substring(0, 32));
        output.setErrorCode(Constantes.OK);
        return output;
    }
}

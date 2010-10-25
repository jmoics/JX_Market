/*
 * (C) 2010 JX_MARKET
 * TODOS LOS DERECHOS RESERVADOS
 */

package pe.com.jx_market.service;

import pe.com.jx_market.utilities.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.*;

/**
 *
 * @author jorge
 */
public class PasswordHashService implements BusinessService {

    static Log logger = LogFactory.getLog(PasswordHashService.class);
    private MessageDigest algorithm = null;

    public PasswordHashService() {
         try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            logger.error("Cannot find digest algorithm MD5");
            throw new RuntimeException(nsae);
        }
    }

    public DTO_Output execute(DTO_Input e) {
        String arg = (String) e.getObject();
        byte[] defaultBytes = arg.getBytes();
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        String answer = hexString.toString();
        DTO_Output output = new DTO_Output();
        output.setObject(answer.substring(0,32));
        output.setErrorCode(Constantes.OK);
        return output;
    }
}

package pe.com.jx_market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Client;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.persistence.ClientMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Administracion de Contactos.
 *
 * @author jorge
 *
 */
@Service
public class ClientService
    implements BusinessService
{

    /**
     *
     */
    private final Log logger = LogFactory.getLog(ClientService.class);
    /**
     *
     */
    @Autowired
    private ClientMapper clientMapper;
    /**
     *
     */
    @Autowired
    private BusinessService userService;

    /**
     * El ServiceInput contendrá como verbo un String: para realizar una
     * consulta se usará el verbo "list" y un string con el codigo de la
     * institucion a la que pertenece el contacto, para ingresar o actualizar
     * datos se usará el verbo "register" conteniendo además un objeto
     * DTO_Contacto con los datos nuevos a ingresar, y para eliminar datos se
     * usará el verbo "delete" adeḿas de contener un string con el codigo del
     * contacto a eliminar. El ServiceOutput tiene codigo de error OK; y si el
     * verbo es "list" contendra una lista de objetos DTO_Contacto con todos los
     * campos leidos de la Base de Datos.
     *
     * @param Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput _input)
    {

        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            final DTO_Client client = (DTO_Client) _input.getObject();
            output.setLista(this.clientMapper.getClients(client));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_Client client = (DTO_Client) _input.getObject();
            final List<DTO_Client> cliLstTmp = this.clientMapper.getClients(client);
            if (cliLstTmp != null && !cliLstTmp.isEmpty()) {
                output.setObject(cliLstTmp.get(0));
                output.setErrorCode(Constantes.OK);
            }
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final Map map = _input.getMapa();
            final DTO_Client client = (DTO_Client) map.get("client");
            DTO_User user = (DTO_User) map.get("user");
            final ServiceInput inp = new ServiceInput(user);
            if (user != null) {
                if (user.getId() == null) {
                    inp.setAction(Constantes.V_REGISTER);
                    final ServiceOutput out = this.userService.execute(inp);
                    if (out.getErrorCode() == Constantes.OK) {
                        user = getUser(user);
                        client.setUserId(user.getId());
                    }
                } else {
                    user = getUser(user);
                    final Map<String, String> map2 = new HashMap<String, String>();
                    map2.put("nonPass", "nonPass");
                    map2.put("oldPass", null);
                    inp.setMapa(map2);
                    inp.setAction("chgpass");
                    final ServiceOutput out = this.userService.execute(inp);
                    if (out.getErrorCode() == Constantes.OK) {
                        this.logger.info("El password fue cambiado correctamente");
                    } else {
                        this.logger.error("Error al cambiar el password");
                    }
                }
            }
            final DTO_Client clTmp = (DTO_Client) this.clientMapper.getClients(client);
            if (clTmp == null) {
                this.clientMapper.insertClient(client);
            } else {
                this.clientMapper.updateClient(client);
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.clientMapper.eliminaClient((DTO_Client) _input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }

    /**
     * @param user User.
     * @return User if found.
     */
    private DTO_User getUser(final DTO_User _user)
    {
        DTO_User ret = null;
        final ServiceInput inp = new ServiceInput(_user);
        inp.setAction(Constantes.V_GET);
        final ServiceOutput out = this.userService.execute(inp);
        if (out.getErrorCode() == Constantes.OK) {
            ret = (DTO_User) out.getObject();
        }
        return ret;
    }
}

package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.Ubication;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public interface UbicationMapper
{

    /**
     * @param _ubi Parameter data for where clause.
     * @return List with the parameters.
     */
    List<Ubication> getUbications(Ubication _ubi);

    /**
     * @param _ubi Parameter data for where clause.
     * @return Parameter.
     */
    Ubication getUbication4Id(Ubication _ubi);

    /**
     * @param _ubi Parameter data for where clause.
     * @return TRUE if the insert is correct.
     */
    boolean insertUbication(Ubication _ubi);

    /**
     * @param _ubi Parameter data for where clause.
     * @return TRUE if the update is correct.
     */
    boolean updateUbication(Ubication _ubi);

    /**
     * @param _ubi Parameter data for where clause.
     * @return TRUE if the delete is correct.
     */
    boolean deleteUbication(Ubication _ubi);

}

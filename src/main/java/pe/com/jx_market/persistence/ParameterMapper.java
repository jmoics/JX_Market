package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.Parameter;
import pe.com.jx_market.domain.ParameterType;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public interface ParameterMapper
{

    /**
     * @param _param Parameter data for where clause.
     * @return List with the parameters.
     */
    List<Parameter> getParameters(Parameter _param);

    /**
     * @param _param Parameter data for where clause.
     * @return Parameter.
     */
    Parameter getParameter4Id(Parameter _param);

    /**
     * @param _param Parameter data for where clause.
     * @return TRUE if the insert is correct.
     */
    boolean insertParameter(Parameter _param);

    /**
     * @param _param Parameter data for where clause.
     * @return TRUE if the update is correct.
     */
    boolean updateParameter(Parameter _param);

    /**
     * @param _param Parameter data for where clause.
     * @return TRUE if the delete is correct.
     */
    boolean deleteParameter(Parameter _param);

    /**
     * @param _paramType ParameterType data for where clause.
     * @return List with the parameter types.
     */
    List<ParameterType> getParameterTypes(ParameterType _paramType);

    /**
     * @param _paramType ParameterType data for where clause.
     * @return Parameter Type.
     */
    ParameterType getParameterType4Id(ParameterType _paramType);

    /**
     * @param _paramType ParameterType data for where clause.
     * @return TRUE if the insert is correct.
     */
    boolean insertParameterType(ParameterType _paramType);

    /**
     * @param _paramType ParameterType data for where clause.
     * @return TRUE if the update is correct.
     */
    boolean updateParameterType(ParameterType _paramType);

    /**
     * @param _paramType ParameterType data for where clause.
     * @return TRUE if the delete is correct.
     */
    boolean deleteParameterType(ParameterType _paramType);
}

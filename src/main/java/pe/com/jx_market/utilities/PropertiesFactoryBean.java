package pe.com.jx_market.utilities;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author jcuevas
 *
 */
public class PropertiesFactoryBean
    implements FactoryBean
{

    /**
     *
     */
    private static Properties props = new Properties();

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public Object getObject()
        throws Exception
    {
        return props;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class getObjectType()
    {
        return Properties.class;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton()
    {
        return true;
    }

    /**
     * @param _aprops properties.
     */
    static void setProperties(final Properties _aprops)
    {
        props = _aprops;
    }
}

package pe.com.jx_market.utilities;

import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;

public class PropertiesFactoryBean implements FactoryBean {
    private static Properties props = new Properties();

    public Object getObject () throws Exception {
        return props;
    }

    public Class getObjectType () {
        return Properties.class;
    }

    public boolean isSingleton () {
        return true;
    }

    static void setProperties (Properties aprops) {
        props = aprops;
    }
}

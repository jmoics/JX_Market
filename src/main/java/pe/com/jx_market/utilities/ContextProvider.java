package pe.com.jx_market.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class ContextProvider
{

    private static final Map<String, Context> contextMap = new HashMap<String, Context>();

    /**
     * Creates a thread local variable.
     */
    public ContextProvider()
    {
    }

    public Context get(final String _key)
    {
        return contextMap.get(_key);
    }

    public void set(final String _key,
                    final Context _value)
    {
        contextMap.put(_key, _value);
    }

    public void remove(final String _key)
    {
        contextMap.remove(_key);
    }

    public boolean existContext(final String _key) {
        return contextMap.containsKey(_key);
    }

}

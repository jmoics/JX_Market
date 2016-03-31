package pe.com.jx_market.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Desktop;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_User;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public final class Context
{

    /**
     * Key used to access the current company from the userattributes.
     */
    public static final String CURRENTCOMPANY = Context.class.getName() + ".CurrentCompany";

    /**
     * Logging instance used in this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Context.class);

    /**
     * Each thread has his own context object. The value is automatically
     * assigned from the filter class. This allows to have a different Context
     * for every Users which is connect to the WebApp Server. For the case that
     * a thread creates a child threat a different context is created this is
     * needed e.g. for background process form quartz.
     */
    private static ContextProvider THREADCONTEXT = new ContextProvider();

    /**
     * This instance variable represents the user of the context.
     *
     * @see #getPerson
     */
    private DTO_User person = null;

    /**
     * The current active company.
     */
    private DTO_Company company = null;

    /**
     * The parameters used to open a new thread context are stored in this
     * instance variable (e.g. the request parameters from a http servlet are
     * stored in this variable).
     *
     * @see #getParameters
     */
    private final Map<String, String[]> parameters;

    /**
     * A map to be able to set attributes with a lifetime of a request (e.g.
     * servlet request).
     *
     * @see #containsRequestAttribute
     * @see #getRequestAttribute
     * @see #setRequestAttribute
     */
    private final Map<String, Object> requestAttributes = new HashMap<String, Object>();

    /**
     * A map to be able to set attributes with a lifetime of a session (e.g. as
     * long as the user is logged in).
     *
     * @see #containsSessionAttribute
     * @see #getSessionAttribute
     * @see #setSessionAttribute
     */
    private Map<String, Object> sessionAttributes = new HashMap<String, Object>();

    /**
     * Holds the timezone belonging to the user of this context.
     */
    private DateTimeZone timezone;

    /**
     * Holds the locale belonging to the user of this context.
     */
    private Locale locale;

    /**
     * Holds the chronology belonging to the user of this context.
     */
    private Chronology chronology;

    /**
     * Holds the iso code of the language belonging to the user of this context.
     */
    private String language;

    /**
     * If used in a webapp the context path of the webapp can be stored here, so
     * that it is accessible for e.g. esjps.
     */
    private String path;

    /**
     * Id of the request (means normally this instance of the context).
     * Used for cacheing during a request.
     */
    private final String requestId;

    /**
     * Private Constructor.
     *
     * @see #begin(String, Locale, Map, Map, Map)
     *
     * @param _sessionAttributes attributes belonging to this session
     * @param _parameters parameters beloonging to this session
     * @param _fileParameters paramters for file up/download
     * @param _inherit              must the context be inherited to child threads
     * @throws JXMarketJXMarketException on error
     */
    private Context(final Map<String, Object> _sessionAttributes,
                    final Map<String, String[]> _parameters,
                    final Map<String, FileParameter> _fileParameters)
    {
        this.requestId = RandomStringUtils.randomAlphanumeric(8);
        this.parameters = _parameters == null ? new HashMap<String, String[]>() : _parameters;
        this.sessionAttributes = _sessionAttributes == null ? new HashMap<String, Object>() : _sessionAttributes;
    }

    /**
     * Destructor of class <code>Context</code>.
     */
    public void finalize(final String _key)
    {
        THREADCONTEXT.remove(_key);
        if (Context.LOG.isDebugEnabled()) {
            Context.LOG.debug("finalize context for " + this.person);
        }
    }

    /**
     * Close this contexts, meaning this context object is removed as thread
     * context.<br/>
     * If not all connection are closed, all connection are closed.
     *
     */
    public void close()
    {
        if (Context.LOG.isDebugEnabled()) {
            Context.LOG.debug("close context for " + this.person);
        }
    }

    /**
     * If a person is assigned to this context, the id of this person is
     * returned. Otherwise the default person id value is returned. The method
     * guarantees to return value which is valid!<br/>
     * The value could be used e.g. if a a value is inserted into the database
     * and the person id is needed for the creator and / or modifier.
     *
     * @return person id of current person or default person id value
     */
    public long getPersonId()
    {
        long ret = 1;

        if (this.person != null) {
            ret = this.person.getId();
        }
        return ret;
    }

    /**
     * Method to get a parameter from the context.
     *
     * @param _key Key for the parameter
     * @return String value of the parameter
     */
    public String getParameter(final String _key)
    {
        String value = null;
        if (this.parameters != null) {
            final String[] values = this.parameters.get(_key);
            if (values != null && values.length > 0) {
                value = values[0];
            }
        }
        return value;
    }

    /**
     * Getter method for instance variable {@link #path}.
     *
     * @return value of instance variable {@link #path}
     */
    public String getPath()
    {
        return this.path;
    }

    /**
     * Setter method for instance variable {@link #path}.
     *
     * @param _path value for instance variable {@link #path}
     */
    public void setPath(final String _path)
    {
        this.path = _path;
    }

    /**
     * Getter method for the instance variable {@link #requestId}.
     *
     * @return value of instance variable {@link #requestId}
     */
    public String getRequestId()
    {
        return this.requestId;
    }

    /**
     * Returns true if request attributes maps one or more keys to the specified
     * object. More formally, returns <i>true</i> if and only if the request
     * attributes contains at least one mapping to a object o such that (o==null
     * ? o==null : o.equals(o)).
     *
     * @param _key key whose presence in the request attributes is to be tested
     * @return <i>true</i> if the request attributes contains a mapping for
     *         given key, otherwise <i>false</i>
     * @see #requestAttributes
     * @see #getRequestAttribute
     * @see #setRequestAttribute
     */
    public boolean containsRequestAttribute(final String _key)
    {
        return this.requestAttributes.containsKey(_key);
    }

    /**
     * Returns the object to which this request attributes maps the specified
     * key. Returns <code>null</code> if the request attributes contains no
     * mapping for this key. A return value of <code>null</code> does not
     * necessarily indicate that the request attributes contains no mapping for
     * the key; it's also possible that the request attributes explicitly maps
     * the key to null. The {@link #containsRequestAttribute} operation may be
     * used to distinguish these two cases.<br/>
     * More formally, if the request attributes contains a mapping from a key k
     * to a object o such that (key==null ? k==null : key.equals(k)), then this
     * method returns o; otherwise it returns <code>null</code> (there can be at
     * most one such mapping).
     *
     * @param _key key name of the mapped attribute to be returned
     * @return object to which the request attribute contains a mapping for
     *         specified key, or <code>null</code> if not specified in the
     *         request attributes
     * @see #requestAttributes
     * @see #containsRequestAttribute
     * @see #setRequestAttribute
     */
    public Object getRequestAttribute(final String _key)
    {
        return this.requestAttributes.get(_key);
    }

    /**
     * Associates the specified value with the specified key in the request
     * attributes. If the request attributes previously contained a mapping for
     * this key, the old value is replaced by the specified value.
     *
     * @param _key key name of the attribute to set
     * @param _value _value of the attribute to set
     * @return Object
     * @see #requestAttributes
     * @see #containsRequestAttribute
     * @see #getRequestAttribute
     */
    public Object setRequestAttribute(final String _key,
                                      final Object _value)
    {
        return this.requestAttributes.put(_key, _value);
    }

    /**
     * Returns true if session attributes maps one or more keys to the specified
     * object. More formally, returns <i>true</i> if and only if the session
     * attributes contains at least one mapping to a object o such that (o==null
     * ? o==null : o.equals(o)).
     *
     * @param _key key whose presence in the session attributes is to be tested
     * @return <i>true</i> if the session attributes contains a mapping for
     *         given key, otherwise <i>false</i>
     * @see #sessionAttributes
     * @see #getSessionAttribute
     * @see #setSessionAttribute
     */
    public boolean containsSessionAttribute(final String _key)
    {
        return this.sessionAttributes.containsKey(_key);
    }

    /**
     * Returns the object to which this session attributes maps the specified
     * key. Returns <code>null</code> if the session attributes contains no
     * mapping for this key. A return value of <code>null</code> does not
     * necessarily indicate that the session attributes contains no mapping for
     * the key; it's also possible that the session attributes explicitly maps
     * the key to null. The {@link #containsSessionAttribute} operation may be
     * used to distinguish these two cases.<br/>
     * More formally, if the session attributes contains a mapping from a key k
     * to a object o such that (key==null ? k==null : key.equals(k)), then this
     * method returns o; otherwise it returns <code>null</code> (there can be at
     * most one such mapping).
     *
     * @param _key key name of the mapped attribute to be returned
     * @return object to which the session attribute contains a mapping for
     *         specified key, or <code>null</code> if not specified in the
     *         session attributes
     * @see #sessionAttributes
     * @see #containsSessionAttribute
     * @see #setSessionAttribute
     */
    public Object getSessionAttribute(final String _key)
    {
        return this.sessionAttributes.get(_key);
    }

    /**
     * Associates the specified value with the specified key in the session
     * attributes. If the session attributes previously contained a mapping for
     * this key, the old value is replaced by the specified value.
     *
     * @param _key key name of the attribute to set
     * @param _value value of the attribute to set
     * @return Object
     * @see #sessionAttributes
     * @see #containsSessionAttribute
     * @see #getSessionAttribute
     */
    public Object setSessionAttribute(final String _key,
                                      final Object _value)
    {
        return this.sessionAttributes.put(_key, _value);
    }

    /**
     * Remove a attribute form the Session.
     * @param _key key of the session attribute to be removed.
     */
    public void removeSessionAttribute(final String _key)
    {
        this.sessionAttributes.remove(_key);
    }

    /**
     * This is the getter method for instance variable {@link #person}.
     *
     * @return value of instance variable {@link #person}
     * @see #person
     */
    public DTO_User getPerson()
    {
        return this.person;
    }

    /**
     * Get the Company currently valid for this context.
     *
     * @return value of instance variable {@link #company}
     * @throws CacheReloadJXMarketException on error
     */
    public DTO_Company getCompany()
    {
        return this.company;
    }

    /**
     * Set the Company currently valid for this context.
     * @param _company Company to set
     * @throws CacheReloadJXMarketException on error
     */
    public void setCompany(final DTO_Company _company)
    {
        if (_company == null) {
            this.company = null;
        } else {
            this.company = _company;
        }
    }

    /**
     * This is the getter method for instance variable {@link #locale}.
     *
     * @return value of instance variable {@link #locale}
     * @see #locale
     */
    public Locale getLocale()
    {
        return this.locale;
    }

    /**
     * This is the getter method for instance variable {@link #timezone}.
     *
     * @return value of instance variable {@link #timezone}
     * @see #timezone
     */
    public DateTimeZone getTimezone()
    {
        return this.timezone;
    }

    /**
     * This is the getter method for instance variable {@link #chronology}.
     *
     * @return value of instance variable {@link #chronology}
     * @see #locale
     */
    public Chronology getChronology()
    {
        return this.chronology;
    }

    /**
     * Getter method for instance variable {@link #language}.
     *
     * @return value of instance variable {@link #language}
     */
    public String getLanguage()
    {
        return this.language;
    }

    /**
     * This is the getter method for instance variable {@link #parameters}.
     *
     * @return value of instance variable {@link #parameters}
     * @see #parameters
     */
    public Map<String, String[]> getParameters()
    {
        return this.parameters;
    }

    /**
     * The method checks if for the current thread a context object is defined.
     * This found context object is returned.
     *
     * @return defined context object of current thread
     * @throws JXMarketException
     * @throws JXMarketException if no context object for current thread is defined
     * @see #INHERITTHREADCONTEXT
     */
    public static Context getThreadContext(final Desktop _desktop)
        throws JXMarketException
    {
        final Context context = THREADCONTEXT.get((String) _desktop.getSession().getAttribute(Constantes.SYSTEM_KEY));
        if (context == null) {
            throw new JXMarketException(Context.class, "getThreadContext.NoContext4ThreadDefined");
        }
        return context;
    }

    /**
     * For current thread a new context object must be created.
     *
     * @param _user                 instance of current user to set
     * @param _comp                 instance of current company to set
     * @param _sessionAttributes    attributes for this session
     * @param _parameters           map with parameters for this thread context
     * @param _fileParameters       map with file parameters
     * @param _inherit              must the context be inherited to child threads
     * @return new context of thread
     * @throws JXMarketException if a new transaction could not be started or if
     *             current thread context is already set
     * @see #INHERITTHREADCONTEXT
     */
    public static Context begin(final String _key,
                                final DTO_User _user,
                                final DTO_Company _comp,
                                final Map<String, Object> _sessionAttributes,
                                final Map<String, String[]> _parameters,
                                final Map<String, FileParameter> _fileParameters)
        throws JXMarketException
    {
        if (THREADCONTEXT.existContext(_key)) {
            throw new JXMarketException(Context.class, "begin.Context4ThreadAlreadSet");
        }

        final Context context = new Context(_sessionAttributes, _parameters, _fileParameters);
        THREADCONTEXT.set(_key, context);

        if (_user != null) {
            context.person = _user;
            context.locale = context.person.getLocale();
            /*context.timezone = context.person.getTimeZone();
            context.chronology = context.person.getChronology();
            context.language = context.person.getLanguage();*/
            context.company = _comp;
        }
        return context;
    }

    /**
     * Interfaces defining file parameters used to access file parameters (e.g.
     * uploads from the user within the web application).
     */
    public interface FileParameter
    {

        /**
         * Closes the file for this this file parameter is defined (e.g. deletes
         * the file in the temporary directory, if needed).
         *
         * @throws IOException if the close failed
         */
        void close()
            throws IOException;

        /**
         * Returns the input stream of the file for which this file parameter is
         * defined.
         *
         * @return input stream of the file
         * @throws IOException if the input stream could not be returned
         */
        InputStream getInputStream()
            throws IOException;

        /**
         * Returns the size of the file for which this file parameter is
         * defined.
         *
         * @return size of file
         */
        long getSize();

        /**
         * Returns the content type of the file for which this file parameter is
         * defined.
         *
         * @return content type of the file
         */
        String getContentType();

        /**
         * Returns the name of the file for which this file parameter is
         * defined.
         *
         * @return name of file
         */
        String getName();

        /**
         * Returns the name of the parameter for which this file parameter is
         * defined.
         *
         * @return parameter name
         */
        String getParameterName();
    }
}

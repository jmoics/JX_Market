package pe.com.jx_market.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
@MappedTypes(Locale.class)
public class LocaleTypeHandler
    implements TypeHandler<Locale>
{

    /*
     * (non-Javadoc)
     * @see
     * org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement
     * , int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setParameter(final PreparedStatement _ps,
                             final int _i,
                             final Locale _parameter,
                             final JdbcType _jdbcType)
        throws SQLException
    {
        if (_parameter != null) {
            _ps.setString(_i, new StringBuilder(_parameter.getLanguage())
                            .append("_").append(_parameter.getCountry()).toString());
        } else {
            _ps.setString(_i, null);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * java.lang.String)
     */
    @Override
    public Locale getResult(final ResultSet _rs,
                            final String _columnName)
        throws SQLException
    {
        Locale ret = null;
        final String loc = _rs.getString(_columnName);
        if (loc != null) {
            if (loc.indexOf("_") >= 0) {
                ret = new Locale(loc.split("_")[0], loc.split("_")[1]);
            } else {
                ret = new Locale(loc);
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement,
     * int)
     */
    @Override
    public Locale getResult(final CallableStatement _cs,
                            final int _columnIndex)
        throws SQLException
    {
        Locale ret = null;
        final String loc = _cs.getString(_columnIndex);
        if (loc != null) {
            if (loc.indexOf("_") >= 0) {
                ret = new Locale(loc.split("_")[0], loc.split("_")[1]);
            } else {
                ret = new Locale(loc);
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * int)
     */
    @Override
    public Locale getResult(final ResultSet _rs,
                            final int _columnIndex)
        throws SQLException
    {
        Locale ret = null;
        final String loc = _rs.getString(_columnIndex);
        if (loc != null) {
            if (loc.indexOf("_") >= 0) {
                ret = new Locale(loc.split("_")[0], loc.split("_")[1]);
            } else {
                ret = new Locale(loc);
            }
        }
        return ret;
    }

}

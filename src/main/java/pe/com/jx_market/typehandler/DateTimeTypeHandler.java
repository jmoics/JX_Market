package pe.com.jx_market.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
@MappedTypes(DateTime.class)
public class DateTimeTypeHandler
    implements TypeHandler<DateTime>
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
                             final DateTime _parameter,
                             final JdbcType _jdbcType)
        throws SQLException
    {
        if (_parameter != null) {
            _ps.setTimestamp(_i, new Timestamp(_parameter.getMillis()));
        } else {
            _ps.setTimestamp(_i, null);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * java.lang.String)
     */
    @Override
    public DateTime getResult(final ResultSet _rs,
                            final String _columnName)
        throws SQLException
    {
        DateTime ret = null;
        final Timestamp ts = _rs.getTimestamp(_columnName);
        if (ts != null) {
            ret = new DateTime(ts.getTime(), DateTimeZone.UTC);
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
    public DateTime getResult(final CallableStatement _cs,
                            final int _columnIndex)
        throws SQLException
    {
        DateTime ret = null;
        final Timestamp ts = _cs.getTimestamp(_columnIndex);
        if (ts != null) {
            ret = new DateTime(ts.getTime(), DateTimeZone.UTC);
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * int)
     */
    @Override
    public DateTime getResult(final ResultSet _rs,
                            final int _columnIndex)
        throws SQLException
    {
        DateTime ret = null;
        final Timestamp ts = _rs.getTimestamp(_columnIndex);
        if (ts != null) {
            ret = new DateTime(ts.getTime(), DateTimeZone.UTC);
        }
        return ret;
    }

}

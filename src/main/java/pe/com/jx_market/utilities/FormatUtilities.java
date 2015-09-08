package pe.com.jx_market.utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.zkoss.zk.ui.Desktop;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class FormatUtilities
{
    /**
     * @return a format used to format BigDecimal for the user interface
     * @param _desktop ui desktop to get the locale.
     * @param _maxFrac maximum Faction, null to deactivate
     * @param _minFrac minimum Faction, null to activate
     * @throws EFapsException on error
     */
    public static DecimalFormat getFormatter(final Desktop _desktop,
                                             final Integer _minFrac,
                                             final Integer _maxFrac)
    {
        final Locale locale = (Locale) _desktop.getSession().getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
        final DecimalFormat formater = (DecimalFormat) NumberFormat.getInstance(locale);
        if (_maxFrac != null) {
            formater.setMaximumFractionDigits(_maxFrac);
        }
        if (_minFrac != null) {
            formater.setMinimumFractionDigits(_minFrac);
        }
        formater.setMinimumIntegerDigits(1);
        formater.setRoundingMode(RoundingMode.HALF_UP);
        formater.setParseBigDecimal(true);
        return formater;
    }
}

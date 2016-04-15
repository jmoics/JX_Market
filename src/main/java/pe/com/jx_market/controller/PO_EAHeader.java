package pe.com.jx_market.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.Context;
import pe.com.jx_market.utilities.JXMarketException;

/**
 * @author <._.>
 *
 */
public class PO_EAHeader
    extends CommonComposer<Window>
{

    private final Log logger = LogFactory.getLog(PO_EAHeader.class);
    @WireVariable
    private Desktop desktop;
    @Wire
    private Label userdata;
    @Wire
    private Image imaLogo;
    @Wire
    private Combobox cmbLocale;
    private byte[] imgLogoByte;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        loadPhoto(this.company.getDomain());
        setGraficoFoto();

        final DTO_Employee employee = (DTO_Employee)
                        this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_EMPLOYEE);
        if (employee == null) {
            throw new JXMarketException(PO_EAHeader.class, "La sesion se perdio, vuelva a ingresar por favor");
        }
        this.userdata.setValue(employee.getEmployeeLastName() + " " + employee.getEmployeeName());

        getLocaleCombo();
        this.cmbLocale.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
            @Override
            public void onEvent(final Event _event)
                throws Exception
            {
                final String localeValue = ((Combobox) _event.getTarget()).getSelectedItem().getValue();
                if (localeValue != null) {
                    final Locale locale;
                    if (localeValue.indexOf("_") >= 0) {
                        locale = new Locale(localeValue.split("_")[0], localeValue.split("_")[1]);
                    } else {
                        locale = new Locale(localeValue);
                    }
                    desktop.getSession().setAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE, locale);
                    Context.getThreadContext(desktop).setSessionAttribute(Constantes.SYSTEM_LANGUAGE, locale);
                }

                //session.setAttribute("Demo_Locale", self.getSelectedIndex());
                Executions.sendRedirect(null);
            }
        });
    }

    /**
     *
     */
    private void setGraficoFoto()
    {
        if (this.imgLogoByte != null) {
            try {
                this.imaLogo.setContent(new AImage("foto", this.imgLogoByte));
                return;
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        this.imaLogo.setSrc("/media/imagProd.gif");
    }

    /**
     * @param _name
     * @throws JXMarketException on error
     */
    private void loadPhoto(final String _name)
        throws JXMarketException
    {
        final File photo = getPhotoFile(_name);
        if (!photo.exists()) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("No existe archivo de foto " + photo.getName());
            }
            this.imgLogoByte = null;
            return;
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Existe archivo de foto " + photo.getName());
        }
        try {
            final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(photo));
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int n;
            while ((n = bis.read()) != -1) {
                baos.write(n);
            }
            bis.close();
            baos.close();
            this.imgLogoByte = baos.toByteArray();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Cargamos bytes en foto " + this.imgLogoByte.length);
            }
        } catch (final IOException ex) {
            throw new JXMarketException("Error in file reading", ex);
        }
    }

    /**
     * @param _name
     * @return File.
     */
    private File getPhotoFile(final String _name)
    {
        String ruta;
        if (System.getProperty("os.name").contains("Windows")) {
            ruta = Constantes.IMAGES_PATH_WINDOWS + File.separator + _name;
        } else {
            ruta = Constantes.IMAGES_PATH + File.separator + _name;
        }
        return new File(ruta);
    }

    /**
     * @throws JXMarketException on error.
     *
     */
    private void getLocaleCombo()
        throws JXMarketException
    {
        /** TODO si se separa el lenguaje del locale esta parte cambiaria*/
        final Locale currentLocale = (Locale) Context.getThreadContext(this.desktop)
                        .getSessionAttribute(Constantes.SYSTEM_LANGUAGE);
        final Locale[] locales = Locale.getAvailableLocales();
        final List<Locale> listLocales = Arrays.asList(locales);
        Collections.sort(listLocales, new Comparator<Locale>()
        {

            @Override
            public int compare(final Locale _o1,
                               final Locale _o2)
            {
                return _o1.getDisplayLanguage(Locales.getCurrent())
                                .compareTo(_o2.getDisplayLanguage(Locales.getCurrent()));

            }
        });
        final Set<String> setLocale = new HashSet<String>();

        for (final Locale locale : listLocales) {
            if (!setLocale.contains(locale.getLanguage() + "_" + locale.getCountry())) {
                setLocale.add(locale.getLanguage() + "_" + locale.getCountry());
                final Comboitem item = new Comboitem();
                item.setValue(locale.getLanguage() + "_" + locale.getCountry());
                item.setLabel(locale.getDisplayLanguage(currentLocale) + " - "
                                + locale.getDisplayCountry(currentLocale));
                this.cmbLocale.appendChild(item);
                if (currentLocale.getLanguage().equals(locale.getLanguage())
                                && currentLocale.getCountry().equals(locale.getCountry())) {
                    this.cmbLocale.setSelectedItem(item);
                }
            }
        }
    }

    /**
     * @throws JXMarketException on error.
     */
    @Listen("onClick=#btnSalir")
    public void logout()
        throws JXMarketException
    {
        final DTO_User user = (DTO_User) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_USER);
        final String key = encryption("" + user.getId() + user.getCompanyId());
        this.desktop.getSession().removeAttribute("login");
        this.desktop.getSession().removeAttribute("actualizar");
        this.desktop.getSession().removeAttribute("company");
        this.desktop.getSession().removeAttribute("employee");
        Context.getThreadContext(this.desktop).finalize(key);
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("eALogin.zul");
    }
}

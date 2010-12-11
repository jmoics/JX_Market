package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_EAAdministraPerfilModulo
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAAdministraPerfilModulo.class);
    private Grid gr_recursos;
    private Button b_info, b_cancel, b_edit;
    private Foot f_buttons;
    private Combobox cmbArea;

    private BusinessService perfilModuloService, areaService;
    private DTO_Empresa empresa;

    @Override
    public void realOnCreate()
    {
        gr_recursos = (Grid) getFellow("gr_recursos");
        b_info = (Button) getFellow("b_info");
        b_edit = (Button) getFellow("b_edit");
        b_cancel = (Button) getFellow("b_cancel");
        f_buttons = (Foot) getFellow("f_buttons");
        cmbArea = (Combobox) getFellow("cmbArea");
        perfilModuloService = Utility.getService(this, "perfilModuloService");
        areaService = Utility.getService(this, "areaService");
        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");
        cargarAreas(cmbArea);
        //CargarTabla();
    }

    public void editar()
    {
        for (int i = 0; i < gr_recursos.getRows().getChildren().size(); i++) {
            for (int j = 1; j < gr_recursos.getColumns().getChildren().size(); j++) {
                ((Checkbox) gr_recursos.getCell(i, j)).setDisabled(false);
                ((Button) ((Footer) gr_recursos.getFoot().getChildren().get(j)).getChildren().get(0)).setDisabled(false);
            }
        }
        b_edit.setVisible(false);
        b_info.setVisible(true);
        b_cancel.setVisible(true);

    }

    public void cancelar()
    {
        b_edit.setVisible(true);
        b_info.setVisible(false);
        b_cancel.setVisible(false);
        CargarTabla();
    }

    public void pintarColumna(final DTO_Perfil perfil)
    {
        for (int j = 1; j < gr_recursos.getColumns().getChildren().size(); j++) {
            if (((String) ((Column) gr_recursos.getColumns().getChildren().get(j)).getAttribute("perfil"))
                                                                                                    .equals(perfil)) {
                for (int i = 0; i < gr_recursos.getRows().getChildren().size(); i++) {
                    ((Checkbox) gr_recursos.getCell(i, j)).setChecked(true);
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void CargarTabla()
    {
        gr_recursos.getFoot().getChildren().clear();
        gr_recursos.getRows().getChildren().clear();
        gr_recursos.getColumns().getChildren().clear();

        Column columna = new Column();
        columna.appendChild(new Label("Modulos"));

        Footer F = new Footer();
        F.appendChild(new Label(""));
        f_buttons.appendChild(F);
        gr_recursos.getColumns().appendChild(columna);

        final DTO_Modulo modL = new DTO_Modulo();
        modL.setEmpresa(empresa.getCodigo());
        final DTO_Perfil perL = new DTO_Perfil();
        perL.setEmpresa(empresa.getCodigo());
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("perfil", perL);
        map.put("modulo", modL);
        final DTO_Input input = new DTO_Input();
        input.setMapa(map);
        input.setVerbo(Constantes.V_LIST);

        final DTO_Output output = perfilModuloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Modulo> listaMod = output.getLista();
            for (final DTO_Modulo dto : listaMod) {
                final Row fila = new Row();
                fila.setAttribute("modulo", dto);
                fila.appendChild(new Label(dto.getDescripcion()));
                gr_recursos.getRows().appendChild(fila);
                /*
                 * columna = new Column(); columna.setAttribute("codigo",
                 * dto.getCodigo()); columna.appendChild(new
                 * Label(dto.getDescripcion())); columna.setAlign("center");
                 * gr_recursos.getColumns().appendChild(columna);
                 */
            }

            final Map<DTO_Perfil, Set<Integer>> mapa = output.getMapa();
            final Iterator perfilIterator = mapa.keySet().iterator();

            while (perfilIterator.hasNext()) {
                final DTO_Perfil perfil = (DTO_Perfil) perfilIterator.next();
                if(perfil.getArea().equals(((DTO_Area)cmbArea.getSelectedItem().getAttribute("area")).getCodigo())) {
                    columna = new Column();
                    columna.setAttribute("perfil", perfil);
                    columna.appendChild(new Label(perfil.getFuncion()));
                    columna.setAlign("center");

                    final Set perfMod = mapa.get(perfil);
                    // verificar para todos los tipos de bloqueo:
                    int i = 0;
                    for (final DTO_Modulo dto : listaMod) {
                        final Checkbox C = new Checkbox();
                        C.setDisabled(true);
                        C.setAttribute("perfil", perfil);
                        if (perfMod.contains(dto.getCodigo())) {
                            C.setChecked(true);
                        }
                        ((Row) gr_recursos.getRows().getChildren().get(i)).appendChild(C);
                        // columna.appendChild(C);
                        i++;
                    }
                    F = new Footer();

                    final Button todos = new Button("Todos");
                    todos.addEventListener("onClick",
                                    new org.zkoss.zk.ui.event.EventListener() {
                                        @Override
                                        public void onEvent(final Event e)
                                            throws UiException
                                        {
                                            final DTO_Perfil codigo = (DTO_Perfil) e.getTarget().getAttribute("perfil");
                                            pintarColumna(codigo);
                                        }
                                    });
                    todos.setDisabled(true);
                    todos.setAttribute("perfil", perfil);
                    F.appendChild(todos);
                    f_buttons.appendChild(F);
                    gr_recursos.getColumns().appendChild(columna);
                }
            }
        } else {
            alertaError("Error al cargar los modulos por perfil", "Error al cargar los modulos por perfil", null);
        }
    }

    public void actualizar()
    {
        final HashMap<DTO_Perfil, Set<DTO_PerfilModulo>> mapa = new HashMap<DTO_Perfil, Set<DTO_PerfilModulo>>();

        for (int j = 1; j < gr_recursos.getColumns().getChildren().size(); j++) {
            final DTO_Perfil perfil = ((DTO_Perfil) ((Column) gr_recursos.getColumns().getChildren().get(j))
                                                                                            .getAttribute("perfil"));
            final Set<DTO_PerfilModulo> estados = new HashSet<DTO_PerfilModulo>();
            for (int i = 0; i < gr_recursos.getRows().getChildren().size(); i++) {
                if (((Checkbox) gr_recursos.getCell(i, j)).isChecked()) {
                    final DTO_Modulo modulo = ((DTO_Modulo) ((Row) gr_recursos.getRows().getChildren().get(i))
                                                                                            .getAttribute("modulo"));
                    final DTO_PerfilModulo perfMod = new DTO_PerfilModulo();
                    perfMod.setModulo(modulo.getCodigo());
                    perfMod.setPerfil(perfil.getCodigo());
                    estados.add(perfMod);
                }
            }
            System.out.println("ESTADOS:" + estados);
            System.out.println("PERFIL :" + perfil);
            mapa.put(perfil, estados);

        }

        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_REGISTER);
        input.setMapa(mapa);
        final DTO_Output output = perfilModuloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            CargarTabla();
            b_edit.setVisible(true);
            b_info.setVisible(false);
            b_cancel.setVisible(false);
            logger.info("Recursos registrados correctamente");
            alertaInfo("", "Los cambios se guardaron correctamente", null);
        } else {
            logger.error("Error al registrar bloqueos");
        }
    }

    private void cargarAreas(final Combobox combo)
    {
        final DTO_Area ar = new DTO_Area();
        ar.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(ar);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> listado = output.getLista();
            for (final DTO_Area area : listado) {
                final Comboitem item = new Comboitem(area.getNombre());
                item.setAttribute("area", area);
                combo.appendChild(item);
            }
        } else {
            alertaError("Error en la carga de areas",
                            "error al cargar los areas", null);
        }
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
            if (t != null) {
                logger.info(txt2, t);
            } else {
                logger.info(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
            if (t != null) {
                logger.error(txt2, t);
            } else {
                logger.error(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULO_ADM_PERFILMODULO };
    }
}

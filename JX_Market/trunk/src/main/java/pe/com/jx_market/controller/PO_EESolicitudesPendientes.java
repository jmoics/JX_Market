package pe.com.jx_market.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;
import pe.com.jx_market.domain.DTO_Solicitud;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_EESolicitudesPendientes extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EESolicitudesPendientes.class);
    private BusinessService solicitudService, empresaService, areaService, perfilService, moduloService, perfilModuloService, empleadoService;
    private Listbox lstSolicitud;

    @Override
    void realOnCreate()
    {
        lstSolicitud = (Listbox) getFellow("lstSolicitud");
        solicitudService = Utility.getService(this, "solicitudService");
        empresaService = Utility.getService(this, "empresaService");
        areaService = Utility.getService(this, "areaService");
        perfilService = Utility.getService(this, "perfilService");
        moduloService = Utility.getService(this, "moduloService");
        perfilModuloService = Utility.getService(this, "perfilModuloService");
        empleadoService = Utility.getService(this, "empleadoService");
        busquedaSolicitudes();
    }

    public void onLimpiar()
    {
        lstSolicitud.setVisible(false);
        lstSolicitud.getItems().clear();
    }

    @SuppressWarnings("unchecked")
    public void busquedaSolicitudes()
    {
        lstSolicitud.getItems().clear();
        final DTO_Solicitud solAux = new DTO_Solicitud();
        solAux.setEstado(Constantes.ST_PENDIENTE);

        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_LIST);
        input.setObject(solAux);
        final DTO_Output output = solicitudService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Solicitud> lstSol = output.getLista();
            for (final DTO_Solicitud solicitud : lstSol) {
                final Listitem item = new Listitem();

                Listcell cell = new Listcell();
                cell.setLabel(solicitud.getRazon());
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(solicitud.getRuc());
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(solicitud.getCorreo());
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(new SimpleDateFormat("dd/MM/yyyy").format(solicitud.getFecha()));
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(Constantes.STATUS_PENDIENTE);
                cell.setParent(item);

                item.setAttribute("solicitud", solicitud);

                lstSolicitud.appendChild(item);
            }
        }

    }

    public void aceptarSolicitud() {
        final DTO_Solicitud sol = (DTO_Solicitud) lstSolicitud.getSelectedItem().getAttribute("solicitud");
        sol.setEstado(Constantes.ST_ACTIVO);
        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_REGISTER);
        input.setObject(sol);
        final DTO_Output output = solicitudService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            generarData(sol);
        }
    }

    public void rechazarSolicitud() {
        final DTO_Solicitud sol = (DTO_Solicitud) lstSolicitud.getSelectedItem().getAttribute("solicitud");
        sol.setEstado(Constantes.ST_CANCELADO);
        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_REGISTER);
        input.setObject(sol);
        final DTO_Output output = solicitudService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("Se cambio correctamente el estado", "estado cambiado correctamente", null);
        } else {
            alertaError("Ocurrio un error al modificar el estado", "error modificando estado", null);
        }
    }

    private void generarData(final DTO_Solicitud sol) {
        final DTO_Empresa empresa = new DTO_Empresa();
        empresa.setDominio(sol.getRazon());
        empresa.setEstado(Constantes.ST_ACTIVO);
        empresa.setRazonsocial(sol.getRazon());
        empresa.setRuc(sol.getRuc());

        DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_REGISTER);
        input.setObject(empresa);
        DTO_Output output = empresaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Integer codEmp = (Integer) output.getObject();
            final DTO_Area area = new DTO_Area();
            area.setEmpresa(codEmp);
            area.setNombre("Administrator");
            input = new DTO_Input();
            input.setVerbo(Constantes.V_REGISTER);
            input.setObject(area);
            output = areaService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final Integer codArea = (Integer) output.getObject();
                final DTO_Perfil perf = new DTO_Perfil();
                perf.setArea(codArea);
                perf.setDescripcion("Administration");
                perf.setEmpresa(codEmp);
                perf.setFuncion("Administrator");
                input = new DTO_Input();
                input.setVerbo(Constantes.V_REGISTER);
                input.setObject(perf);
                output = perfilService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    final Integer codPerf = (Integer) output.getObject();
                    Integer codMod = insertModulo("Modulo para administracion", Constantes.MODULO_ADMINISTRACION, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("modulo para administracion de areas", Constantes.MODULO_ADM_AREA, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para administracion de empleados", Constantes.MODULO_ADM_EMPLEADO, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para administracion de modulos", Constantes.MODULO_ADM_MODULO, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para administracion de perfiles", Constantes.MODULO_ADM_PERFIL, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para asignacion de modulos a perfiles", Constantes.MODULO_ADM_PERFILMODULO, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para consulta de productos", Constantes.MODULO_PROD_CONSULTA, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para edicion de productos", Constantes.MODULO_PROD_EDICION, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para ingreso de productos", Constantes.MODULO_PROD_INGRESO, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para inventario", Constantes.MODULO_PROD_INVENTARIO, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para productos", Constantes.MODULO_PRODUCTOS, codEmp);
                    connectPerfilModulo(codPerf, codMod);
                    codMod = insertModulo("Modulo para administrar la contrasena", Constantes.MODULO_CHANGE_PASS, codEmp);
                    connectPerfilModulo(codPerf, codMod);

                    final Map<String, Object> mapUser = new HashMap<String,Object>();
                    final DTO_Usuario usuario = new DTO_Usuario();
                    usuario.setEmpresa(codEmp);
                    usuario.setUsername("Administrator");
                    usuario.setContrasena("Administrator");

                    final DTO_Empleado empleado = new DTO_Empleado();
                    empleado.setApellido("Administrator");
                    empleado.setEmpresa(codEmp);
                    empleado.setEstado(Constantes.ST_ACTIVO);
                    empleado.setPerfil(codPerf);

                    mapUser.put("empleado", empleado);
                    mapUser.put("usuario", usuario);

                    input = new DTO_Input();
                    input.setVerbo(Constantes.V_REGISTER);
                    input.setMapa(mapUser);
                    output = empleadoService.execute(input);
                    if (output.getErrorCode() == Constantes.OK) {
                        alertaInfo("Se creao la data basica para la nueva empresa", "nueva data registrada correctamente", null);
                    }
                }
            }
        }
    }

    private Integer insertModulo(final String desc, final String recurso, final Integer empresa) {
        final DTO_Modulo mod = new DTO_Modulo();
        mod.setDescripcion(desc);
        mod.setEmpresa(empresa);
        mod.setRecurso(recurso);

        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_REGISTER);
        input.setObject(mod);
        final DTO_Output output = moduloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Integer cod = (Integer) output.getObject();
            return cod;
        } else {
            return null;
        }
    }

    private Boolean connectPerfilModulo(final Integer perf, final Integer mod) {
        final DTO_PerfilModulo perfMod = new DTO_PerfilModulo();
        perfMod.setModulo(mod);
        perfMod.setPerfil(perf);

        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_REGISTERPM);
        input.setObject(perfMod);
        final DTO_Output output = perfilModuloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return true;
        } else {
            return false;
        }
    }

    public void salir()
    {
        getDesktop().getSession().removeAttribute("login");
        getDesktop().getSession().removeAttribute("actualizar");
        getDesktop().getSession().removeAttribute("empresa");
        getDesktop().getSession().removeAttribute("empleado");
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("eALogin.zul");
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, "JX_Market", 1, Messagebox.INFORMATION);
        }
        if (t != null) {
            logger.info(txt2, t);
        } else {
            logger.info(txt2);
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, "JX_Market", 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }

    }

    @Override
    String[] requiredResources() {
        // TODO Auto-generated method stub
        return null;
    }

}

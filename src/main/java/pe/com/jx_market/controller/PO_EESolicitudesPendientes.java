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
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.domain.DTO_Solicitud;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EESolicitudesPendientes extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EESolicitudesPendientes.class);
    private BusinessService solicitudService, companyService, areaService, roleService, moduleService, roleModuleService, employeeService;
    private Listbox lstSolicitud;

    @Override
    void realOnCreate()
    {
        lstSolicitud = (Listbox) getFellow("lstSolicitud");
        solicitudService = ContextLoader.getService(this, "solicitudService");
        companyService = ContextLoader.getService(this, "companyService");
        areaService = ContextLoader.getService(this, "areaService");
        roleService = ContextLoader.getService(this, "roleService");
        moduleService = ContextLoader.getService(this, "moduleService");
        roleModuleService = ContextLoader.getService(this, "roleModuleService");
        employeeService = ContextLoader.getService(this, "employeeService");
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

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_LIST);
        input.setObject(solAux);
        final ServiceOutput output = solicitudService.execute(input);
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
        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTER);
        input.setObject(sol);
        final ServiceOutput output = solicitudService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            generarData(sol);
        }
    }

    public void rechazarSolicitud() {
        final DTO_Solicitud sol = (DTO_Solicitud) lstSolicitud.getSelectedItem().getAttribute("solicitud");
        sol.setEstado(Constantes.ST_CANCELADO);
        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTER);
        input.setObject(sol);
        final ServiceOutput output = solicitudService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("Se cambio correctamente el estado", "estado cambiado correctamente", null);
        } else {
            alertaError("Ocurrio un error al modificar el estado", "error modificando estado", null);
        }
    }

    private void generarData(final DTO_Solicitud sol) {
        final DTO_Company company = new DTO_Company();
        company.setDomain(sol.getRazon());
        company.setActive(Constantes.ST_ACTIVO);
        company.setBusinessName(sol.getRazon());
        company.setDocNumber(sol.getRuc());

        ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTER);
        input.setObject(company);
        ServiceOutput output = companyService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Integer codEmp = (Integer) output.getObject();
            final DTO_Area area = new DTO_Area();
            area.setCompanyId(codEmp);
            area.setAreaName("Administrator");
            input = new ServiceInput();
            input.setAccion(Constantes.V_REGISTER);
            input.setObject(area);
            output = areaService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final Integer codArea = (Integer) output.getObject();
                final DTO_Role perf = new DTO_Role();
                perf.setAreaId(codArea);
                perf.setRoleDescription("Administration");
                perf.setCompanyId(codEmp);
                perf.setRoleName("Administrator");
                input = new ServiceInput();
                input.setAccion(Constantes.V_REGISTER);
                input.setObject(perf);
                output = roleService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    final Integer codPerf = (Integer) output.getObject();
                    Integer codMod = insertModule("Module para administracion", Constantes.MODULE_ADMINISTRACION, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("module para administracion de areas", Constantes.MODULE_ADM_AREA, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para administracion de employees", Constantes.MODULE_ADM_EMPLOYEE, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para administracion de modules", Constantes.MODULE_ADM_MODULE, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para administracion de roles", Constantes.MODULE_ADM_ROLE, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para asignacion de modules a roles", Constantes.MODULE_ADM_ROLEMODULE, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para consulta de productos", Constantes.MODULE_PROD_PRODUCT, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para edicion de productos", Constantes.MODULE_PROD_INVENTORY, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para ingreso de productos", Constantes.MODULE_PROD_CATEGORY, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para inventario", Constantes.MODULE_PROD_AMOUNT, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para productos", Constantes.MODULE_PRODUCTS, codEmp);
                    connectRoleModule(codPerf, codMod);
                    codMod = insertModule("Module para administrar la contrasena", Constantes.MODULE_CHANGE_PASS, codEmp);
                    connectRoleModule(codPerf, codMod);

                    final Map<String, Object> mapUser = new HashMap<String,Object>();
                    final DTO_User user = new DTO_User();
                    user.setCompanyId(codEmp);
                    user.setUsername("Administrator");
                    user.setPassword("Administrator");

                    final DTO_Employee employee = new DTO_Employee();
                    employee.setEmployeeLastName("Administrator");
                    employee.setCompanyId(codEmp);
                    employee.setActive(Constantes.STB_ACTIVO);
                    employee.setRoleId(codPerf);

                    mapUser.put("employee", employee);
                    mapUser.put("user", user);

                    input = new ServiceInput();
                    input.setAccion(Constantes.V_REGISTER);
                    input.setMapa(mapUser);
                    output = employeeService.execute(input);
                    if (output.getErrorCode() == Constantes.OK) {
                        alertaInfo("Se creao la data basica para la nueva company", "nueva data registrada correctamente", null);
                    }
                }
            }
        }
    }

    private Integer insertModule(final String desc, final String recurso, final Integer company) {
        final DTO_Module mod = new DTO_Module();
        mod.setDescription(desc);
        mod.setCompanyId(company);
        mod.setResource(recurso);

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTER);
        input.setObject(mod);
        final ServiceOutput output = moduleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Integer cod = (Integer) output.getObject();
            return cod;
        } else {
            return null;
        }
    }

    private Boolean connectRoleModule(final Integer perf, final Integer mod) {
        final DTO_RoleModule perfMod = new DTO_RoleModule();
        perfMod.setModuleId(mod);
        perfMod.setRole(perf);

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTERPM);
        input.setObject(perfMod);
        final ServiceOutput output = roleModuleService.execute(input);
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
        getDesktop().getSession().removeAttribute("company");
        getDesktop().getSession().removeAttribute("employee");
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

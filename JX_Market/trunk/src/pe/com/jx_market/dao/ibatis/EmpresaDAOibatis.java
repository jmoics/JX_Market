/**
 * 
 */
package pe.com.jx_market.dao.ibatis;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.EmpresaDAO;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;
/**
 * @author George
 *
 */
public class EmpresaDAOibatis extends SqlMapClientDaoSupport implements EmpresaDAO {

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#getEmpresaXCodigo(java.lang.String)
     */
    @Override
    public DTO_Empresa getEmpresaXCodigo (String codigo) {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", codigo);
        return (DTO_Empresa) getSqlMapClientTemplate().queryForObject("getEmpresaPorCodigo", mapa);
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#eliminaEmpresa(java.lang.String)
     */
    @Override
    public boolean eliminaEmpresa (String codigo) {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", codigo);
        getSqlMapClientTemplate().delete("deleteEmpresa", mapa);
        return false;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#registraEmpresa(pe.com.jx_market.domain.DTO_Empresa)
     */
    @Override
    public boolean registraEmpresa (DTO_Empresa empresa) {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", empresa.getCodigo());
        mapa.put("empresa_v_nombre", empresa.getNombre());
        mapa.put("empresa_n_activo", empresa.getEstado());
        mapa.put("empresa_n_razonsocial", empresa.getRuc());

        DTO_Empresa emp = (DTO_Empresa) getSqlMapClientTemplate().queryForObject(
                "getEmpresaPorCodigo", mapa);

        if (emp == null) {
            getSqlMapClientTemplate().insert("insertEmpresa", mapa);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateEmpresa", mapa);
            return true;
        }
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#getEmpresas()
     */
    @Override
    public List<DTO_Empresa> getEmpresas (String nombre, Integer ruc) {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_v_nombre", nombre);
        mapa.put("empresa_n_razonsocial", ruc);
        return (List<DTO_Empresa>)getSqlMapClientTemplate().queryForList("getEmpresas", mapa);
    }
}

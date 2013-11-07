/**
 *
 */
package pe.com.jx_market.dao.ibatis;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.EmpresaDAO;
import pe.com.jx_market.domain.DTO_Empresa;
/**
 * @author George
 *
 */
public class EmpresaDAOibatis extends SqlMapClientDaoSupport implements EmpresaDAO {

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#getEmpresaXCodigo(java.lang.String)
     */
    @Override
    public DTO_Empresa getEmpresaXCodigo (final String codigo) {
        final HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", codigo);
        return (DTO_Empresa) getSqlMapClientTemplate().queryForObject("getEmpresaPorCodigo", mapa);
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#eliminaEmpresa(java.lang.String)
     */
    @Override
    public boolean eliminaEmpresa (final String codigo) {
        final HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", codigo);
        getSqlMapClientTemplate().delete("deleteEmpresa", mapa);
        return false;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#registraEmpresa(pe.com.jx_market.domain.DTO_Empresa)
     */
    @Override
    public Integer registraEmpresa (final DTO_Empresa empresa) {
        final HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", empresa.getCodigo());
        mapa.put("empresa_v_razonsocial", empresa.getRazonsocial());
        mapa.put("empresa_n_activo", empresa.getEstado());
        mapa.put("empresa_v_ruc", empresa.getRuc());

        final DTO_Empresa emp = (DTO_Empresa) getSqlMapClientTemplate().queryForObject(
                "getEmpresaPorCodigo", mapa);

        Integer ret = -1;
        if (emp == null) {
            ret = (Integer) getSqlMapClientTemplate().insert("insertEmpresa", empresa);
            return ret;
        } else {
            getSqlMapClientTemplate().update("updateEmpresa", mapa);
            return ret;
        }
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.EmpresaDAO#getEmpresas()
     */
    @Override
    public List<DTO_Empresa> getEmpresas (final String razonsocial, final String ruc) {
        final HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_v_razonsocial", razonsocial);
        mapa.put("empresa_v_ruc", ruc);
        return getSqlMapClientTemplate().queryForList("getEmpresas", mapa);
    }
}

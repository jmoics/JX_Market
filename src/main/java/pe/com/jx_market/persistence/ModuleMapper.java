package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Module;

public interface ModuleMapper
{
    /**
     * @param module
     * @return
     */
    public List<DTO_Module> getModules(DTO_Module module);

    /**
     * @param module
     * @return
     */
    public DTO_Module getModuleXId(DTO_Module module);

    /**
     * @param module
     * @return
     */
    public Integer insertModule(DTO_Module module);

    /**
     * @param module
     * @return
     */
    public Integer updateModule(DTO_Module module);

    /**
     * @param module
     * @return
     */
    public boolean deleteModule(DTO_Module module);
}

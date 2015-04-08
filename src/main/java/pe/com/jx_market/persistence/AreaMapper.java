package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Area;

public interface AreaMapper
{
    /**
     * @param area
     * @return
     */
    public List<DTO_Area> getAreas(DTO_Area area);

    /**
     * @param area
     * @return
     */
    public DTO_Area getAreaXCodigo(DTO_Area area);

    /**
     * @param area
     * @return
     */
    public Integer insertArea(DTO_Area area);
    
    /**
     * @param area
     * @return
     */
    public Integer updateArea(DTO_Area area);

    /**
     * @param area
     * @return
     */
    public boolean deleteArea(DTO_Area area);
}

/**
 * 
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Category;

/**
 * @author George
 * 
 */
public interface CategoryMapper
{
    /**
     * @param category
     * @return
     */
    public List<DTO_Category> getCategories(DTO_Category category);
    
    /**
     * @param category
     * @return
     */
    public DTO_Category getCategory4Id(DTO_Category category);

    /**
     * @param category
     * @return
     */
    public boolean insertCategory(DTO_Category category);
    
    /**
     * @param category
     * @return
     */
    public boolean updateCategory(DTO_Category category);

    /**
     * @param category
     * @return
     */
    public boolean deleteCategory(DTO_Category category);
}

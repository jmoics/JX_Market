/**
 *
 */
package pe.com.jx_market.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_ProductImage;
import pe.com.jx_market.domain.DTO_TradeMark;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoryTreeNode;
import pe.com.jx_market.utilities.CategoryTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAProductsCreate
    extends SecuredComposer<Window>
{
    private static final long serialVersionUID = -5294436527686469836L;

    private final Log logger = LogFactory.getLog(PO_EAProductsCreate.class);
    @Wire
    private Combobox cmbStatus, cmbTradeMark;
    @Wire
    private Textbox txtNombre, txtDesc;
    @Wire
    private Image imgFoto;
    @Wire
    private Label lbImageName, lbImageSize;
    @Wire
    private Window wEAPC;
    @Wire
    private Tree tree;
    @Wire
    private Listbox lstImages;
    @Wire
    private Include incPricelistRetail;
    @Wire
    private Include incPricelistCost;
    @WireVariable
    private BusinessService<DTO_Product> productService;
    @WireVariable
    private BusinessService<DTO_Category> categoryService;
    @WireVariable
    private BusinessService<DTO_TradeMark> tradeMarkService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private DTO_Product product;
    private CategoryTreeNode categoryTreeNode;
    private AdvancedTreeModel categoryTreeModel;
    private PO_EAProducts productParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.company = (DTO_Company) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        buildActiveCombo(this.cmbStatus);
        this.product = null;

        this.desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_PRODUCT);
        this.tree.setCheckmark(true);
        this.tree.setMultiple(true);
        this.categoryTreeNode = getCategories();
        this.categoryTreeModel = new AdvancedTreeModel(this.categoryTreeNode);
        this.categoryTreeModel.setMultiple(true);
        this.tree.setItemRenderer(new CategoryTreeRenderer());
        this.tree.setModel(this.categoryTreeModel);

        buildTradeMarkCombo();

        this.incPricelistRetail.setAttribute(Constantes.V_REGISTERPRICE, Constantes.PRICELIST_RETAIL);
        this.incPricelistRetail.setSrc(Constantes.Form.PRICELIST_FORM.getForm());
        this.incPricelistCost.setAttribute(Constantes.V_REGISTERPRICE, Constantes.PRICELIST_COST);
        this.incPricelistCost.setSrc(Constantes.Form.PRICELIST_FORM.getForm());

        // Obtenemos el controlador de la pantalla principal de marcas.
        final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
        this.productParentUI = (PO_EAProducts) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);

        final DTO_User user = (DTO_User) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_USER);
        checkResources2(user);
        setVisibilityByResource2(_comp, "btnSave", user);
        setVisibilityByResource2(_comp, "btnSave2", user);
        setVisibilityByResource2(_comp, "btnSave3", user);
    }

    /**
     * Build combo of trademarks.
     */
    private void buildTradeMarkCombo()
    {
        final DTO_TradeMark marFi = new DTO_TradeMark();
        marFi.setCompanyId(this.company.getId());
        final ServiceInput<DTO_TradeMark> input = new ServiceInput<DTO_TradeMark>(marFi);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_TradeMark> output = this.tradeMarkService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(this.logger, "", "Exito al cargar categories", null);
            final List<DTO_TradeMark> lstMar = output.getLista();
            for (final DTO_TradeMark marIt : lstMar) {
                final Comboitem item = new Comboitem();
                item.setLabel(marIt.getTradeMarkName());
                item.setValue(marIt);
                this.cmbTradeMark.appendChild(item);
            }
        } else {
            alertaError(this.logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando marcas", null);
        }
    }

    /**
     * Method to create the product.
     */
    @Listen("onClick = #btnSave")
    public void createProduct()
    {
        if (!this.txtNombre.getValue().equals("") && !this.txtDesc.getValue().equals("")
                        && this.cmbTradeMark.getSelectedItem() != null && this.cmbStatus.getSelectedItem() != null) {
            final DTO_Product product = new DTO_Product();
            // product.setCategory(((DTO_Category)
            // cmbCateg.getSelectedItem().getAttribute("category")).getCodigo());
            product.setActive((Boolean) this.cmbStatus.getSelectedItem().getValue());
            product.setTradeMark((DTO_TradeMark) this.cmbTradeMark.getSelectedItem().getValue());
            product.setProductDescription(this.txtDesc.getValue());
            product.setCompanyId(this.company.getId());
            // product.setTradeMark(txtTradeMark.getValue());
            product.setProductName(this.txtNombre.getValue());
            // product.setPrecio(decPrec.getValue());
            final ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(product);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Product> output = this.productService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info.Label"), null);
                this.product = product;
                this.product.setCategories(new ArrayList<DTO_Category>());
                this.product.setImages(new ArrayList<DTO_ProductImage>());
                if (resp == Messagebox.OK) {
                    this.productParentUI.searchProducts();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.PRODUCTS_FORM.getForm());
                    // wEAEP.detach();
                }
            } else {
                alertaError(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Error.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Error.Label"), null);
            }
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info2.Label"), null);
        }
    }

    /**
     * @param _event Event parameter.
     */
    @Listen("onClick = #btnClose, #btnClose2, #btnClose3")
    public void accionCerrar(final Event _event)
    {
        this.wEAPC.detach();
    }

    /**
     * @param _event Event parameter.
     */
    @Listen("onClick = #btnSave2")
    public void saveCategories(final Event _event)
    {
        if (this.product != null) {
            final Map<Integer, DTO_Category> mapCat4Prod = new HashMap<Integer, DTO_Category>();
            for (final DTO_Category cat : this.product.getCategories()) {
                mapCat4Prod.put(cat.getId(), cat);
            }
            if (this.tree.getItems() != null && !this.tree.getItems().isEmpty()) {
                final DTO_Product prod4DelCat = new DTO_Product();
                prod4DelCat.setId(this.product.getId());
                prod4DelCat.setCompanyId(this.product.getCompanyId());
                prod4DelCat.setCategories(new ArrayList<DTO_Category>());
                for (final Treeitem item : this.tree.getItems()) {
                    saveCategories(item, mapCat4Prod, prod4DelCat);
                }

                ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(this.product);
                input.setAction(Constantes.V_REGISTERCAT4PROD);
                ServiceOutput<DTO_Product> output = this.productService.execute(input);
                boolean correct = true;
                if (output.getErrorCode() != Constantes.OK) {
                    alertaError(this.logger, "", "Error al guardar nuevas categories para el producto", null);
                    correct = false;
                }
                input = new ServiceInput<DTO_Product>(prod4DelCat);
                input.setAction(Constantes.V_DELETECAT4PROD);
                output = this.productService.execute(input);
                if (output.getErrorCode() != Constantes.OK) {
                    alertaError(this.logger, "", "Error al eliminar categories para el producto", null);
                    correct = false;
                }
                if (correct) {
                    alertaInfo(this.logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveCategories.Info.Label"),
                                    "", null);
                } else {
                    alertaError(this.logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveCategories.Error.Label"),
                                    "Error al guardar o eliminar categories para el producto", null);
                }
            }
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveCategories.Info2.Label"),
                            "Ningun producto a sido creado previamente", null);
        }
    }

    /**
     * @param _item Item of tree.
     * @param _mapCat4Prod Map of products for categories.
     * @param _prod4DelCat to delete.
     */
    private void saveCategories(final Treeitem _item,
                                final Map<Integer, DTO_Category> _mapCat4Prod,
                                final DTO_Product _prod4DelCat)
    {
        final CategoryTreeNode ctn = _item.getValue();
        final DTO_Category categ = ctn.getData();

        if (_item.isSelected() && !_mapCat4Prod.containsKey(categ.getId())) {
            this.product.getCategories().add(categ);
        } else if (!_item.isSelected() && _mapCat4Prod.containsKey(categ.getId())) {
            for (int cnt = 0; cnt < this.product.getCategories().size(); cnt++) {
                if (this.product.getCategories().get(cnt).getId().equals(categ.getId())) {
                    _prod4DelCat.getCategories().add(this.product.getCategories().get(cnt));
                    this.product.getCategories().remove(cnt);
                }
            }
        }
        if (_item.getTreechildren() != null) {
            for (int i = 0; i < _item.getTreechildren().getChildren().size(); i++) {
                saveCategories((Treeitem) _item.getTreechildren().getChildren().get(i), _mapCat4Prod, _prod4DelCat);
            }
        }
    }

    /**
     * @return Structure of categories.
     */
    public CategoryTreeNode getCategories()
    {
        final DTO_Category cat = new DTO_Category();
        cat.setCompanyId(this.company.getId());
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>(cat);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Category> output = this.categoryService.execute(input);
        CategoryTreeNode categoryTreeNode = null;
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Category> lstCat = output.getLista();
            categoryTreeNode = buildCategoriesTree(lstCat);
        } else {
            alertaError(this.logger, Labels.getLabel("pe.com.jx_market.Error.Label"),
                            "Error cargando categories", null);
        }
        return categoryTreeNode;
    }

    /**
     * @param _categories list of categories.
     * @return tree node with the structure.
     */
    private CategoryTreeNode buildCategoriesTree(final List<DTO_Category> _categories)
    {
        final Map<Integer, DTO_Category> mapCateg = new TreeMap<Integer, DTO_Category>();
        final Map<Integer, DTO_Category> roots = new TreeMap<Integer, DTO_Category>();
        final Map<Integer, DTO_Category> childs = new TreeMap<Integer, DTO_Category>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Category cat : _categories) {
            mapCateg.put(cat.getId(), cat);
            setPadres.add(cat.getCategoryParentId());
            if (cat.getCategoryParentId() == null) {
                roots.put(cat.getId(), cat);
            } else {
                childs.put(cat.getId(), cat);
            }
        }

        final CategoryTreeNode categoryTreeNode = new CategoryTreeNode(null,
            new CategoryTreeNodeCollection()
            {

                private static final long serialVersionUID = -8249078122595873454L;
                {
                    for (final DTO_Category root : roots.values()) {
                        if (!setPadres.contains(root.getId())) {
                            add(new CategoryTreeNode(root));
                        } else {
                            add(new CategoryTreeNode(root,
                                new CategoryTreeNodeCollection()
                                {

                                    private static final long serialVersionUID = -5643408533240445491L;
                                    {
                                        construirJerarquia(childs.values(), root, setPadres,
                                                        false);
                                    }
                                }, true));
                        }
                    }
                }
            },
            true);
        return categoryTreeNode;
    }

    /**
     * Class to render the tree.
     *
     * @author jcuevas
     * @version $Id$
     */
    private final class CategoryTreeRenderer
        implements TreeitemRenderer<CategoryTreeNode>
    {

        @Override
        public void render(final Treeitem _treeItem,
                           final CategoryTreeNode _treeNode,
                           final int _index)
            throws Exception
        {
            final Map<Integer, DTO_Category> mapCat4Prod = new HashMap<Integer, DTO_Category>();
            if (product != null) {
                for (final DTO_Category cat : product.getCategories()) {
                    mapCat4Prod.put(cat.getId(), cat);
                }
            }
            final CategoryTreeNode ctn = _treeNode;
            final DTO_Category categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(_treeItem);
            _treeItem.setValue(ctn);
            _treeItem.setOpen(ctn.isOpen());
            if (mapCat4Prod.containsKey(categ.getId())) {
                _treeItem.setSelected(true);
            }

            final Hlayout hl = new Hlayout();
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            hl.appendChild(new Label(categ.getCategoryName()));
            hl.setSclass("h-inline-block");
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.appendChild(treeCell);
            final Treecell treeCell2 = new Treecell();
            final Hlayout h2 = new Hlayout();
            h2.appendChild(new Label(categ.isActive() ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO));
            treeCell2.appendChild(h2);
            dataRow.appendChild(treeCell2);

            _treeItem.addEventListener(Events.ON_CLICK, new EventListener<Event>()
            {

                @Override
                public void onEvent(final Event _event)
                    throws Exception
                {
                    final Treeitem item = (Treeitem) _event.getTarget();
                    if (item.isSelected() && item.getParentItem() != null
                                    && item.getParentItem().getValue() != null) {
                        modificarPadres(item.getParentItem(), item.isSelected());
                    }
                    if (!item.isSelected() && item.getTreechildren() != null) {
                        for (int i = 0; i < item.getTreechildren().getChildren().size(); i++) {
                            final Treeitem comp = (Treeitem) item.getTreechildren().getChildren().get(i);
                            if (comp instanceof Treeitem) {
                                final Treeitem itemChild = comp;
                                modificarHijos(itemChild, item.isSelected());
                            }
                        }
                    }
                }

                private void modificarHijos(final Treeitem _item,
                                            final boolean _checked)
                {
                    _item.setSelected(_checked);
                    if (_item.getTreechildren() != null) {
                        for (int i = 0; i < _item.getTreechildren().getItemCount(); i++) {
                            modificarHijos((Treeitem) _item.getTreechildren().getChildren().get(i), _checked);
                        }
                    }
                }

                private void modificarPadres(final Treeitem _item,
                                             final boolean _checked)
                {
                    _item.setSelected(_checked);
                    if (_checked && _item.getParentItem() != null
                                    && _item.getParentItem().getValue() != null) {
                        modificarPadres(_item.getParentItem(), _checked);
                    }
                }
            });
        }
    }

    /**
     * @param _media Media with images.
     */
    private void setImage(final Media _media)
    {
        if (_media != null) {
            final byte[] imgProducto = _media.getByteData();
            if (imgProducto != null) {
                try {
                    this.imgFoto.setContent(new AImage("foto", imgProducto));
                    this.lbImageName.setValue(_media.getName());
                    this.lbImageSize.setValue("" + _media.getByteData().length);

                    final DTO_ProductImage img4Prod = new DTO_ProductImage();
                    img4Prod.setCompanyId(this.company.getId());
                    img4Prod.setImage(this.imgFoto.getContent().getByteData());
                    img4Prod.setImageName(_media.getName());

                    final Listitem item = new Listitem();
                    item.setAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE, true);
                    item.setValue(img4Prod);
                    Listcell cell = new Listcell();
                    cell.setLabel(_media.getName());
                    item.appendChild(cell);
                    cell = new Listcell();
                    cell.setLabel("" + _media.getByteData().length);
                    item.appendChild(cell);
                    item.setParent(this.lstImages);
                    item.addEventListener(Events.ON_CLICK, new EventListener<Event>()
                    {

                        @Override
                        public void onEvent(final Event _event)
                            throws Exception
                        {
                            final Listitem lItem = (Listitem) _event.getTarget();
                            final DTO_ProductImage img = lItem.getValue();
                            lbImageName.setValue(img.getImageName());
                            lbImageSize.setValue("" + img.getImage().length);
                            imgFoto.setContent(new AImage("foto", img.getImage()));
                        }
                    });
                    return;
                } catch (final IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        this.imgFoto.setSrc("/media/silueta.gif");
    }

    /**
     * @param _event Event parameter.
     * @throws Exception on Error.
     */
    @Listen("onUpload = #btnUpload")
    public void uploadPhoto(final UploadEvent _event)
        throws Exception
    {
        final Media media = _event.getMedia();
        if (media == null) {
            alertaError(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Error.Label"),
                            "Hubo un problema con el archivo proporcionado.", null);
        } else {
            //System.out.println(media.getName());
            if (media instanceof org.zkoss.image.Image) {
                if (media.getByteData().length > 102400) {
                    alertaInfo(this.logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Info.Label"),
                                    "El archivo seleccionado es muy grande. Maximo permitido = 100k", null);

                } else {
                    setImage(media);
                }
                // imgFoto.setContent((org.zkoss.image.Image)media);
            } else {
                alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Info2.Label",
                                Constantes.EMPTY_STRING,
                                new String[] { media.getName() }),
                                "El archivo seleccionado " + media.getName() + " no es una imagen", null);
                return;
            }
        }
    }

    /**
     * @param _event Event parameter.
     */
    @Listen("onClick = #btnSave3")
    public void saveImages(final Event _event)
    {
        if (this.product != null) {
            if (this.lstImages.getItems() != null && !this.lstImages.getItems().isEmpty()) {
                for (final Listitem imgIt : this.lstImages.getItems()) {
                    if ((Boolean) imgIt.getAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE)) {
                        final DTO_ProductImage img4Prod = imgIt.getValue();
                        img4Prod.setProductId(this.product.getId());
                        img4Prod.setActive(true);
                        img4Prod.setDefaul(false);
                        this.product.getImages().add(img4Prod);
                        imgIt.setAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE, false);
                    }
                }
                final ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(this.product);
                input.setAction(Constantes.V_REGISTERIMG4PROD);
                final ServiceOutput<DTO_Product> output = this.productService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo(this.logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info.Label"),
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info.Label"), null);
                    // product.setImages(new ArrayList<DTO_ProductImage>());
                    // cleanImagePanel();
                } else {
                    alertaError(this.logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Error.Label"),
                                    "Erro al guardar la imagen.", null);
                }
            } else {
                alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info3.Label"),
                                "No hay imagenes para cargar", null);
            }
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info2.Label"),
                            "Ningun producto a sido creado previamente", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_PROD_PRODUCT };
    }
}

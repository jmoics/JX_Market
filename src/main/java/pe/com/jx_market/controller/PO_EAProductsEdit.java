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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
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
public class PO_EAProductsEdit
    extends SecuredComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAProductsEdit.class);
    @Wire
    private Combobox cmbCateg, cmbStatus, cmbMarca;
    @Wire
    private Textbox txtNombre, txtDesc, txtMarca;
    @Wire
    private Decimalbox decPrec;
    @Wire
    private Image imgFoto;
    @Wire
    private Label lbImageName, lbImageSize;
    @Wire
    private Window wEAEP;
    @Wire
    private Tree tree;
    @Wire
    private Listbox lstImages;
    //private byte[] imgProducto;
    @WireVariable
    private BusinessService<DTO_Product> productService;
    @WireVariable
    private BusinessService<DTO_Category> categoryService;
    @WireVariable
    private BusinessService<DTO_TradeMark> marcaService;
    private DTO_Company company;
    private DTO_Product product;
    @WireVariable
    private Desktop desktop;
    private CategoryTreeNode categoryTreeNode;
    private AdvancedTreeModel categoryTreeModel;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        //decPrec.setValue(BigDecimal.ZERO);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);

        product = (DTO_Product) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_PRODUCT);
        final ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(product);
        input.setAccion(Constantes.V_GETIMG);
        final ServiceOutput<DTO_Product> output = productService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            alertaInfo(logger, "", "El product" + product.getProductName() + "no posee imagen", null);
        }

        if (product == null) {
            alertaInfo(logger, "", "No se encontro producto, retornando a busqueda", null);
        } else {
            desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_PRODUCT);
            tree.setCheckmark(true);
            tree.setMultiple(true);
            cargarDatos();
            buildMarcaCombo();
        }
    }

    private void buildMarcaCombo()
    {
        final DTO_TradeMark marFi = new DTO_TradeMark();
        marFi.setEmpresa(company.getId());
        final ServiceInput<DTO_TradeMark> input = new ServiceInput<DTO_TradeMark>(marFi);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_TradeMark> output = marcaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(logger, "", "Exito al cargar categories", null);
            final List<DTO_TradeMark> lstMar = output.getLista();
            for (final DTO_TradeMark marIt : lstMar) {
                final Comboitem item = new Comboitem();
                item.setLabel(marIt.getTradeMarkName());
                item.setValue(marIt);
                cmbMarca.appendChild(item);
                if (product.getTradeMark().getId().equals(marIt.getId())) {
                    cmbMarca.setSelectedItem(item);
                }
            }
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando marcas", null);
        }
    }

    @Listen("onClick = #btnSave")
    public void editProduct()
    {
        if (cmbStatus.getSelectedItem() != null && cmbMarca.getSelectedItem() != null
                        && !txtNombre.getValue().equals("") && !txtDesc.getValue().equals("")) {
            product.setActivo((Boolean) cmbStatus.getSelectedItem().getValue());
            product.setTradeMark((DTO_TradeMark) cmbMarca.getSelectedItem().getValue());
            product.setProductDescription(txtDesc.getValue());
            product.setEmpresa(company.getId());
            product.setProductName(txtNombre.getValue());
            // product.setPrecio(decPrec.getValue());
            /*if (imgProducto != null && !imgProducto.equals(product.getImagen())) {
                product.setImagen(imgProducto);
                product.setNomimg(null);
            }*/
            final ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(product);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Product> output = productService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsEdit.editProduct.Info.Label",
                                product.getProductName()),
                                "Los datos del producto se actualizaron correctamente", null);
                if (resp == Messagebox.OK) {
                    desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    ContextLoader.recargar(desktop, Constantes.Form.PRODUCTS_FORM.getForm());
                }
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsEdit.editProduct.Error.Label"),
                                "Ocurrio un error inesperado al guardar el producto, consulte al Administrador", null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsEdit.editProduct.Info2.Label"),
                                "No se ingreso data en todos los campos", null);
        }
        ContextLoader.recargar(desktop, Constantes.Form.PRODUCTS_FORM.getForm());

        wEAEP.detach();
    }

    @Listen("onClick = #btnSave2")
    public void editCategories()
    {
        final Map<Integer, DTO_Category> mapCat4Prod = new HashMap<Integer, DTO_Category>();
        for (final DTO_Category cat : product.getCategories()) {
            mapCat4Prod.put(cat.getId(), cat);
        }
        if (tree.getItems() != null && !tree.getItems().isEmpty()) {
            final DTO_Product prod4DelCat = new DTO_Product();
            prod4DelCat.setId(product.getId());
            prod4DelCat.setEmpresa(product.getEmpresa());
            prod4DelCat.setCategories(new ArrayList<DTO_Category>());
            for (final Treeitem item : tree.getItems()) {
                saveCategories(item, mapCat4Prod, prod4DelCat);
            }

            ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(product);
            input.setAccion(Constantes.V_REGISTERCAT4PROD);
            ServiceOutput<DTO_Product> output = productService.execute(input);
            boolean correct = true;
            if (output.getErrorCode() != Constantes.OK) {
                alertaError(logger, "", "Error al guardar nuevas categories para el producto", null);
                correct = false;
            }
            input = new ServiceInput<DTO_Product>(prod4DelCat);
            input.setAccion(Constantes.V_DELETECAT4PROD);
            output = productService.execute(input);
            if (output.getErrorCode() != Constantes.OK) {
                alertaError(logger, "", "Error al eliminar categories para el producto", null);
                correct = false;
            }
            if (correct) {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsEdit.editCategories.Info.Label"), "", null);
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsEdit.editCategories.Error.Label"),
                                "Error al guardar o eliminar categories para el producto", null);
            }
        }
    }

    private void saveCategories(final Treeitem _item,
                                final Map<Integer, DTO_Category> _mapCat4Prod,
                                final DTO_Product _prod4DelCat)
    {
        final CategoryTreeNode ctn = _item.getValue();
        final DTO_Category categ = ctn.getData();

        if (_item.isSelected() && !_mapCat4Prod.containsKey(categ.getId())) {
            product.getCategories().add(categ);
        } else if (!_item.isSelected() && _mapCat4Prod.containsKey(categ.getId())) {
            for (int cnt = 0; cnt < product.getCategories().size(); cnt++) {
                if (product.getCategories().get(cnt).getId().equals(categ.getId())) {
                    _prod4DelCat.getCategories().add(product.getCategories().get(cnt));
                    product.getCategories().remove(cnt);
                }
            }
        }
        if (_item.getTreechildren() != null) {
            for (int i = 0; i < _item.getTreechildren().getChildren().size(); i++) {
                saveCategories((Treeitem) _item.getTreechildren().getChildren().get(i), _mapCat4Prod, _prod4DelCat);
            }
        }
    }

    @Listen("onClick = #btnClose, #btnClose2, #btnClose3")
    public void accionCerrar(final Event e) {
        //ContextLoader.recargar(desktop, Constantes.Form.PRODUCTS_FORM.getForm());
        wEAEP.detach();
    }

    private void cargarDatos()
    {
        buildActiveCombo(cmbStatus);
        txtNombre.setValue(product.getProductName());
        txtDesc.setValue(product.getProductDescription());
        // decPrec.setValue(product.getPrecio());
        //imgProducto = product.getImagen();
        loadPhotos();

        categoryTreeNode = getCategories();
        categoryTreeModel = new AdvancedTreeModel(categoryTreeNode);
        categoryTreeModel.setMultiple(true);
        tree.setItemRenderer(new CategoryTreeRenderer());
        tree.setModel(categoryTreeModel);
    }

    @Override
    public void buildActiveCombo(final Combobox _combo)
    {
        super.buildActiveCombo(cmbStatus);
        for(final Comboitem item : cmbStatus.getItems()) {
            if (product.getActivo().equals(item.getValue())){
                cmbStatus.setSelectedItem(item);
            }
        }
    }

    public CategoryTreeNode getCategories()
    {
        final DTO_Category cat = new DTO_Category();
        cat.setEmpresa(company.getId());
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Category> output = categoryService.execute(input);
        CategoryTreeNode categoryTreeNode = null;
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Category> lstCat = output.getLista();
            categoryTreeNode = buildCategoriesTree(lstCat);
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"),
                            "Error cargando categories", null);
        }
        return categoryTreeNode;
    }

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
                                construirJerarquia(childs.values(), root, setPadres, false);
                            }
                        }, true));
                    }
                }
            }
        },
        true);
        return categoryTreeNode;
    }

    private final class CategoryTreeRenderer
        implements TreeitemRenderer<CategoryTreeNode>
    {
        @Override
        public void render(final Treeitem treeItem,
                           final CategoryTreeNode treeNode,
                           final int index)
            throws Exception
        {
            final Map<Integer, DTO_Category> mapCat4Prod = new HashMap<Integer, DTO_Category>();
            for (final DTO_Category cat : product.getCategories()) {
                mapCat4Prod.put(cat.getId(), cat);
            }
            final CategoryTreeNode ctn = treeNode;
            final DTO_Category categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(treeItem);
            treeItem.setValue(ctn);
            treeItem.setOpen(ctn.isOpen());
            if (mapCat4Prod.containsKey(categ.getId())) {
                treeItem.setSelected(true);
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

            treeItem.addEventListener(Events.ON_CLICK, new EventListener<Event>()
            {
                @Override
                public void onEvent(final Event event)
                    throws Exception
                {
                    final Treeitem item = (Treeitem) event.getTarget();
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
                                             final boolean _checked) {
                    _item.setSelected(_checked);
                    if (_checked && _item.getParentItem() != null
                            && _item.getParentItem().getValue() != null) {
                        modificarPadres(_item.getParentItem(), _checked);
                    }
                }
            });
        }
    }

    private void loadPhotos() {
        final StringBuilder str = new StringBuilder();
        for (final DTO_ProductImage img : product.getImages()) {
            if (img.getImage() != null) {
                final Listitem item = new Listitem();
                item.setAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE, false);
                item.setValue(img);
                Listcell cell = new Listcell();
                cell.setLabel(img.getImageName());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel("" + img.getImage().length);
                item.appendChild(cell);
                cell = new Listcell();
                final Checkbox checkBoxEstado = new Checkbox();
                checkBoxEstado.setValue(img.isActive());
                checkBoxEstado.setChecked(img.isActive());
                checkBoxEstado.addEventListener(Events.ON_CHECK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event event)
                        throws Exception
                    {
                        final Checkbox check = (Checkbox) event.getTarget();
                        final DTO_ProductImage imgProd = ((Listitem) check.getParent().getParent()).getValue();
                        imgProd.setActive(check.isChecked());
                    }
                });
                cell.appendChild(checkBoxEstado);
                item.appendChild(cell);
                cell = new Listcell();
                final Checkbox checkBoxDef = new Checkbox();
                checkBoxDef.setValue(img.isDefaul());
                checkBoxDef.setChecked(img.isDefaul());
                checkBoxDef.addEventListener(Events.ON_CHECK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event event)
                        throws Exception
                    {
                        final Checkbox check = (Checkbox) event.getTarget();
                        final DTO_ProductImage imgProd = ((Listitem) check.getParent().getParent()).getValue();
                        imgProd.setDefaul(check.isChecked());
                    }
                });
                cell.appendChild(checkBoxDef);
                item.appendChild(cell);
                item.setParent(lstImages);
                item.addEventListener(Events.ON_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event event)
                        throws Exception
                    {
                        final Listitem lItem = (Listitem) event.getTarget();
                        final DTO_ProductImage img = lItem.getValue();
                        lbImageName.setValue(img.getImageName());
                        lbImageSize.setValue("" + img.getImage().length);
                        imgFoto.setContent(new AImage("foto", img.getImage()));
                    }
                });
            } else {
                if (str.length() == 0) {
                    str.append(img.getImageName());
                } else {
                    str.append(", ").append(img.getImageName());
                }
            }
        }
        if (str.length() > 0) {
            alertaError(logger, "", Labels.getLabel("pe.com.jx_market.PO_EAProductsEdit.loadPhoto.Error.Label",
                                            Constantes.EMPTY_STRING,
                                            new String[] {str.toString()}), null);
        }
    }

    private void setImage(final Media _media)
    {
        if (_media != null) {
            final byte[] imgProducto = _media.getByteData();
            if (imgProducto != null) {
                try {
                    imgFoto.setContent(new AImage("foto", imgProducto));
                    lbImageName.setValue(_media.getName());
                    lbImageSize.setValue("" + _media.getByteData().length);

                    final DTO_ProductImage img4Prod = new DTO_ProductImage();
                    img4Prod.setCompany(company.getId());
                    img4Prod.setImage(imgFoto.getContent().getByteData());
                    img4Prod.setImageName(_media.getName());
                    img4Prod.setActive(true);
                    img4Prod.setDefaul(false);

                    final Listitem item = new Listitem();
                    item.setAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE, true);
                    item.setValue(img4Prod);
                    Listcell cell = new Listcell();
                    cell.setLabel(_media.getName());
                    item.appendChild(cell);
                    cell = new Listcell();
                    cell.setLabel("" + _media.getByteData().length);
                    item.appendChild(cell);
                    final Checkbox checkBoxEstado = new Checkbox();
                    checkBoxEstado.setValue(img4Prod.isActive());
                    checkBoxEstado.setChecked(img4Prod.isActive());
                    checkBoxEstado.addEventListener(Events.ON_CHECK, new EventListener<Event>()
                    {
                        @Override
                        public void onEvent(final Event event)
                            throws Exception
                        {
                            final Checkbox check = (Checkbox) event.getTarget();
                            final DTO_ProductImage imgProd = ((Listitem) check.getParent().getParent()).getValue();
                            imgProd.setActive(check.isChecked());
                        }
                    });
                    cell.appendChild(checkBoxEstado);
                    item.appendChild(cell);
                    cell = new Listcell();
                    final Checkbox checkBoxDef = new Checkbox();
                    checkBoxDef.setValue(img4Prod.isDefaul());
                    checkBoxDef.setChecked(img4Prod.isDefaul());
                    checkBoxDef.addEventListener(Events.ON_CHECK, new EventListener<Event>()
                    {
                        @Override
                        public void onEvent(final Event event)
                            throws Exception
                        {
                            final Checkbox check = (Checkbox) event.getTarget();
                            final DTO_ProductImage imgProd = ((Listitem) check.getParent().getParent()).getValue();
                            imgProd.setDefaul(check.isChecked());
                        }
                    });
                    cell.appendChild(checkBoxDef);
                    item.appendChild(cell);

                    item.setParent(lstImages);
                    item.addEventListener(Events.ON_CLICK, new EventListener<Event>()
                    {
                        @Override
                        public void onEvent(final Event event)
                            throws Exception
                        {
                            final Listitem lItem = (Listitem) event.getTarget();
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
        imgFoto.setSrc("/media/silueta.gif");
    }

    @Listen("onUpload = #btnUpload")
    public void uploadPhoto(final UploadEvent _event)
        throws Exception
    {
        Media media;
        try {
            media = _event.getMedia();
            if (media == null) {
                return;
            }
        } catch (final Exception ex) {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Error.Label"),
                            "Hubo un problema con el archivo proporcionado.", ex);
            return;
        }
        System.out.println(media.getName());
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 102400) {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Info.Label"),
                                "El archivo seleccionado es muy grande. Maximo permitido = 100k", null);
                return;
            }
            setImage(media);

            // imgFoto.setContent((org.zkoss.image.Image)media);
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Info2.Label",
                                                Constantes.EMPTY_STRING,
                                                new String[] {media.getName()}),
                            "El archivo seleccionado " + media.getName() + " no es una imagen", null);
            return;
        }
    }

    @Listen("onClick = #btnSave3")
    public void saveImages(final Event _event) {
        if (product != null) {
            if (lstImages.getItems() != null && !lstImages.getItems().isEmpty()) {
                for (final Listitem imgIt : lstImages.getItems()) {
                    if ((Boolean) imgIt.getAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE)) {
                        final DTO_ProductImage img4Prod = imgIt.getValue();
                        img4Prod.setProductId(product.getId());
                        product.getImages().add(img4Prod);
                        imgIt.setAttribute(Constantes.ATTRIBUTE_PRODUCT_EDITIMAGE, false);
                    }
                }
                final ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>(product);
                input.setAccion(Constantes.V_REGISTERIMG4PROD);
                final ServiceOutput<DTO_Product> output = productService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info.Label"),
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info.Label"), null);
                } else {
                    alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Error.Label"),
                                    "Erro al guardar la imagen.", null);
                }
            } else {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info3.Label"),
                                "No hay imagenes para cargar", null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info2.Label"),
                            "Ningun producto a sido creado previamente", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULO_PROD_PRODUCT };
    }
}

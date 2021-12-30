/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.modelo.jardineria;

import inser.persistence.restful_crud.LinkedList_Producto;
import inser.persistence.restful_crud.Producto;
import inser.web.utilidades.Paginacion_busquedas_ordenaciones_modelo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author informatica
 */
public class Producto_operaciones {
    public String usuario;
    public String contraseña;
    public incli.restful.restful_crud_cliente.rest.Producto producto = new incli.restful.restful_crud_cliente.rest.Producto ();
    
    public long contar_filas(String [] error) {
        Long retorno = -1L;
        try {
            retorno = producto.countREST(usuario, contraseña, error);
        } catch (Exception e) {
            retorno = -1L;
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al contar productos. "
                    + error[0];
            Logger.getLogger("innui").log(Level.SEVERE, error[0], e);
        }
        return retorno;
    }
    /**
     * Devuelve una lista con las filas de la tabla productos 
     * @param paginacion_busquedas_ordenaciones_modelo Datos para la paginacion.
     * @param error En la posición 0, mensaje de error, si lo hay. 
     * @return null si hay error, una lista de productos si no hay error
     */
    public List<Producto> listar(Paginacion_busquedas_ordenaciones_modelo paginacion_busquedas_ordenaciones_modelo, String [] error) {
        boolean ret = true;
        List lista = null;
        boolean es_con_filtro = false;
        boolean es_con_ordenacion_ascendente = false;
        boolean es_con_ordenacion_descendente = false;
        try {
            LinkedList_Producto linkedList_Producto = new LinkedList_Producto();
            if (paginacion_busquedas_ordenaciones_modelo.filtro != null
                    && paginacion_busquedas_ordenaciones_modelo.filtro.isBlank() == false) {
                es_con_filtro = true;
            }
            if (paginacion_busquedas_ordenaciones_modelo.orden_ascendente != null) {
                es_con_ordenacion_ascendente = true;
            } else if (paginacion_busquedas_ordenaciones_modelo.orden_descendente != null) {
                es_con_ordenacion_descendente = true;
            }
            if (es_con_filtro == false
                    && es_con_ordenacion_ascendente == false
                    && es_con_ordenacion_descendente == false) {
                linkedList_Producto = producto.findRange_JSON(linkedList_Producto.getClass()
                        , String.valueOf(paginacion_busquedas_ordenaciones_modelo.pagina_fila_inicio_num)
                        , String.valueOf(paginacion_busquedas_ordenaciones_modelo.pagina_fila_inicio_num 
                            + paginacion_busquedas_ordenaciones_modelo.pagina_tam - 1)
                        , usuario, contraseña, error); 
                ret = (linkedList_Producto != null);
            }
            if (ret) {
                lista = linkedList_Producto.lista;
            }
        } catch (Exception e) {
            lista = null;
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al listar productos. "
                    + error[0];
            Logger.getLogger("innui").log(Level.SEVERE, error[0], e);
        }
        return lista;
    }
    
    /**
     * Devuelve una fila de la tabla productos 
     * @param error En la posición 0, mensaje de error, si lo hay. 
     * @return null si hay error, una lista de productos si no hay error
     */
    public Producto encontrar(String codigo_producto, String [] error) {
        Producto producto_entidad = null;
        try {
            producto_entidad = producto.find_JSON(inser.persistence.restful_crud.Producto.class
                    , codigo_producto, usuario, contraseña, error);
        } catch (Exception e) {
            producto = null;
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al encontrar producto. "
                    + error[0];
            Logger.getLogger("innui").log(Level.SEVERE, error[0], e);
        }
        return producto_entidad;
    }
    
    public boolean borrar(String codigo_producto, String [] error) {
        boolean ret = true;
        try {
            ret = producto.remove("1", usuario, contraseña, error);
        } catch (Exception e) {
            ret = false;
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al borrar producto. "
                    + error[0];
            Logger.getLogger("innui").log(Level.SEVERE, error[0], e);
        }
        return ret;
    }

    public boolean crear(Producto producto_entidad, String [] error) {
        boolean ret = true;
        try {
            ret = producto.create_JSON(producto_entidad, usuario, contraseña, error);
        } catch (Exception e) {
            ret = false;
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al crear producto. "
                    + error[0];
            Logger.getLogger("innui").log(Level.SEVERE, error[0], e);
        }
        return ret;
    }

    public boolean modificar(Producto producto_entidad, String [] error) {
        boolean ret = true;
        try {
            ret = producto.edit_JSON(producto_entidad, producto_entidad.getCodigoProducto(), usuario, contraseña, error);
        } catch (Exception e) {
            ret = false;
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al actualizar producto. "
                    + error[0];
            Logger.getLogger("innui").log(Level.SEVERE, error[0], e);
        }
        return ret;
    }
}

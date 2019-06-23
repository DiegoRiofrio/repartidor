package es.upm.miw.repartidor.objetos;
import org.apache.commons.lang3.builder.ToStringBuilder;
import es.upm.miw.repartidor.estado.Estado;

public class Pedido {
    private String referencia;
    private Integer articulos;
    private String cliente;
    private String direccion;
    //private Estado estado;
    private String estado;

    public Pedido(String referencia, int articulos, String cliente, String direccion, String estado) {
        this.articulos = articulos;
        this.cliente = cliente;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Pedido() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public Integer getArticulos() {
        return articulos;
    }

    public void setArticulos(Integer articulos) {
        this.articulos = articulos;
    }
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String toString() {return direccion; }
}


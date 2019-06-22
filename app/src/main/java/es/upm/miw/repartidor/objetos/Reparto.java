package es.upm.miw.repartidor.objetos;

import es.upm.miw.repartidor.estado.Estado;

public class Reparto {
    int articulos;
    String cliente;
    String direccion;
    Estado estado;

    public Reparto(int articulos, String cliente, String direccion, Estado estado) {
        this.articulos = articulos;
        this.cliente = cliente;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Reparto() {
    }

    public int getArticulos() {
        return articulos;
    }

    public void setArticulos(int articulos) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}

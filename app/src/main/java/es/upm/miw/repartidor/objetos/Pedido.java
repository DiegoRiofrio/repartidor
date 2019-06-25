package es.upm.miw.repartidor.objetos;

import es.upm.miw.repartidor.estado.Estado;

public class Pedido {
    private String referencia;
    private Integer articulos;
    private String cliente;
    private String direccion;
    private Estado estado;
    private Estado encamino;
    private Estado entregado;
    private String fecha_registro;
    private String fecha_encamino;
    private String fecha_entrega;



    public Pedido() {
    }

    public Pedido(String referencia, Integer articulos, String cliente, String direccion, Estado estado, Estado encamino, Estado entregado, String fecha_registro, String fecha_encamino, String fecha_entrega) {
        this.referencia = referencia;
        this.articulos = articulos;
        this.cliente = cliente;
        this.direccion = direccion;
        this.estado = estado;
        this.encamino = encamino;
        this.entregado = entregado;
        this.fecha_registro = fecha_registro;
        this.fecha_encamino = fecha_encamino;
        this.fecha_entrega = fecha_entrega;
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


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public Estado getEntregado() {
        return entregado;
    }

    public void setEntregado(Estado entregado) {
        this.entregado = entregado;
    }
    public String toString() {return direccion; }

    public Estado getEncamino() {
        return encamino;
    }

    public void setEncamino(Estado encamino) {
        this.encamino = encamino;
    }

    public String getFecha_encamino() {
        return fecha_encamino;
    }

    public void setFecha_encamino(String fecha_encamino) {
        this.fecha_encamino = fecha_encamino;
    }
}


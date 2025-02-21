package org.model;

import org.util.ListaProgreso_Util;

public class Item_Model {

    private int nivel, progreso;
    private double precio, precioTotal;

    public Item_Model(int nivel, int progreso, double precio) {
        this.nivel = nivel;
        this.progreso = progreso;
        this.precio = precio;
        this.precioTotal = 0;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void calcularPrecioTotal() {
        setPrecioTotal(calcularPrecioNivel() + calcularPrecioProgreso());
    }

    private double calcularPrecioNivel() {
        return getPrecio() * Math.pow(3, getNivel() - 1);
    }

    private double calcularPrecioProgreso() {
        int[] progressTable = ListaProgreso_Util.PROGRESO;
        int progresoDisponible = getProgreso();
        double precioTotalProgreso = 0;

        while (progresoDisponible > 0) {
            for (int i = progressTable.length - 1; i >= 0; i--) {
                if (progressTable[i] <= progresoDisponible) {
                    progresoDisponible -= progressTable[i];
                    int nivelProgreso = i + 1;
                    precioTotalProgreso += Math.pow(3, nivelProgreso - 1) * getPrecio();
                    break;
                }
                if (progresoDisponible == 10) {
                    progresoDisponible = 0;
                    precioTotalProgreso += Math.pow(3, 0) * getPrecio();
                    break;
                }
            }
            System.out.println(progresoDisponible);
        }
        return precioTotalProgreso;
    }
}

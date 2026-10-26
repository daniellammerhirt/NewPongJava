package com.info;

public class Raquete extends Movel{

    private double dy;

    public Raquete(double x, double y){
        setX(x);
        setY(y);
        setAltura(70);
        setLargura(50);
        setImagem(carregarImagem("/imagens/raquete.gif"));
    }

    public void mexer(){
        setY(getY() + dy);
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}

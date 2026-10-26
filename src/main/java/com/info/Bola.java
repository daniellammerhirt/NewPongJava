package com.info;

public class Bola extends Movel{

    private double dx;
    private double dy;

    public Bola(int x, int y, int dx){
        setX(x);
        setY(y);
        setAltura(20);
        setLargura(20);
        this.dx =dx;
        setImagem(carregarImagem("/imagens/bola.gif"));
    }

    public void mexer(){
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}

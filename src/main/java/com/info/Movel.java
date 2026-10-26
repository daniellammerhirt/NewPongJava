package com.info;

import javax.swing.*;
import java.awt.*;

public class Movel {
    private int altura;
    private int largura;
    private Image imagem;
    private double x;
    private double y;

    public Rectangle getLimites(){
        return new Rectangle((int)x, (int)y, largura,altura);
    }

    protected Image carregarImagem(String caminho){
        return new ImageIcon(getClass().getResource(caminho))
                .getImage()
                .getScaledInstance(getLargura(), getAltura(), Image.SCALE_SMOOTH);
    }


    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public Image getImagem() {
        return imagem;
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

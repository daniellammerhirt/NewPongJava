package com.info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Campo extends JPanel implements ActionListener {

    private Bola bola;
    private Raquete raquetePc;
    private Raquete raqueteJ;
    private final Timer timer;
    private int pontoJ, pontoPc;
    private String msg;
    private final int velocidade;

    public Campo(int largura, int altura){
        setFocusable(true);
        setDoubleBuffered(true);
        setSize(new Dimension(largura, altura));
        velocidade = 5;
        inicializa();
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(new KeyAdapter(){
          @Override
          public void keyPressed(KeyEvent e){
              verificaTeclado(e.getKeyCode());
        }

        public void keyRelased(KeyEvent e){
              raqueteJ.setDy(0);
        }
        });
    }

    private void verificaTeclado(int tecla){
        switch (tecla){
            case KeyEvent.VK_UP -> raqueteJ.setDy(-velocidade);
            case KeyEvent.VK_DOWN -> raqueteJ.setDy(velocidade);
            case KeyEvent.VK_P -> pausa();
            case KeyEvent.VK_R -> reinicia();
            case KeyEvent.VK_SPACE -> continua();
        }
    }

    private void continua(){
        if (pontoJ < 5 && pontoPc < 5){
            msg = "";
            timer.start();
        }
    }

    private void reinicia(){
        inicializa();
        timer.start();
    }

    private void pausa(){
        if (timer.isRunning()){
            msg = "Pausa";
            repaint();
            timer.stop();
        }else {
            timer.restart();
            msg = "";
        }
    }

    private void inicializa(){
        raquetePc = new Raquete(10, this.getHeight() / 2);
        raqueteJ = new Raquete(this.getWidth() - 60, this.getHeight() / 2);
        bola = new Bola(raquetePc.getLargura() + 10, this.getHeight() / 2, velocidade);
        bola.setDy(new Random().nextDouble() * (velocidade / 2));
        pontoJ = 0;
        pontoPc = 0;
        msg = "";
    }

    @Override
    public void actionPerformed(ActionEvent e){
        bola.mexer();
        raquetePc.mexer();
        raqueteJ.mexer();
        verificarLimitesRaquete(raqueteJ);
        verificarColisoes();
        mexerPc();
        verificaPonto();
        repaint();
    }

    private void verificarLimitesRaquete(Raquete raquete){
        if (raquete.getY() > this.getHeight() - raquete.getAltura()){
            raquete.setY(this.getHeight() - raquete.getAltura());
        }
        if (raquete.getY() < 0){
            raquete.setY(0);
        }
    }

    private void verificarColisoes(){
        if (verificaColisao(bola, raqueteJ)){
            ajustarRebatida(bola, raqueteJ);
        }
        if (verificaColisao(bola, raquetePc)){
            ajustarRebatida(bola, raquetePc);
        }
        if (bola.getY() < 10 || bola.getY() > this.getHeight() - bola.getAltura()){
            bola.setDy(-bola.getDy());
        }
    }

    private void ajustarRebatida(Bola bola, Raquete raquete){
        bola.setDx(-bola.getDx());
        double novaDy = new Random().nextDouble() * (velocidade / 2) * Math.signum(bola.getDy()) + raquete.getDy();
        bola.setDy(novaDy);
    }

    private  boolean verificaColisao(Bola b, Raquete r){
         return  r.getLimites().intersects(b.getLimites());
    }

    private void mexerPc(){
        if (bola.getX() < this.getWidth() / 2 && bola.getDx() < 0){
            raquetePc.setDy((velocidade / 2) * Math. signum(bola.getY() - raquetePc.getY()));
        }else {
            raquetePc.setDy(0);
        }
    }

    private void verificaPonto(){
        if (bola.getX() < 0){
            pontoJ++;
            reiniciarPosicaoBola(-velocidade);
            verificarFimDeJogo();
        } else if (bola.getX() > this.getWidth()){
            pontoPc++;
            reiniciarPosicaoBola(velocidade);
            verificarFimDeJogo();
        }
    }

    private  void reiniciarPosicaoBola(double novaDx){
        bola.setX(novaDx > 0 ? raquetePc.getLargura() + 10 : raqueteJ.getX() - 20);
        bola.setY((novaDx > 0 ? raquetePc.getY() : raqueteJ.getY()));
        bola.setDx(novaDx);
        bola.setDy( new Random().nextDouble() * (velocidade / 2) * Math.signum(bola.getDy()));
        timer.stop();
        msg = novaDx < 0 ? "Ponto para jogador - Espaço para continuar" : "Ponto para PC - Espaço para continuar";
    }

    private void  verificarFimDeJogo(){
        if (pontoJ > 4){
            msg = "Jogador Ganhou! 'R' - Reinicia o Jogo";
            timer.stop();
        }else if (pontoPc > 4){
            msg = "PC Ganhou! 'R' - Reinicia o Jogo";
            timer.stop();
        }
        repaint();
}

@Override
public void  paint(Graphics g){
    super.paint(g);
    desenharCampo(g);
    desenharObjetos(g);
    desenharPontuacao(g);
    desenharMensagem(g);
}

private void desenharCampo(Graphics g){
    g.setColor(new Color(0, 150, 0));
    g.fillRect(0,0, this.getWidth(), this.getHeight());
    g.setColor(Color.WHITE);
    g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
    }

    private void desenharObjetos(Graphics g){
        g.drawImage(bola.getImagem(), (int) bola.getX(), (int) bola.getY(), this);
        g.drawImage(raquetePc.getImagem(), (int) raquetePc.getX(), (int) raquetePc.getY(),this);
        g.drawImage(raqueteJ.getImagem(), (int) raqueteJ.getX(), (int) raqueteJ.getY(), this);
    }

    private void desenharPontuacao(Graphics g){
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString(String.valueOf(pontoPc), this.getWidth() /4, 50);
        g.drawString(String.valueOf(pontoJ), 3 * this.getWidth() / 4, 50);
    }

    private void desenharMensagem(Graphics g){
        if (!msg.isEmpty()){
            Font f = new Font("Arial", Font.BOLD, 20);
            g.setFont(f);
            FontMetrics fm = g.getFontMetrics(f);
            Rectangle2D rect = fm.getStringBounds(msg, g);
            int textWidth = (int) rect.getWidth();
            int x = (this.getWidth() - textWidth) /2;
            g.drawString(msg, x, 3 * this.getHeight() / 4);
        }
    }
}

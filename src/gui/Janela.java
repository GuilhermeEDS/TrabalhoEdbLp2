package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Posicao {
    private final int x;
    private final int y;

    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Janela extends JPanel {
    private static final int LARGURA = 800;
    private static final int ALTURA = 800;
    private static final int DISTANCIA_CENTRO = 200;
    private static final int DISTANCIA_EXTRA = 20;

    Image imagemCasa;

    public Janela() throws IOException {
        imagemCasa = ImageIO.read(new File("img/casa.png"));
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();

        frame.getContentPane().add(new Janela());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(LARGURA, ALTURA);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        desenharCasas(g, getPosicoesCasas(10));
    }

    private ArrayList<Posicao> getPosicoesCasas(int numeroCasas) {
        ArrayList<Posicao> posicoes = new ArrayList<>(numeroCasas);
        for (int indiceCasa = 0; indiceCasa < numeroCasas; indiceCasa++) {
            double valor = 2 * Math.PI * indiceCasa / numeroCasas;

            int x = (int) (DISTANCIA_CENTRO * Math.cos(valor));
            int y = (int) (DISTANCIA_CENTRO * Math.sin(valor));

            posicoes.add(new Posicao((LARGURA - DISTANCIA_CENTRO / 2) / 2 + x, (ALTURA - DISTANCIA_CENTRO / 2) / 2 + y));
        }

        return posicoes;
    }

    private void desenharCasas(Graphics graphics, ArrayList<Posicao> posicoes) {
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < posicoes.size(); i++) {
            Posicao posicao = posicoes.get(i);
            graphics.drawImage(imagemCasa,
                    posicao.getX() + imagemCasa.getWidth(null) / 2,
                    posicao.getY() + imagemCasa.getHeight(null) / 2,
                    null);

            graphics.drawString(Integer.toString(i),
                    posicao.getX() + imagemCasa.getWidth(null) / 2 + DISTANCIA_EXTRA,
                    posicao.getY() + imagemCasa.getHeight(null) / 2);
        }
    }
}

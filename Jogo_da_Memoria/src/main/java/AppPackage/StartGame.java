
package AppPackage;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.*;
import javax.swing.*;

/** Inicializa o tabuleiro do jogo, contudo, a lógica por trás é formada na classe GameRules*/

public class StartGame {
    
    protected static JFrame frame; // Janela em que o jogo irá rodar
    private JPanel containerPanel; // Painel em que serão postos o jogo e o temporizador
    private JPanel gamePanel; // Painel do jogo
    protected static JPanel timerPanel; // Painel do timer
    protected static JLabel timerLabel; // Texto do timer
    protected static Card[] tiles; // Array de botões para a construção do grid do jogo
    
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Coleta o tamanho da tela
    
    protected static int time; // Tempo restante para o término da partida
    protected static int gridSize; // Tamanho do tabuleiro
    
    protected static GameRules rules; // Lógica do jogo
    
    protected StartGame(int selected) {
        time = 80 - selected*10; // Configura o tempo de jogo
        
        frame = new JFrame("Jogo da Memória");
        containerPanel = new JPanel();
        gamePanel = new JPanel();
        timerPanel = new JPanel();
        timerLabel = new JLabel("<html><h1><b>"+time+"</b></h1></html>");
        
        rules = new GameRules();
        
        Window.frame.dispose();
        
        // Construindo os paineis
        
        containerPanel.setLayout(new BorderLayout());
        constructGamePanel(gamePanel, tiles, selected);
        
        timerPanel.add(timerLabel, SwingConstants.CENTER);
        
        containerPanel.add(timerPanel, BorderLayout.NORTH);
        containerPanel.add(gamePanel, BorderLayout.CENTER);
        
        frame.setBackground(Color.DARK_GRAY);
        frame.add(containerPanel);
        frame.setDefaultCloseOperation(3);
        frame.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        frame.pack();
        frame.setVisible(true);
        
    }
    
    /** Constrói a forma final do tabuleiro */ 
    
    private void constructGamePanel(JPanel gamePanel, Card[] tiles,int selected) { 
    
        // Setando o tamanho do tabuleiro

        switch(selected) {
            case 0: gridSize = 4;
            break;
            case 1: gridSize = 6;
            break;
            case 2: gridSize = 8;
            break;
            default: System.out.println("Algo deu errado");
            gridSize = 4;
            break;
        }
        
        tiles = new Card[gridSize*gridSize]; // Indica a quantidade de cartas
        
        gamePanel.setLayout(new GridLayout(gridSize, gridSize)); // Indica o tamanho do tabuleiro
        
        // Construindo os botões

        for(int i = 0; i < tiles.length; i++) {
            
            tiles[i] = new Card();
            tiles[i].setId(i);
            tiles[i].setBackground(new Color(136, 0, 0));
            
        }
        
        StartGame.tiles = tiles; // Faz com que o tiles original siga a construção dos botões
       
        // Embaralhando os botões
        
        tiles = shuffle(tiles); // Embaralha a posição dos botões
        
        // Adicionando os botões ao painel de jogo
        
        for(Card c : tiles) {
            
            gamePanel.add(c);
        }
    
    }
    
    /** Embaralha as cartas */
    
    private static Card[] shuffle(Card[] tiles) {
           
        List<Card> list =Arrays.asList(tiles);
        Collections.shuffle(list);
        
        list.toArray(tiles);
        
        return tiles;
    }
    
}

/** Indica a função das cartas e seu comportamento no jogo, além de armazenar informações usadas na lógica */

class Card extends JButton {
    
    private int id = -1;
    private String iconString = "";
    
    protected Card() {
    
        this.addActionListener(l -> {
           StartGame.rules.revealTile(this);
        });
    }
    
    protected int getId() {
        return this.id;
    }
    
    protected void setId(int id) {
        this.id = id;
    }
    
    protected void setIconString(String iconString) {
        this.iconString = iconString;
    }
    
    protected String getIconString() {
        return this.iconString;
    }
    
}
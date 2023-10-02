// Classe dedicada à toda lógica do jogo da memória
// Aqui também é onde a "engenharia" será encontrada
package AppPackage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/** Contém a lógica do jogo da memória e o funcionamento geral do temporizador */

public class GameRules {
    
    protected static File directory = new File("src/main/java/AppPackage/Images"); // Diretorio de imagens
    protected static File[] images = directory.listFiles(); // Coleta todas as imagens do diretorio
    
    protected static boolean started = false; // Quando verdadeiro, irá rodar o temporizador
    private static int active = 0; // Variável que vai monitorar quantidade de botões revelados
    
    private static int gridSize = StartGame.gridSize; // Adiquire o tamanho do grid
    private static int selected = Window.selected; // Verifica a dificuldade
    protected static int time = StartGame.time; // Tempo
    private static int marked = 0; // Quantidade de botões selecionados corretamente
    
    protected static Card activeCard1; // Botão ativado 1
    protected static Card activeCard2; // Botão ativado 2
    
    private static Countdown countdown; // Temporizador
    
    /** Indica a imagem dada à carta de acordo com seu indicador*/
    
    private static void giveIcon(Card card) {
        int index = card.getId();
        int designatedIcon;
        
        if(index >= StartGame.tiles.length/2) designatedIcon = index - StartGame.tiles.length/2;
        else designatedIcon = index;
        
        card.setBackground(Color.BLACK);
        
        Icon icon = new ImageIcon("src/main/java/AppPackage/Images/"+images[designatedIcon].getName());
        
        card.setIcon(icon);
        card.setIconString("src/main/java/AppPackage/Images/"+images[designatedIcon].getName());
        card.setFocusPainted(false);
        
    }
    
    /** Remove a imagem da carta*/
    
    private static void removeIcon(Card card) {
        
        card.setEnabled(true);
        card.setBackground(new Color(136, 0, 0));
        card.setIcon(null);
        
    }
    
    /** Verifica se as cartas selecionadas possuem a mesma imagem*/
    
    private static void check(Card card1, Card card2) {
        
        if(card1.getIconString().equals(card2.getIconString()) && card1.getId() != card2.getId()) {
            
            // Removendo actionListeners
            
            for(ActionListener e : card1.getActionListeners()) {
                card1.removeActionListener(e);
            }
            
            for(ActionListener e : card2.getActionListeners()) {
                card2.removeActionListener(e);
            }
            
            marked += 2; // Acrescentando número de combinações encontradas
            if(marked == StartGame.tiles.length) gameWin();
        }
        
        // Pondo um temporizador para dar tempo de ver o ícone antes de ser apagado
        
        else {
            
            TimerTask remove = new TimerTask() {
                @Override
                public void run() {
                    removeIcon(card1);
                    removeIcon(card2);
                }      
            };
            Timer delay = new Timer();
            delay.schedule(remove, 200);
           
        }
    }
    
    /** Define procedimentos para a verificação da carta e aciona o temporizador quando chamado pela primeira vez*/
    
    protected static void revealTile(Card card) {
        giveIcon(card);
        
        if(active == 0) {
            activeCard1 = card;
            active++;
        }
        
        else {
            activeCard2 = card;
            check(activeCard1, activeCard2);
            active = 0;
        }
        
        if(!started) {
            countdown = new Countdown();
            countdown.startTimer(time);
            started = true;
        }
    
    }
    
    /** Cria a frase de vitória e cria o botão para o menu principal*/
    
    private static void gameWin() {
        
        countdown.timer.cancel();
        countdown.task.cancel();
        StartGame.timerLabel.setForeground(Color.GREEN);
        StartGame.timerLabel.setText("<html><h1><b>Você Venceu!</b></h1></html>");
        StartGame.timerPanel.add(new ReturnToMenu("<html><h1><b>Menu</b></h1></html>"));
    }
    
    /** Cria a frase de derrota e cria o botão para o menu principal*/
    
    protected static void gameOver() {
        
        // Desabilitando os botões
        
        for(Card card : StartGame.tiles) {
            
            for(ActionListener al : card.getActionListeners()) {
                card.removeActionListener(al);
            }
        }
        
        countdown.timer.cancel();
        StartGame.timerLabel.setForeground(Color.red);
        StartGame.timerLabel.setText("<html><h1><b>Você Perdeu!</b></h1></html>");
        countdown.timer.cancel();
        StartGame.timerPanel.add(new ReturnToMenu("Menu"));
    }
}

/** Cria o botão que leva o jogador a tela inicial, também reinicia a variável que aciona o temporizador*/

class ReturnToMenu extends JButton  {
    
    protected ReturnToMenu(String name) {
        this.setText(name);
        this.addActionListener(l -> {
            StartGame.frame.dispose();
            new Window();
            GameRules.started = false;
        });
    }
}

/** Modifica o texto do JLabel da classe StartGame, conta do tempo indicado à 0*/

class Countdown {
    
    public static Timer timer;
    public static TimerTask task;
    
    protected static void startTimer(int time) {
        
        System.out.println(time);
        
        timer = new Timer();
        
        task = new TimerTask() {
            
            int currentTime = time;
            @Override
            public void run() {
                
                if(currentTime >= 0) {
                    StartGame.timerLabel.setText("<html> <h1> <b><i> " + currentTime + "</i></b> </h1> </html>");
                    currentTime--;
                }
                else {
                    GameRules.gameOver();
                }
            }
        };
        
        timer.scheduleAtFixedRate(task, 0 ,1000);
    }
    
}
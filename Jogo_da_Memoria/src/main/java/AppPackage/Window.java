
package AppPackage;
import javax.swing.*;
import java.awt.*;
import java.io.*;

// A classe Window cria a janela de ínicio de jogo
// O jogo em si será criado na classe StartGame

/** Cria uma janela (frame) com três seletores de dificuldade e um botão de início linkado diretamente
 a uma outra classe (StartGame) que rodará a tela de jogo*/

public class Window {
    
    protected static JFrame frame; // Tela inicial
    private static JPanel containerPanel; // Janela de suporte para as demais
    private static JPanel startGamePanel; // Janela de seleção e inicio de jogo
    private static JPanel radioPanel; // Janela de estatísticas
    private JButton startButton; // Botão de inicio de jogo
    private JRadioButton[] difficulties; // Botões de dificuldade
    private ButtonGroup btnGrp; // Grupo dos botões de dificuldade
    private static BufferedReader reader;
    
    protected static int selected; // Indica dificuldade selecionada
    
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Coleta o tamanho da tela
    
    /** Cria a janela principal do jogo da memória **/
    
    public Window() {
        frame = new JFrame("Jogo Da Memória");
        containerPanel = new JPanel();
        startGamePanel = new JPanel();
        radioPanel = new JPanel();
        startButton = new JButton("<html> <h1> <b> Começar </b> </h1> </html>");
        difficulties = new JRadioButton[3];
        btnGrp = new ButtonGroup();
        
        selected = 0; // Indica que a dificuldade padrão é a fácil
        
        // Inicializando os JRadioButtons
        
        for(int i = 0; i < 3; i++) {
            int id = i;
            difficulties[i] = new JRadioButton("");
            difficulties[i].addActionListener(e -> selected = id);
            btnGrp.add(difficulties[i]);
        }
        
        // Dando nome e atributos aos botões radio
        
        difficulties[0].setText("<html> <h2> <b> Fácil </b> </h2> </html>");
        difficulties[0].setSelected(true);
        difficulties[0].setHorizontalAlignment(SwingConstants.CENTER);
        difficulties[1].setText("<html> <h2> <b> Médio </b> </h2> </html>");
        difficulties[1].setHorizontalAlignment(SwingConstants.CENTER);
        difficulties[2].setText("<html> <h2> <b> Difícil </b> </h2> </html>");
        difficulties[2].setHorizontalAlignment(SwingConstants.CENTER);
        
        // Configurando e estilizando os componentes
        
        startGamePanel.setBackground(Color.GRAY);
        radioPanel.setBackground(Color.GRAY);
        
        startButton.addActionListener(e -> new StartGame(selected));
        
        radioPanel.setLayout(new GridLayout(1, 0)); // O segundo parametro está em 0 para possibilitar expansões
        
        radioPanel.add(difficulties[0]);
        radioPanel.add(difficulties[1]);
        radioPanel.add(difficulties[2]);
        
        startGamePanel.add(startButton);
        
        containerPanel.setLayout(new BorderLayout());
        
        containerPanel.add(radioPanel, BorderLayout.NORTH);
        containerPanel.add(startGamePanel);
        
        // Setando as configurações do frame
        
        frame.setBackground(Color.DARK_GRAY);
        frame.add(containerPanel);
        frame.setDefaultCloseOperation(3);
        frame.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        frame.setVisible(true);
        frame.pack();
        
    }
    
}

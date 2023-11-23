import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad {
    // Declaração das variáveis usadas
    private JTextArea textArea;
    private JFrame frame;
    private JFileChooser fileChooser;
    private File currentFile;
    private boolean isModified;
    private JLabel status;

    //Construtor da classe Notepad.
    public Notepad() {
        //Criação janela JFrame.
        frame = new JFrame("Notepad - Programação II");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Criação da área de texto da janela (fonte padrão ao abrir documento).
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));


        // Criação dos componentes da janela.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenu fontMenu = new JMenu("Fontes");
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        JMenuItem saveMenuItem = new JMenuItem("Salvar");
        JMenuItem saveAsMenuItem = new JMenuItem("Salvar Como");
        JMenuItem exitMenuItem = new JMenuItem("Sair");
        JMenuItem arialMenuItem = new JMenuItem("Arial (tamanho 14)");
        JMenuItem courierMenuItem = new JMenuItem("Courier New (tamanho 24)");
        JMenuItem aboutMenuItem = new JMenuItem("Sobre");
        status = new JLabel("Caracteres: 0   Modificado: Não");

        // Adicionando os itens de menus aos menus.
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exitMenuItem);
        fontMenu.add(arialMenuItem);
        fontMenu.add(courierMenuItem);
        helpMenu.add(aboutMenuItem);

        // Adicionando os menus na barra de menu.
        menuBar.add(fileMenu);
        menuBar.add(fontMenu);
        menuBar.add(helpMenu);

        // Configuração Action Listeners.
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        arialMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = new Font("Arial", Font.PLAIN, 14);
                textArea.setFont(font);
            }
        });

        courierMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = new Font("Courier New", Font.PLAIN, 24);
                textArea.setFont(font);
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateStatus();
                setModified(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStatus();
                setModified(false);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStatus();
                setModified(true);
            }
        });

        // Configuração do layout da janela (posições).
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(status, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    // Métodos do código ('abrir arquivo', 'salvar', 'salvar como' e status da quantidade de caracteres e 'modificado').
    private void openFile() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(currentFile);
                BufferedReader br = new BufferedReader(reader);
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
                setModified(false);
                br.close();
                reader.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao abrir o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

        private void saveFile() {
        if (currentFile != null) {
            try {
                FileWriter writer = new FileWriter(currentFile);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(textArea.getText());
                bw.close();
                writer.close();
                setModified(false);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao salvar o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveAsFile(); // Chama o método "Salvar como" caso não tenha nenhum arquivo selecionado nesse momento.
        }
    }

    private void saveAsFile() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(currentFile);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(textArea.getText());
                bw.close();
                writer.close();
                setModified(false);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao salvar o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setModified(boolean modified) {
        isModified = modified;
        // updateStatus();
    }

    private void updateStatus() {
        int charCount = textArea.getText().length();
        String modifiedStatus = isModified ? "Sim" : "Não";
        status.setText("Caracteres: " + charCount + "   Modificado: " + modifiedStatus);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(frame, "Editor de texto simples feito na Disciplina de Programação II.\nVersão 1.0", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }

    //Função main para rodar o código.
    public static void main(String[] args) {
        new Notepad();
    }
}

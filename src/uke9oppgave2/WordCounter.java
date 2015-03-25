package uke9oppgave2;

import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author André
 */
public class WordCounter extends JFrame {
    
   
    private JTextField characterField;
    private JTextField wordField;
    private JTextField lineField;
    
    private JButton chooseB;
    private JFileChooser fileChooser;
    
    /**
     * Constructor
     */
    public WordCounter() {
        setup();
        
    }
    
    /**
     * Sets up the user interface components
     */
    public void setup() {
        setTitle("Word Count");
        chooseB = new JButton("Choose file");
        chooseB.addActionListener(new InputListener());
        
        characterField = new JTextField(10);
        characterField.setEditable(false);
        wordField = new JTextField(10);
        wordField.setEditable(false);
        lineField = new JTextField(10);
        lineField.setEditable(false);
        
        setLayout(new FlowLayout());
        add(new JLabel("Teller opp tegn, ord og linjer i valgt tekstfil"));
        add(chooseB);
        add(new JLabel("Totalt antall tegn: "));
        add(characterField);
        add(new JLabel("Totalt antall ord: "));
        add(wordField);
        add(new JLabel("Totalt antall linjer: "));
        add(lineField);
        
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        setSize(310, 200);
        setVisible(true);
        
    }
    
    /**
     * 
     * @param fileName Saves the document to this
     * 
     * @return Color of the paintbrush
     */
    public void count(String fileName) {
        
        try (BufferedReader inFile = new BufferedReader(new FileReader(fileName))) {
            
            // Counts charactacters, words and lines:
            int characters = 0;
            int words = 0;
            int lines = 0;
            String line = null;
            while ((line = inFile.readLine()) != null) {
                lines++;
                characters += line.length();
                Scanner wordCounter = new Scanner(line);
                while (wordCounter.hasNext()) {
                    wordCounter.next();
                    words++;
                }
                wordCounter.close();
            }
            characterField.setText("" + characters);
            wordField.setText("" + words);
            lineField.setText("" + lines);
            
        }
        catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Finner ikke fil " + fileName, "Advarsel",
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, "Problem med fillesing.", "Advarsel", 
                    JOptionPane.ERROR_MESSAGE);
        }
    } // end of method count
    
    
    private class InputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int choice = fileChooser.showOpenDialog(WordCounter.this);
            if (choice  == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                count(f.getPath());
            }
        }
    }
}

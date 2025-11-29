package com.example.quiz;

import javax.swing;
import javax.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.util.*;
public class App {
	private JFrame frame;
	private JTextField quizTextField;
	private JComboBox<String> questionTypeCombo;
	private JTextArea questionTextArea;
	private JTextField option1Field, option2Field, option3Field, option4Field;
	private JTextField correctAnswerField;
	private JLabel statusLabel;
	private final List<Question> questions = new ArrayList<>();
	private JPanel takeQuizPanelContent;
	private CardLayout cardLayout;
	private final List<QuestionUI> questionUis = new ArrayList<>();
	private final currentQuestionIndex=0;
	private JLabel questionIndexLabel;
	private JLabel quizTitleLabel;
	private JButton prevBtn;
	private JButton nextBtn;
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(()-> new App().createAndShowGUI());
    }
    private void createAndShowGUI() {
    	frame = new JFrame("Quiz Management System");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(900,600);
    	frame.setLocationRelativeTo(null);
    	JTabbedPane tabbenPane = new JTabbedPane();
    	tabbenPane.addTab("Admin", createAdminPanel);
    	tabbenPane.addTab("Take Quiz", createTakeQuizPanel);
    	frame.add(tabbenPane);
    	frame.setVisible(true);
    }
    
    private JPanel createAdminPanel() {
    	JPanel panel = new JPanel(new BorderLayout());
    	JPanel formPanel = new JPanel();
    	formPanel.setLayout(new BoxLayout(formPanel,BoxLayout.Y_AXIS));
    	formPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	JPanel titlePanel = new JPanel(new BorderLayout(5,5));
    	titlePanel.add(new JLabel("Quiz Title:"),BorderLayout.WEST);
    	quizTitleField = new JTextField();
    	titlePanel.add(quizTitleField,BorderLayout.CENTER);
    	formPanel.add(titlePanel);
    	formPanel.add(Box.createVerticalStrut(10));
    	JPanel typePanel = new JPanel(new Borderlayout(5,5));
    	typePanel.add(new JLabel("Question Type"),BorderLayout.WEST);
    	questionTypeCombo = new JComboBox<>(new String[] {"MCQ","True/False","Text"});
    	typePanel.add(questionTypeCombo,BorderLayout.CENTER);
    	formPanel.add(typePanel);
    	formPanel.add(Box.createVerticalStrut(10));
    	JPanel qPanel = new JPanel(new BorderLayout(5,5));
    	qPanel.add(new JLabel("Question:"),BorderLayout.NORTH);
    	questionTextArea = new JTextArea(3,40);
    	questionTextArea.setLineWrap(true);
    	questionTextArea.setWrapStyleWord(true);
    	qPanel.add(new JScrollPanel(questionTextArea),BorderLayout.CENTER);
    	formPanel.add(qPanel);
    	formPanel.add(Box.createVerticalStrut(10));
    	JPanel optionsPanel = new JPanel(new GridLayout(4,2,5,5));
    	optionsPanel.setBorder(new TitleBorder("Options used only for MCQ"));
    	optionsPanel.add(new JLabel("Option 1:"));
    	option1Field = new JTextField();
    	optionsPanel.add(option1Field);
    	optionsPanel.add(new JLabel("Option 2:"));
    	option2Field = new JTextField();
    	optionsPanel.add(option1Field);
    	optionsPanel.add(new JLabel("Option 3:"));
    	option3Field = new JTextField();
    	optionsPanel.add(option1Field);
    	optionsPanel.add(new JLabel("Option 4:"));
    	option4Field = new JTextField();
    	optionsPanel.add(option1Field);
    	formPanel.add(optionsPanel);
    	formPanel.add(Box.createVerticalStrut(10));
    }
    
}
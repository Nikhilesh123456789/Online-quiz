package com.example.quiz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;
import java.util.List;
public class App {
	private JFrame frame;
	private JTextField quizTitleField;
	private JComboBox<String> questionTypeCombo;
	private JTextArea questionTextArea;
	private JTextField option1Field, option2Field,option3Field,option4Field;
	private JTextField correctAnswerField;
	private JLabel statusLabel;
	private final List<Question> questions = new ArrayList<>();
	private JPanel takeQuizPanelContent;
	private CardLayout cardLayout;
	private final List<QuestionUI> questionUIs;
	private int currentQuestionIndex=0;
	private JLabel questionLabel;
	private JLabel quizTitleLabel;
	private JButton prevBtn;
	private JButton nextBtn;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new App().createAndShowGUI();
	}
	private void createAndShowGUI() {
		frame = new JFrame("Quiz Management System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,600);
		frame.setLocationRelativeTo(null);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Admin",createAdminPanel());
		tabbedPane.addTab("Take Quiz",createQuizPanel());
		frame.add(tabbedPane);
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
		titlePanel.add(quizTitleField, BorderLayout.CENTER);
		formPanel.add(titlePanel);
		formPanel.add(Box.createVerticalStrut(10));
		JPanel typePanel = new JPanel(new BorderLayout(5,5));
		typePanel.add(new JLabel("Question Type:"),BorderLayout.WEST);
		questionTypeCombo = new JComboBox<>(new String[] {"MCQ","True/False","Text"});
		typePanel.add(questionTypeCombo),BorderLayout.CENTER);
		formPanel.add(typePanel);
		formPanel.add(Box.createVerticalStrut(10));
		JPanel qPanel = new JPanel(new BorderLayout(5,5));
		qPanel.add(new JLabel("Question"),BorderLayout.NORTH);
		questionTextArea = new JTextArea(3,40);
		questionTextArea.setLineWrap(true);
		questionTextArea.setWrapStyleWord(true);
		qPanel.add(new JScrollPane(questionTextArea),BorderLayout.CENTER);
		formPanel.add(qPanel);
		formPanel.add(Box.createVerticalStrut(10));
		JPanel optionsPanel = new JPanel(new GridLayout(4,2,5,5));
		optionsPanel.setBorder(new TitledBorder("Options (used only for MCQ"));
		optionsPanel.add(new JLabel("Option 1:"));
		option1Field = new JTextField();
		optionsPanel.add(option1Field);
		optionsPanel.add(new JLabel("Option 2:"));
		option2Field = new JTextField();
		optionsPanel.add(option2Field);
		optionsPanel.add(new JLabel("Option 3:"));
		option3Field = new JTextField();
		optionsPanel.add(option3Field);
		optionsPanel.add(new JLabel("Option 4:"));
		option4Field = new JTextField();
		optionsPanel.add(option4Field);
		formPanel.add(optionsPanel);
		formPanel.add(Box.createVerticalStrut(10));
		JPanel caPanel = new JPanel(new BorderLayout(5,5));
		caPanel.add(new JLabel("Correct Answer (exact text/option):"),BorderLayout.WEST);
		correctAnswerField = new JTextField();
		caPanel.add(correctAnswerField, BorderLayout.CENTER);
		formPanel.add(caPanel);
		formPanel.add(Box.createVerticalStrut(10));
		JButton addQuestionBtn = new JButton("Add Question");
		addQuestionBtn.addActionListener(e-> addQuestion());
		JButton clearQuizBtn = new JButton("Clear All Questions");
		clearQuizBtn.addActionListener(e-> clearQuiz());
		statusLabel = new JLabel("Questions:0");
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.add(addQuestionBtn);
		bottomPanel.add(clearQuizBtn);
		bottomPanel.add(Box.createHorizontalStrut(20));
		bottomPanel.add(statusLabel);
		panel.add(formPanel,BorderLayout.CENTER);
		panel.add(bottomPanel,BorderLayout.SOUTH);
		return panel;
	}
	private void addQuestion() {
		String title = quizTitleField.getText().trim();
		if(title.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please enter quiz title.","Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String qText = questionTextArea.getText().trim();
		if(qText.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please enter question text","Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String typeStr = (String) questionTypeCombo.getSelectedItem();
		QuestionType type = QuestionType.TEXT;
		if("MCQ".equals(typeStr)) {
			type = QuestionType.MCQ;
		}
		else if("True/False".equals(typeStr)) {
			type = QuestionType.TRUE_FALSE;
		}
		String correct = correctAnswerField.getText().trim();
		if(correct.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please enter correct answer","Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		List<String> options = new ArrayList<>();
		if(type==QuestionType.MCQ) {
			if(!option1Field.getText().trim().isEmpty()) {
				options.add(option1Field.getText().trim());
			}
			if(!option2Field.getText().trim().isEmpty()) {
				options.add(option2Field.getText().trim());
			}
			if(!option3Field.getText().trim().isEmpty()) {
				options.add(option3Field.getText().trim());
			}
			if(!option4Field.getText().trim().isEmpty()) {
				options.add(option4Field.getText().trim());
			}
			if(options.size()<2) {
				JOptionPane.showMessageDialog(frame,  "Please enter at least two options for MCQ.","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!options.contains(correct)) {
				JOptionPane.showMessageDialog(frame, "Correcr answer must match one of the options","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		Question q = new Question(qText, type, options, correct);
		questions.add(q);
		statusLabel.setText("Questons:" + questions.size());
		JOptionPane.showMessageDialog(frame, "Question added to quiz,","Info",JOptionPane.INFORMATION_MESSAGE);
		questionTextArea.setText("");
		option1Field.setText("");
		option2Field.setText("");
		option3Field.setText("");
		option4Field.setText("");
		correctAnswerField.setText("");
	}
	private void clearQuiz() {
		
	}
} 
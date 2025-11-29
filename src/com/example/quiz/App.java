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
	private final List<QuestionUI> questionUIs=new ArrayList<>();
	private int currentQuestionIndex=0;
	private JLabel questionIndexLabel;
	private JLabel quizTitleLabel;
	private JButton prevBtn;
	private JButton nextBtn;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new App().createAndShowGUI());
	}
	private void createAndShowGUI() {
		frame = new JFrame("Quiz Management System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,600);
		frame.setLocationRelativeTo(null);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Admin",createAdminPanel());
		tabbedPane.addTab("Take Quiz",createTakeQuizPanel());
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
		typePanel.add(questionTypeCombo,BorderLayout.CENTER);
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
		questions.clear();
		statusLabel.setText("Questions:0");
		JOptionPane.showMessageDialog(frame, "All questions cleared.","Info",JOptionPane.INFORMATION_MESSAGE);
	}
	private JPanel createTakeQuizPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JButton loadQuizBtn = new JButton("Load Quiz");
		JButton submitBtn = new JButton("Submit Answers");
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(new JLabel("After creating questions in Admin tab, click 'Load Quiz' here"));
		topPanel.add(loadQuizBtn);
		topPanel.add(submitBtn);
		JPanel centerPanel = new JPanel(new BorderLayout());
		quizTitleLabel = new JLabel("");
		quizTitleLabel.setFont(quizTitleLabel.getFont().deriveFont(Font.BOLD,16f));
		quizTitleLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		centerPanel.add(quizTitleLabel,BorderLayout.NORTH);
		cardLayout = new CardLayout();
		takeQuizPanelContent = new JPanel(cardLayout);
		JScrollPane scrollPane = new JScrollPane(takeQuizPanelContent);
		centerPanel.add(scrollPane,BorderLayout.CENTER);
		JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		prevBtn = new JButton("<");
		nextBtn = new JButton(">");
		questionIndexLabel = new JLabel("No questions loaded");
		prevBtn.addActionListener(e-> showQuestion(currentQuestionIndex-1));
		nextBtn.addActionListener(e-> showQuestion(currentQuestionIndex+1));
		navPanel.add(prevBtn);
		navPanel.add(questionIndexLabel);
		navPanel.add(nextBtn);
		centerPanel.add(navPanel,BorderLayout.SOUTH);
		loadQuizBtn.addActionListener(e->buildQuizUI());
		submitBtn.addActionListener(e->calculateScore());
		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(centerPanel,BorderLayout.CENTER);
		return panel;

	}
	
	private void buildQuizUI() {
		takeQuizPanelContent.removeAll();
		questionUIs.clear();
		currentQuestionIndex = 0;
		if(questions.isEmpty()) {
			quizTitleLabel.setText("");
			questionIndexLabel.setText("No questions defined");
			prevBtn.setEnabled(false);
			nextBtn.setEnabled(false);
			JPanel msgPanel = new JPanel();
			msgPanel.add(new JLabel("No questions defined. Please add questions in Admin tab."));
			takeQuizPanelContent.add(msgPanel,"MSG");
			cardLayout.show(takeQuizPanelContent, "MSG");
		}
		else {
			String title = quizTitleField.getText().trim();
			if(!title.isEmpty()) {
				quizTitleLabel.setText("Quiz:" + title);
			}
			else {
				quizTitleLabel.setText("Quiz");
			}
			int index=0;
			for(Question q:questions) {
				QuestionUI ui = new QuestionUI(q,index+1);
				questionUIs.add(ui);
				takeQuizPanelContent.add(ui.getPanel(),"Q"+index);
				index++;
			}
			showQuestion(0);
		}
		takeQuizPanelContent.revalidate();
		takeQuizPanelContent.repaint();
	}
	private void showQuestion(int newIndex) {
		if(!questionUIs.isEmpty()) {
			return;
		}
		if(newIndex <0 || newIndex >=questionUIs.size()) {
			return;
		}
		currentQuestionIndex = newIndex;
		cardLayout.show(takeQuizPanelContent, "Q" + currentQuestionIndex);
		int total = questionUIs.size();
		questionIndexLabel.setText("Question" + (currentQuestionIndex+1)+ "of" + total);
		prevBtn.setEnabled(currentQuestionIndex>0);
		nextBtn.setEnabled(currentQuestionIndex<total-1);
	}
	private void calculateScore() {
		if(questionUIs.isEmpty()) {
			JOptionPane.showMessageDialog(frame,"Please load quiz first.","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		int correctCount=0;
		StringBuilder details = new StringBuilder();
		int i=1;
		for(QuestionUI ui:questionUIs) {
			String userAns = ui.getUserAnswer();
			if(userAns==null || userAns.trim().isEmpty()) {
				details.append("Q").append(i).append(":No answer\n");
			}
			else if(ui.getQuestion().isCorrect(userAns)) {
				correctCount++;
				details.append("Q").append(i).append(":Correct\n");
			}
			else {
				details.append("Q").append(i).append("Wrong Correct:").append(ui.getQuestion().getCorrectAnswer()).append("\n");
			}
			i++;
		}
		int total = questionUIs.size();
		JOptionPane.showMessageDialog(frame, "Score:" + correctCount + "/" +total+"\n\nDetails\n" + details, "Quiz Result",JOptionPane.INFORMATION_MESSAGE);
	}
	private enum QuestionType{
		MCQ, TRUE_FALSE,TEXT
	}
	private static class Question{
		private final String text;
		private final QuestionType type;
		private final List<String> options;
		private final String correctAnswer;
		public Question(String text,QuestionType type,List<String> options,String correctAnswer) {
			this.text = text;
			this.type=type;
			this.options=options;
			this.correctAnswer=correctAnswer;
		}
		public String getText() {
			return text;
		}
		public QuestionType getType() {
			return type;
		}
		public List<String> getOptions(){
			return options;
		}
		public String getCorrectAnswer() {
			return correctAnswer;
		}
		public boolean isCorrect(String userAnswer) {
			if(userAnswer==null) {
				return false;
			}
			return correctAnswer.trim().equalsIgnoreCase(userAnswer.trim());
		}
	}
	private static class QuestionUI{
		private final Question question;
		private final JPanel panel;
		private ButtonGroup buttonGroup;
		private JTextField textField;
		public QuestionUI(Question  question, int index) {
			this.question = question;
			this.panel = new JPanel();
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createTitledBorder("Question" + index));
			JLabel qLabel = new JLabel(question.getText());
			panel.add(qLabel);
			if(question.getType()==QuestionType.MCQ) {
				buttonGroup = new ButtonGroup();
				for(String opt:question.getOptions()) {
					JRadioButton rb = new JRadioButton(opt);
					buttonGroup.add(rb);
					panel.add(rb);
				}
			}
			else if(question.getType()==QuestionType.TRUE_FALSE) {
				buttonGroup = new ButtonGroup();
				JRadioButton trueBtn = new JRadioButton("True");
				JRadioButton falseBtn = new JRadioButton("False");
				buttonGroup.add(trueBtn);
				buttonGroup.add(falseBtn);
				panel.add(trueBtn);
				panel.add(falseBtn);
			}
			else {
				textField = new JTextField(30);
				panel.add(textField);
			}
		}
		public JPanel getPanel() {
			return panel;
		}
		public Question getQuestion() {
			return question;
		}
		public String getUserAnswer() {
			if(question.getType()==QuestionType.MCQ || question.getType()==QuestionType.TRUE_FALSE) {
				if(buttonGroup==null) {
					return "";
				}
				Enumeration<AbstractButton> buttons = buttonGroup.getElements();
				while(buttons.hasMoreElements()) {
					AbstractButton b = buttons.nextElement();
					if(b.isSelected()) {
						return b.getText();
					}
				}
				return "";
			}
			else {
				return textField!=null ? textField.getText():"";
			}
		}
	}
} 
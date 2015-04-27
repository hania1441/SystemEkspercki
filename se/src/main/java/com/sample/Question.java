package com.sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Question {
	public static final int SINGLE = 0;
	public static final int MULTIPLE = 1;
	public static final int ANSWERED = 1;
	
	private String fullText;
	private ArrayList<String> answers;
	private Integer type;
	
	public Question(){
		
	}
	
	public Question (String fullText, String[] answers, Integer type) {
		this.fullText = fullText;
		this.answers = new ArrayList<String>();
		this.answers.addAll(Arrays.asList(answers));
		this.type = type;
	}
	
	public String getFullText() {
		return this.fullText;
	}
	
	
	public ArrayList<String> getAnswers() {
		return this.answers;
	}
	
	public Integer getType() {
		return this.type;
	}

	/*public void setFullText(String fullText) {
		this.fullText = fullText;
	}
		
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}*/
}

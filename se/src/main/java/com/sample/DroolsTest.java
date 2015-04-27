package com.sample;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {
	public ArrayList<Question> questions = new ArrayList<Question>();
	public ArrayList<Question> allQuestions = new  ArrayList<Question>();
	
	public ArrayList<Offer> offers = new  ArrayList<Offer>();
	public ArrayList<Offer> allOffers = new  ArrayList<Offer>();
	
	private Question currentQuestion;
	
	private final JFrame window = new JFrame();
	
	private final JTextPane text = new JTextPane();
	private final JButton button = new JButton();
	
	private final ArrayList<JRadioButton> rdButtons = new ArrayList<JRadioButton>();
	private final ArrayList<JCheckBox> chkButtons = new ArrayList<JCheckBox>();
	
	private final ButtonGroup rdGroup = new ButtonGroup();
	private final ButtonGroup chkGroup = new ButtonGroup();
	
	public KnowledgeBase kbase;
	public StatefulKnowledgeSession ksession;
	public KnowledgeRuntimeLogger logger;
	
	public void initFrame() {
		window.setResizable(false);
		window.getContentPane().setBackground(new Color(0, 191, 255));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(700,500);
		window.setTitle("System ekspercki");
		
		
		button.setBounds(227, 358, 251, 44);
		window.getContentPane().setLayout(null);
		window.getContentPane().add(button);
				
		text.setBackground(new Color(255, 250, 205));
		text.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		text.setEditable(false);
		
		text.setBounds(87, 96, 516, 125);
		window.getContentPane().add(text);
		window.setVisible(true);
		
		for (int i = 0; i < 4; i++) {
			rdButtons.add(new JRadioButton());
			rdButtons.get(i).setBounds(90, 125+i*30, 500, 30);
			window.getContentPane().add(rdButtons.get(i));
			rdGroup.add(rdButtons.get(i));
			rdButtons.get(i).setVisible(false);;
			rdButtons.get(i).setBackground(new Color(0, 191, 255));
			
			chkButtons.add(new JCheckBox());
			chkButtons.get(i).setBounds(90, 125+i*30, 500, 30);
			window.getContentPane().add(chkButtons.get(i));
			//chkGroup.add(chkButtons.get(i));
			chkButtons.get(i).setVisible(false);;
			chkButtons.get(i).setBackground(new Color(0, 191, 255));
		}
		
		text.setText("Witamy w systemie eksperckim !!\r\nCelem tego programu jest dialog z u\u017Cytkownikiem na temat jego wymarzonych wakacji.\r\nPo zadaniu odpowiednich pyta\u0144 system wskazuje dobran\u0105 dla u\u017Cytkownika ofert\u0119 wakacji.");
		button.setText("Rozpocznij");
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (button.getText() == "Rozpocznij") {
					button.setText("OK");
					text.setSize(516, 27);
					ksession.fireAllRules();
					getQuestionAndDisplay();
				}
				else if (button.getText() == "Zakoncz") {
					window.dispose();
				}
				else if (insertAnswer()) {
					rdGroup.clearSelection();
					for (int i = 0; i < 4; i++){
						chkButtons.get(i).setSelected(false);
					}
		
					
					ksession.fireAllRules();
					getQuestionAndDisplay();
				}
				
				
				//startQuiz();
			}
		});
	}
	
	public void initAllQuesitons(){
		//dodanie wszystkich mozliwych pytan
		allQuestions.add(new Question("Gdzie chcia³byœ pojechaæ?", new String[]{"Polska","Za granice"}, Question.SINGLE));	//0 -
		allQuestions.add(new Question("Na jak d³ugo chcia³byœ pojechaæ?", new String[]{"Weekend", "Tydzieñ", "Dwa tygodnie"}, Question.SINGLE)); //1 -
		allQuestions.add(new Question("W jakiej cenie chcia³byœ otrzymaæ ofertê?", new String[]{"ponizej 2500","powyzej 2500"}, Question.SINGLE)); //2 -
		allQuestions.add(new Question("Co chcia³byœ robic?", new String[]{"Zwiedzac", "Odpoczywac", "Uprawiac sport"}, Question.MULTIPLE));//3 -
		allQuestions.add(new Question("Chcesz zwiedzaæ z przewodnikiem czy bez?", new String[]{"Z przewodnikiem","Bez przewodnika"}, Question.SINGLE)); //4
		allQuestions.add(new Question("Gdzie chcia³byœ odpocz¹æ?", new String[]{"Nad morzem","W gorach"}, Question.SINGLE));//5 -
		allQuestions.add(new Question("Jaka pora roku Cie interesuje?", new String[]{"Latem","Zim¹"}, Question.SINGLE));//6 
		allQuestions.add(new Question("Co dok³adnie chcia³byœ zobaczyæ?", new String[]{"Parki karjobrazowe","Architekture sakralna","Muzea"}, Question.MULTIPLE));//7 -
		allQuestions.add(new Question("Wybierz kontynent, gdzie chcia³byœ pojechaæ?", new String[]{"Afryka","Ameryka", "Europa"}, Question.SINGLE));//8 -
		allQuestions.add(new Question("Jaki sport chcia³byœ uprawiaæ?", new String[]{"Jazda na nartach", "Plywanie", "Wspinaczka"}, Question.SINGLE));//9 
		allQuestions.add(new Question("Jak¹ pla¿ê wolisz?", new String[]{"Piaszczyst¹","Kamienist¹"}, Question.SINGLE));//10 -
		allQuestions.add(new Question("W jakie góry chcesz pojechaæ?", new String[]{"Karpaty","Gory Swietokrzyskie","Sudety"}, Question.SINGLE));//11 -
		allQuestions.add(new Question("Do jakiego Pañstwa w Ameryce chcesz pojechaæ?", new String[]{"Brazylia","Argentyna", "Stany Zjednoczone"}, Question.SINGLE));//12 -
		allQuestions.add(new Question("W jakie góry chcesz pojechaæ?", new String[]{"Kaukaz","Alpy"}, Question.SINGLE));//13
		allQuestions.add(new Question("Gdzie chcesz mieszkaæ w trakcie pobytu?", new String[]{"W hotelu","Gospodarsto agroturystyczne", "Pod namiotem"}, Question.SINGLE));//14 -
		allQuestions.add(new Question("Nad jakim morzem chcesz odpocz¹æ?", new String[]{"Nad morzem Œródziemnym","Nad morzem Adriatyckim"}, Question.SINGLE));//15
		allQuestions.add(new Question("Jakie szlaki górskie Ciê interesuj¹?", new String[]{"Trudne","£atwe"}, Question.SINGLE));//16 -
		allQuestions.add(new Question("Jakie miasto w Stanach Zjednoczonych chcesz najbardziej zobaczyæ?", new String[]{"Nowy York","Los Angeles", "Waszyngton"}, Question.SINGLE));//17 -
		allQuestions.add(new Question("Jakie pañstwo Afryki wolisz?", new String[]{"Tunezja","Egipt"}, Question.SINGLE));//18 -
		allQuestions.add(new Question("Jakie pansto chcialbys najbardziej zobaczyc?", new String[]{"Grecja","Norwegia", "Hiszpania", "Irlandia"}, Question.SINGLE));//19 -
	}
	
	public void initAllOffers() {
		//dodanie wszystkich mozliwych ofert
		allOffers.add(new Offer("Polska", "Warszawa", "Atos", 400));                   //1
		allOffers.add(new Offer("Polska", "Kraków", "Doris", 350));                    //2
		allOffers.add(new Offer("Polska", "Ko³obrzeg", "Diva", 400));                  //3
		allOffers.add(new Offer("Polska", "Zakopane", "Domek Maria", 600));            //4
		allOffers.add(new Offer("Polska", "Gdañsk", "Bazyl", 900));                    //5
		allOffers.add(new Offer("Polska", "Wroc³aw", "Aradnis", 800));                 //6
		allOffers.add(new Offer("Polska", "Bia³ystok", "Ibis", 750));                  //7
		allOffers.add(new Offer("Polska", "Poznañ", "Przy Rynku", 850));               //8
		allOffers.add(new Offer("Polska", "Szczecin", "Focus", 1100));                 //9
		allOffers.add(new Offer("Polska", "£ódŸ", "Delicjusz", 900));                  //10
		allOffers.add(new Offer("Polska", "Zakopane", "Willa Orla", 1500));            //11
		allOffers.add(new Offer("Polska", "Puszczykowo", "Green", 1000));              //12
		allOffers.add(new Offer("Polska", "Mielno", "Pensjonat Arkadia", 1100));       //13
		allOffers.add(new Offer("Polska", "Drawsko", "Agroturystyka u Marioli", 1050));      //14
		allOffers.add(new Offer("Polska", "Karpacz", "Noclegi Anna", 1200));            //15
		allOffers.add(new Offer("Polska", "Bia³ka Tatrzañska", "S³oneczny Dworek", 1400));   //16
		allOffers.add(new Offer("Polska", "Krynica Zdrój", "Hotel Baginscy SPA", 1600));       //17
		
		allOffers.add(new Offer("Madagaskar", "Antenanariva", "Tamasina", 2100));              //18
		allOffers.add(new Offer("W³ochy", "Rzym", "Salaria", 1500));                            //19
		allOffers.add(new Offer("Egipt", "Kair", "Ramses Hillon", 2300));                      //20
		allOffers.add(new Offer("Turcja", "Anamur", "Anemonia SPA", 2400));					//21
		allOffers.add(new Offer("Turcja", "Anamur", "Dom jednorodzinny, pokój", 2000));		//22
		allOffers.add(new Offer("Majorka", "Palma de Mallorca", "Saratoga", 2200));			//23
		allOffers.add(new Offer("Chorwacja", "Dubrovnik", "Berkeley Hotel", 1700));			//24
		allOffers.add(new Offer("Francja", "Prades", "Hexagone", 2150));						//25
		allOffers.add(new Offer("Francja", "Lourdes", "Les glicines", 1800));					//26
		allOffers.add(new Offer("Norwegia", "Lom", "Schronisko górskie Scandinavia", 2300));  //27
		allOffers.add(new Offer("Wlochy", "Pallermo", "Posta", 2400));						//28
		allOffers.add(new Offer("Wlochy", "Apulia", "Tonic", 2100));							//29
		allOffers.add(new Offer("RPA", "Pretoria", "Pezula Resort", 3100));					//30
		allOffers.add(new Offer("RPA", "Mel Moth", "Cabanas", 2800));							//31
		allOffers.add(new Offer("Maroko", "Rabat", "Royal Atlas", 3300));						//32
		allOffers.add(new Offer("Brazylia", "Rio de Janeiro", "Atlantico Sul", 4000));		//33
		allOffers.add(new Offer("Argentyna", "Buenos Aires", "Mine Hotel", 4500));			//34
		allOffers.add(new Offer("Stany Zjednoczone", "Nowy York", "Pennsylvania", 4400));		//35
		allOffers.add(new Offer("Stany Zjednoczone", "Los Angeles", "Sofitel", 4300));		//36
		allOffers.add(new Offer("Stany Zjednoczone", "Waszyngton", "Row NYC", 4700));			//37
		allOffers.add(new Offer("Tunezja", "Sousse", "Marabout", 4300));                      //38
		allOffers.add(new Offer("Egipt", "Sharm El Sheikh", "Grand Plaza", 4000));			//39
		allOffers.add(new Offer("Portugalia", "Altura", "Eurotel Altura", 3500));				//40
		allOffers.add(new Offer("Hiszpania", "Lanzarotte", "Blue Sea Lagos De Cesar", 3700));			//41
		allOffers.add(new Offer("Szwecja", "Malmo", "Scandic Hotel", 3000));					//42
		allOffers.add(new Offer("Norwegia", "Kristiansand", "Rica Norge", 2900));				//43
		allOffers.add(new Offer("Irlandia", "Ardmore", "Ardmore Hotel", 3300));				//44
		allOffers.add(new Offer("Grecja", "Chalkidiki", "Nostos", 3700));						//45
		allOffers.add(new Offer("Chorwacja", "Korcula", "Grand Hotel Neum - Wellness & Spa ", 4200));			//46
		allOffers.add(new Offer("Gruzja", " Bakuriani Tbilisi", "Bakuriani Zimowa Stolica Gruzji", 2800));			//47
		allOffers.add(new Offer("Szwajcaria","Saas-Fee", "Apartamenty Acimo", 4500));			//48
		allOffers.add(new Offer("Austria","Kaprun", "Antonius", 4300));						//49
		allOffers.add(new Offer("Niemcy","Hamburg", "Wedina", 3500));							//50
	}
	
	public void initKnowledgeBase () {
		try {
			kbase = readKnowledgeBase();
			ksession = kbase.newStatefulKnowledgeSession();
			logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
    		ksession.setGlobal("questions", questions);
    		ksession.setGlobal("allQuestions", allQuestions);
    		ksession.setGlobal("offers", offers);
    		ksession.setGlobal("allOffers", allOffers);
		}
		catch (Throwable t) {
            t.printStackTrace();
        }
	}
	
	public void getQuestionAndDisplay() {
		//pobranie pytania lub przejscie do wyswietlenia oferty
		if (questions.size() > 0) {
			currentQuestion = questions.get(0);
			questions.remove(0);
			displayQuestion();
		} 
		else {
			displayOffer();
			button.setText("Zakoncz");
			System.out.println("display offer");
			//displayOffer();
		}
	}
	
	public boolean insertAnswer() {
		if (currentQuestion.getType() == Question.SINGLE) {
			for (int i =0; i < 4; i++) {
				if (rdButtons.get(i).isSelected()) {
					ksession.insert(new Answer(rdButtons.get(i).getText()));
					currentQuestion = null;
						return true;
				}
			}
		} else {
			for (int i =0; i < 4; i++) {
				if (chkButtons.get(i).isSelected()) {
					ksession.insert(new Answer(chkButtons.get(i).getText()));
				}
			}
			currentQuestion = null;
			return true;
		}
			
		return false;
	}
	
	public void displayQuestion() {
		if (currentQuestion != null) {
			text.setText(currentQuestion.getFullText());
			
			//radio buttons
			if (currentQuestion.getType() == Question.SINGLE) {
				for (int i = 0; i < 4; i++) {
					rdButtons.get(i).setVisible(false);
					chkButtons.get(i).setVisible(false);
					if (i < currentQuestion.getAnswers().size()) {
						rdButtons.get(i).setText(currentQuestion.getAnswers().get(i));
						rdButtons.get(i).setVisible(true);
					}	
				}
				
			}
			
			//check boxes
			if (currentQuestion.getType() == Question.MULTIPLE) {
				for (int i =0; i < 4; i++) {
					rdButtons.get(i).setVisible(false);
					chkButtons.get(i).setVisible(false);
					if (i < currentQuestion.getAnswers().size()) {
						chkButtons.get(i).setText(currentQuestion.getAnswers().get(i));
						chkButtons.get(i).setVisible(true);
						
					}
				}
			}
		}
	}
	

	public void displayOffer() {
		for (int i = 0; i < 4; i++) {
			rdButtons.get(i).setVisible(false);
			chkButtons.get(i).setVisible(false);
		}
		text.setLocation(50, 30);
		text.setSize(600, 27+25*(offers.size()));
		
		if (offers.size() == 0) {
			
			text.setText("Przykro mi ale nie znaleziono ofertu która spelnialaby kryteria.");
		} 
		else {
			text.setText("Oferty dla ciebie:\n");
			for (int i = 0; i < offers.size(); i++) {
				text.setText(text.getText()+"Panstwo: "+offers.get(i).getCountry()+"   ");
				text.setText(text.getText()+"Miasto: "+offers.get(i).getTown()+"   ");
				text.setText(text.getText()+"Hotel: "+offers.get(i).getHotel()+"   ");
				text.setText(text.getText()+"Cena: "+offers.get(i).getPrice()+"\n");
			}
		}
	}
	
    public static final void main(String[] args) {
    	int a = 10;
    	DroolsTest start = new DroolsTest();
    	start.initKnowledgeBase();
    	start.initAllQuesitons();
    	start.initAllOffers();
    	start.initFrame();
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }
}

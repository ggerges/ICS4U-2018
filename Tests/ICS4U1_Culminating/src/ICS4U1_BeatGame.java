import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.Timer;
 
/*
 * ICS4U1_BeatGame.java
 * Culminating Assignment
 * Jackie Chen, Grace Ting
 * ICS4U1_02
 * January 18, 2018
 */

//The ICS4U1_BeatGame class allows the user to play the music game
public class ICS4U1_BeatGame {
 
    public static void main(String[] args) {
        MyUtil.importSongs();
        MenuFrame frame = new MenuFrame("M U S I C   G A M E");
    }
}
 
//MenuFrame class is the main class for the starting menu and game
class MenuFrame extends JFrame implements ActionListener, KeyListener{
	private Container c;
	private JPanel menu, menuButtons,keys;
    private JButton buttonArray[], keyButtons[];
    private String buttons[] = {"Load Game", "Create BeatMap", "Instructions"};
    private ImageIcon programIcon;
    private JFileChooser fc;
    private Note playerNote;
    private BeatMap mapIn;
    private GamePanel gamePanel;
    private String keyText[] = {"d", "f", "space", "j", "k"};
    private Timer playerTimer;
    private long playerStartTime, playerCurrentTime, playerNoteTime, playerSongTime;
    private int playerNoteKey, score, bestScore;
    private boolean donePlaying;
    private JTextArea scoreInfo;

    public MenuFrame(String s) {
        setTitle(s);
        c = getContentPane();
        
        programIcon = new ImageIcon("programIcon.png");
        setIconImage(programIcon.getImage());
        
        fc = new JFileChooser();
        mapIn = new BeatMap();
        
        buttonArray = new JButton[buttons.length];  
        gamePanel = new GamePanel();
        
        scoreInfo = new JTextArea();
        scoreInfo.setEditable(false);
        updateHighscore(score);
        scoreInfo.setText(" High Score: " + bestScore + "     Current Score: " + score);
        
        keys = new JPanel();
        keyButtons = new JButton[keyText.length];
        for (int i = 0; i < keyText.length; i++) {
            keyButtons[i] = new JButton(keyText[i]);
            keyButtons[i].setBackground(new Color(246,246,246));
            keyButtons[i].setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
            keyButtons[i].setBorderPainted(false);
            keyButtons[i].addKeyListener(this);
            keys.add(keyButtons[i]);
        }
        keys.addKeyListener(this);
        
        menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,170));
        menuButtons = new JPanel();
        menuButtons.setLayout(new GridLayout(3,1,10,10));
        for (int i = 0; i < buttons.length; i++) {
            buttonArray[i] = new JButton(buttons[i]);
            buttonArray[i].setBackground(new Color(246,246,246));
            buttonArray[i].setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
            buttonArray[i].setBorderPainted(false);
            buttonArray[i].addActionListener(this);
            menuButtons.add(buttonArray[i]);
        }
        menu.add(menuButtons);
        menu.setBackground(new Color(156,149,131));
        menuButtons.setBackground(new Color(156,149,131));
        
        c.add(menu, BorderLayout.CENTER);
        c.add(scoreInfo, BorderLayout.NORTH);
        
        setSize(300,500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playerTimer) {
        }else {
            JButton b = (JButton) e.getSource();
            if (b == buttonArray[0]) {
            	try{
                    int returnVal = fc.showOpenDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        openMethod(file);
                        remove(menu);
                        c.add(scoreInfo, BorderLayout.NORTH);
                        c.add(gamePanel, BorderLayout.CENTER);
                        c.add(keys, BorderLayout.SOUTH);
                        keys.setFocusable(true);
                        keys.requestFocusInWindow();
                        revalidate();
                        playerTimer = new Timer(10,this);
                        playerStartTime = System.nanoTime()/10000000;
                        playerTimer.start();
                        score = 0;
                        donePlaying = false;
                    }
                }catch(Exception ex){  
                    System.out.print("Error" + ex);
                }  
            } else if (b == buttonArray[1]) {
            	//If the "Create BeatMap" button is pressed, open a Map Creator window
            	 MapCreator creator = new MapCreator("Map Creator");
            } else if (b == buttonArray[2]) {
            	//If the "Instructions" button is pressed, open an Instructions window
            	InstructionFrame instruc = new InstructionFrame("Instructions");
            }
        }
    }
    //Opens an existing map
    public void openMethod(File filePath){
        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        try{
            fileIn = new FileInputStream(filePath);
            objectIn = new ObjectInputStream(fileIn);
            mapIn = (BeatMap)(objectIn.readObject());
            gamePanel.setMap(mapIn);
            objectIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   //Graphics of the main menu
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(246,246,246));
        g.setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
        g.drawString("M  U  S  I  C     G  A  M  E", 87, 140);
        g.fillRect(222, 473, 3, 20);
        g.fillArc(222,470, 20,15, -180, -230 );
        g.fillArc(206, 470, 20, 15, 0, 230 );
    }
 
    public void keyTyped(KeyEvent e) {}
    //Timing system for main game
    public void keyPressed(KeyEvent e) {
        playerCurrentTime = System.nanoTime()/10000000 - 430;
        playerNoteTime = playerCurrentTime - playerStartTime;
//      System.out.println("Player Current Time: " + playerNoteTime);
        if (e.getKeyCode() == 68){
            keyButtons[0].setBackground(Color.yellow);
            playerNoteKey = 1;
        }
        if (e.getKeyCode() == 70) {
            keyButtons[1].setBackground(Color.yellow);
            playerNoteKey = 2;
        }
        if (e.getKeyCode() == 32) {
            keyButtons[2].setBackground(Color.yellow);
            playerNoteKey = 3;
        }
        if (e.getKeyCode() == 74) {
            keyButtons[3].setBackground(Color.yellow);
            playerNoteKey = 4;
        }
        if (e.getKeyCode() == 75) {
            keyButtons[4].setBackground(Color.yellow);
            playerNoteKey = 5;
        }
        playerNote = mapIn.getNextNote();
        if (playerNote != null) {
            if (playerNote.compareNote(playerNoteTime, playerNoteKey) == 1) {
                score += playerNote.calcScore(50, playerNoteTime);
                scoreInfo.setText(" High Score: " + bestScore + "     Current Score: " + score);
            }
        }
        try {
        	//Highscore updating once the song is over
            if (playerNoteTime> MyUtil.clip.getMicrosecondLength()/10000) {
                System.out.println(MyUtil.clip.getMicrosecondLength()/10000);
                donePlaying = true;
                System.out.println("song is over");
                updateHighscore(score);
                scoreInfo.setText(" High Score: " + bestScore + "     Current Score: " + score);
                this.dispose();
                MenuFrame frame = new MenuFrame("M U S I C   G A M E");
            }
        }
        catch(Exception ex) {}
    }
 
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 68){
            keyButtons[0].setBackground(new Color(246, 246, 246));
        }
        if (e.getKeyCode() == 70) {
            keyButtons[1].setBackground(new Color(246, 246, 246));
        }
        if (e.getKeyCode() == 32) {
            keyButtons[2].setBackground(new Color(246, 246, 246));
        }
        if (e.getKeyCode() == 74) {
            keyButtons[3].setBackground(new Color(246, 246, 246));
        }
        if (e.getKeyCode() == 75) {
            keyButtons[4].setBackground(new Color(246, 246, 246));
        }
    }
    public void paintComponent(Graphics gr) {
        super.paintComponents(gr);
        gr.setColor(Color.black);
        gr.drawString("Highscore: " + bestScore + "   Your Score: " + score, 20, 30);
    }
    //Method that reads file and updates highscore
    public void updateHighscore (int score) {
        File myFile = new File("a.out");
        FileReader fr;
        BufferedReader reader;
        String lineIn="0";
        try {
            fr = new FileReader(myFile);
            reader = new BufferedReader(fr);
            lineIn = reader.readLine();
            reader.close();
            fr.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
 
        bestScore = Integer.parseInt(lineIn);
       
        if (bestScore < score) {
            System.out.println("New High Score!");
            BufferedWriter bw;
            try {
                bw=new BufferedWriter(new FileWriter ("a.out"));
                bw.write("" + score);
                bw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
 
}

//InstructionFrame class shows the instructions to the game
class InstructionFrame extends JFrame {
    private JTextArea ins;
    private Container c;
	public InstructionFrame(String s) {
		setTitle(s);
        setResizable(false);
		setSize(300,200);
    	setVisible(true);
    	c=getContentPane();
    	ins = new JTextArea();
    	ins.setEditable(false);
    	ins.setText(" INSTRUCTIONS\n\n Create your own beatmap by importing .wav files\n or load a pre-existing map.\n" + 
    			" Begin to press the keys (d, f, spacebar, j, and k)\n once the song starts to play.\n" + 
    			" Try to match your presses with the descending keys.\n" + 
    			" Press any key once more after the game to see\n your highscore update.\n" + 
    			"");
    	c.add(ins, BorderLayout.CENTER);
	}
}

//MapCreator class allows user to create their own beatmap
class MapCreator extends JFrame implements ActionListener, KeyListener{
	private Container c;
	private JButton btn_open, btn_save, btn_start;
    private JPanel control, side, main, creator;
    private JLabel selectedSong;
    private JFileChooser fc;
    private SongSelect selector;
    private BeatMap map1;
    private int currentSong, noteKey;
    private long startTime, currentTime, noteTime, songTime;
    private boolean isRecording;
    private Timer songTimer;
    private JButton keys[];
    private String keyText[] = {"d", "f", "space", "j", "k"};
 
    public MapCreator(String s, int currentSong) {
        this.currentSong = currentSong;
        
        setTitle(s);
        setResizable(false);
        setSize(600,300);
        setVisible(true);
        
        control = new JPanel();
        main = new JPanel();
        creator = new JPanel();
        side = new JPanel();
        selectedSong = new JLabel();
        c = getContentPane();
        
        btn_open = new JButton("Open Song");
        btn_save = new JButton("Save");
        btn_start = new JButton("Start Recording");
        isRecording = false;
        
        fc = new JFileChooser();
        
        control.setLayout(new GridLayout(3,1,10,10));
        creator.setLayout(new GridLayout(1, 5, 20,10));
        main.setLayout(new FlowLayout(FlowLayout.CENTER, 20,100));
        side.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 150));
        songTimer = new Timer(10, this);
        btn_open.setBackground(new Color(246,246,246));
        btn_open.setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
        btn_open.setBorderPainted(false);
        btn_open.addActionListener(this);
        control.add(btn_open);
       
        btn_save.setBackground(new Color(246,246,246));
        btn_save.setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
        btn_save.setBorderPainted(false);
        btn_save.addActionListener(this);
        control.add(btn_save);
       
        btn_start.setBackground(new Color(246,246,246));
        btn_start.setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
        btn_start.setBorderPainted(false);
        btn_start.addActionListener(this);
        control.add(btn_start);
       
        keys = new JButton[keyText.length];
        for (int i = 0; i < keyText.length; i++) {
            keys[i] = new JButton(keyText[i]);
            keys[i].setBackground(new Color(246,246,246));
            keys[i].setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
            keys[i].setBorderPainted(false);
            main.add(keys[i]);
        }
       
        if (currentSong == -1) {
            selectedSong.setText("Selected song:                    ");
        }else {
            selectedSong.setText("Selected song: " + MyUtil.currentSong(currentSong).getName());
        }
        main.add(selectedSong);
        side.add(control);
        creator.add(main);
       
        main.addKeyListener(this);
        btn_save.addKeyListener(this);
        btn_open.addKeyListener(this);
        btn_start.addKeyListener(this);
        main.setFocusable(true);
       
        c.add(side, BorderLayout.EAST);
        c.add(creator, BorderLayout.CENTER);
    }
    public MapCreator(String s) {
        this(s, -1);
    }
    // if the song is finished playing, the timer is stopped
    public void actionPerformed (ActionEvent e) {
        if (isRecording) {
            currentTime = System.nanoTime()/10000000;
            if (currentTime - startTime > songTime) {
                isRecording = false;
                System.out.println("Song is over");
                songTimer.stop();
            }
            return;
        }
        JButton b = (JButton) e.getSource();
        // if the open button is pressed, opens new window to select songs
        if (b == btn_open) {
            selector = new SongSelect("Song Select");
            this.dispose();
        } else if (b == btn_save) {
            try{
                int returnVal = fc.showSaveDialog(this); 
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile(); 
                    saveMethod(file);
                }
            }catch(Exception ex){
                System.out.print("Error" + ex);
            }
        }
        //if the "Start Recording" button is pressed with a song selected, start playing and recording the beatmap
        else if (b == btn_start) {
            if (currentSong != -1) {
                System.out.println("Recording started");
                songTime = MyUtil.playSong(currentSong);
                System.out.println("Song time: " + songTime);
                map1 = new BeatMap(currentSong);
                startTime = System.nanoTime()/10000000;
                songTimer.start();
                isRecording = true;
                main.setFocusable(true);
                btn_start.setFocusable(false);
            }
        }
    }
    //Records keys pressed and their timing
    public void keyPressed(KeyEvent ev) {
        if (isRecording) {
            noteTime = currentTime - startTime;
            currentTime = System.nanoTime()/10000000;
            //Stops recording when song is over
            if (currentTime - startTime > songTime)
                isRecording = false;
        }
        //Saves the key and sets background to yellow while it is pressed
        if (ev.getKeyCode() == 68){// d
            noteKey = 1;
            keys[0].setBackground(Color.yellow);
        }
        if (ev.getKeyCode() == 70) {// f
            noteKey = 2;
            keys[1].setBackground(Color.yellow);
        }
        if (ev.getKeyCode() == 32) {// space
            noteKey = 3;
            keys[2].setBackground(Color.yellow);
        }
        if (ev.getKeyCode() == 74) {// j
            noteKey = 4;
            keys[3].setBackground(Color.yellow);
        }
        if (ev.getKeyCode() == 75) {// k
            noteKey = 5;
            keys[4].setBackground(Color.yellow);
        }
        // adds note to beatmap
        if (noteKey != -1 && isRecording) {
            map1.addNote(noteTime, noteKey);
            System.out.println("Added note");
            System.out.println("Time: " + noteTime);
            System.out.println("Note:" + noteKey);
        }
    }
    public void keyReleased(KeyEvent ev) {
        if (isRecording) {
            int n = ev.getKeyCode();
            switch(n) {
            case 68: // d
                noteKey = 1;
                keys[0].setBackground(new Color(246, 246, 246));
                break;
            case 70: // f
                noteKey = 2;
                keys[1].setBackground(new Color(246, 246, 246));
                break;
            case 32: // spacebar
                noteKey = 3;
                keys[2].setBackground(new Color(246, 246, 246));
                break;
            case 74: // j
                noteKey = 4;
                keys[3].setBackground(new Color(246, 246, 246));
                break;
            case 75: // k
                noteKey = 5;
                keys[4].setBackground(new Color(246, 246, 246));
                break;
            default:
                noteKey = -1;
                break;
            }
        }else {
            System.out.println("Recording is over");
        }
    }
    public void keyTyped(KeyEvent ev) {}
    public void saveMethod(File filePath){
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
 
        try {
            fout = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(map1);
            fout.close();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
 
// SongSelect class allows user to select a .wav file to play and record
class SongSelect extends JFrame implements ActionListener {
    private Container c;
    private JPanel songSelect, southPanel, base;
    private JButton[] btnArr;
    private JButton selectSong, clearSong;
    private int currentSong;
   
    public SongSelect(String s) {
    	setTitle(s);
        setResizable(false);
        setSize(600,300);
        setVisible(true); 
        
        songSelect = new JPanel();
        southPanel = new JPanel();
        base = new JPanel();
        
        selectSong = new JButton("Select Song");
        selectSong.setBackground(new Color(246,246,246));
        selectSong.setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
        selectSong.setBorderPainted(false);
        selectSong.addActionListener(this);
        
        clearSong = new JButton("Clear Song");
        clearSong.setBackground(new Color(246,246,246));
        clearSong.setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
        clearSong.setBorderPainted(false);
        clearSong.addActionListener(this);
        
        c = getContentPane();
        
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(selectSong);
        southPanel.add(clearSong);
        songSelect.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 150));
        
        btnArr = new JButton[MyUtil.songArr.size()];
        for (int i = 0; i < btnArr.length; i++) {
            btnArr[i] = new JButton(MyUtil.songArr.get(i).getName());
            songSelect.add(btnArr[i]);
            btnArr[i].setBackground(new Color(246,246,246));
            btnArr[i].setFont(new Font("Helvetica Nue", Font.PLAIN, 12));
            btnArr[i].setBorderPainted(false);
            btnArr[i].addActionListener(this);
        }
        
        c.add(songSelect, BorderLayout.CENTER);
        c.add(southPanel, BorderLayout.SOUTH);
    }
 
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if (b == clearSong) {
            try {
                MyUtil.clearSong();
            }catch(Exception ex) {}
        //selectSong opens a MapCreator window with the selected song
        }else if(b == selectSong) {
            try {
                MyUtil.clearSong();
            }catch(Exception ex) {}
            MapCreator creator = new MapCreator("Map Creator", currentSong);
            this.dispose();
        }
        for (int i = 0; i < btnArr.length; i++) {
            if (btnArr[i] == e.getSource()) {
                try {
                    MyUtil.clearSong();
                }catch(Exception ex) {}
                MyUtil.playSong(i);
                currentSong = i;
            }
        }
    }
}

// Utility class is used for loading and playing songs
class MyUtil {
    static ArrayList<Song> songArr = new ArrayList<Song>();
    static File songFolder = new File("Songs");
    static AudioInputStream stream;
    static AudioFormat format;
    static DataLine.Info info;
    static Clip clip;
   
    //importSongs loads the songs from Song folder
    public static void importSongs() {
        File[] files = songFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            songArr.add(new Song(files[i], files[i].getName(), i));
            System.out.println("Adding new song: " + files[i].getName());
        }
    }
    //playSong plays a given song from list of songs
    public static long playSong(int i) {
        try {
            stream = AudioSystem.getAudioInputStream(songArr.get(i).getSongFile());
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return clip.getMicrosecondLength()/10000;
    }
    public static void clearSong() {
        clip.close();
    }
    public static Song currentSong(int i) {
        return songArr.get(i);
    }
}

//Song class saves the file, name, and song togther as an object
class Song implements Serializable{
    private File songFile;
    private String name;
    private int songNum;
   
    public Song(File file, String name, int songNum) {
        songFile = file;
        this.name = name;
        this.songNum = songNum;
    }
    public Song(String name) {
        this(new File(""), name, 0);
    }
   
    public String getName() {
        return name;
    }
    public File getSongFile() {
        return songFile;
    }
    public int getSongNum() {
        return songNum;
    }
}

//BeatMap class saves the song and the notes together
class BeatMap implements Serializable {
    private ArrayList<Note> notes;
    private Song song;
   
    public BeatMap(){}
    public BeatMap(int i) {
        notes = new ArrayList<Note>();
        song = MyUtil.currentSong(i);
    }
    //adds note to beatmap
    public void addNote(long noteTime, int noteKey) {
        notes.add(new Note(noteTime, noteKey));
    }
    public ArrayList<Note> getAllNotes() {
        return notes;
    }
    public int getNumNotes() {
        return notes.size();
    }
    public Song getSong(){
        return song;
    }
    //gets next note that hasn't been played
    public Note getNextNote() {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).hasPlayed == false) {
                //System.out.print(notes.get(i).noteKey);
                notes.get(i).canPlay = true;
                return (notes.get(i));
            }
        }
        return null;
    }
}
//Note class stores timing and key of note
class Note implements Serializable {
    private long noteTime;
    public int noteKey;
    public int moveY;
    private boolean onScreen;
    public boolean hasPlayed, canPlay;
    public Note(long noteTime, int noteKey) {
        this.noteTime = noteTime;
        this.noteKey = noteKey;
        moveY = (int)(noteTime*-1); //the spacing messes up the accuracy of the bars
        onScreen = true;
        hasPlayed = false;
        canPlay = false;
    }
    //recursively calculates score based on player accuracy
    public int calcScore(int range, long pNoteTime) {
        if (range <= 1 || Math.abs(pNoteTime - noteTime) > range) {
            return 1;
        }
        else {
            return range + calcScore(range/2, pNoteTime);
        }
    }
    //compares the player's key presses to the created map for accuracy and returns score
    public int compareNote(long pNoteTime, int pNoteKey) {
        if (pNoteKey == noteKey) {
            //System.out.println("Player note time: " + pNoteTime);
            //System.out.println("Note time: " + noteTime);
            if (canPlay == true && Math.abs(pNoteTime - noteTime) < 100) {
                canPlay = false;
                hasPlayed = true;
                System.out.println("Note hit!");
                return 1;
            } else {
                System.out.println("Note missed");
                return 0;
            }
        } else {
            System.out.println("Note Missed");
            return 0;
        }
    }
    //draws rectangles according to the note's timing and the current time in the song
    public void draw(Graphics g, long currentTime){
        if (moveY + currentTime > 450 && onScreen) {
            //System.out.println("NoteTime: " + noteTime);
            onScreen = false;
            hasPlayed = true;
        }
        if (noteKey == 1) {
            g.fillRect(18, (int) (moveY + currentTime), 35, 5); 
        } else if (noteKey == 2) {
            g.fillRect(64,  (int) (moveY + currentTime) , 35, 5);
        } else if(noteKey == 3) {
            g.fillRect(109, (int) (moveY + currentTime), 60, 5);
        } else if (noteKey == 4){
            g.fillRect(178, (int) (moveY + currentTime), 35, 5);
        } else if (noteKey == 5) {
            g.fillRect(222, (int) (moveY + currentTime), 35, 5);
        }
    }
}
// GamePanel class displays the beatmap for the user
class GamePanel extends JPanel implements ActionListener{
    BeatMap map1;
    Timer gameTimer;
    public long startTime, currentTime;
    private boolean songPlaying;
   
    public GamePanel() {}
   
    public void setMap(BeatMap m){
        map1 = m;
        gameTimer = new Timer(10,this);  //every tick (tick = 0.01s)
        gameTimer.start();
        startTime = System.nanoTime()/10000000;
        songPlaying = false;
    }
    //updates time in song
    public void actionPerformed(ActionEvent e) {
        currentTime = (System.nanoTime()/10000000) - startTime;
        //System.out.println("Current Time: " + currentTime);
        if (e.getSource() == gameTimer) {
            if (currentTime > 423 && !songPlaying) {
                MyUtil.playSong(map1.getSong().getSongNum());
                songPlaying = true;
            }
            repaint();
        }
    }
    //draws notes
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < map1.getAllNotes().size(); i++) {
            map1.getAllNotes().get(i).draw(g, currentTime);
        }
        repaint();
       
    }
}
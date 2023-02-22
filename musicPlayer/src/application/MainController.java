package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable  {
	@FXML
	MediaPlayer mediaPlayer, mp2 ;
	Media media,video;
	@FXML
	File file,file2;
	Timer timer;
	TimerTask task;
	@FXML
	MediaView spec;
	@FXML
	Label songName;
	@FXML
	ProgressBar bar;
	@FXML
	Slider vol;
	FileChooser filechoose;
	Stage stage;
	boolean running;
	double current,end;
	@FXML private AnchorPane ap;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		file2 = new File("\\musicPlayer\\musicPlayer\\spec.mp4"); 
		video = new Media(file2.toURI().toString());
		mp2 = new MediaPlayer(video);
		spec.setMediaPlayer(mp2);
		mp2.setCycleCount(MediaPlayer.INDEFINITE);
		
	}
	@FXML
	public void load(MouseEvent e) {
		file = this.add();
		if(mediaPlayer != null) {
		if(mediaPlayer.statusProperty().getValue() == MediaPlayer.Status.PLAYING) {
			mediaPlayer.stop();}}
		if(file != null) {
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		String name = file.getName();
		songName.setText("Now Playing: " + name);
		mediaPlayer.play();
		beginTime();}
		playVideo();
	}
	@FXML
public void menu(MouseEvent e) {
		load(null);
		
	}
	public void play(MouseEvent e) {
		
		if(file != null) {
			mediaPlayer.play();
			beginTime();
			playVideo();
		}
	}
	public void pause(MouseEvent e) {
		if(file != null) {
			endTimer();
			mediaPlayer.pause();
			pauseVideo();}
	}
	public File add() {
		ExtensionFilter list = new ExtensionFilter(".mp3","*.mp3");
		stage = (Stage) ap.getScene().getWindow();
		filechoose = new FileChooser();	
		filechoose.getExtensionFilters().add(list);
		File fi = filechoose.showOpenDialog(stage);
		return fi;
	}
	public void beginTime() {
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				running = true;
				current = mediaPlayer.getCurrentTime().toSeconds();
				 end = media.getDuration().toSeconds();
				bar.setProgress(current/end); 
				if(current/end == 1) {
					endTimer();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0,1000);
	}
	public void endTimer() {
		
		running = false;
		timer.cancel();
	}
	
	public void backward() {
		if(file != null) {
			
			if(bar.getProgress()> 0.1) {
	
				bar.setProgress((current-10)/end);
				mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds()-10));
				}
		}
		}
	@FXML
public void forward() {
	if(file != null) {
		bar.setProgress((current+10)/end);
		mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds()+10));
	}
	}
public void volume(MouseEvent e) {
		if(file != null) {
			vol.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					mediaPlayer.setVolume(vol.getValue()* 0.01);
				
			}
			
		});
		}
}
public void playVideo() {
	mp2.play();	
}
public void pauseVideo() {
	mp2.pause();
}

}

package com.example.mediaplayer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.control.Slider;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.util.Random;
import javafx.stage.Stage;
import javax.swing.*;

public class HelloApplication extends Application {

    private MediaPlayer mediaPlayer;
    private double previousVolume = 0.5;
    private double playbackSpeed = 1.0;

    private String formatTime(Duration duration) {

        int totalSeconds = (int) duration.toSeconds();

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void start(Stage stage) {

        ObservableList<String> playlist = FXCollections.observableArrayList();
        ObservableList<String> playlistPaths = FXCollections.observableArrayList();
        ObservableList<String> allMedia = FXCollections.observableArrayList();
        ObservableList<String> allMediaPaths = FXCollections.observableArrayList();
        ObservableList<String> recentMedia = FXCollections.observableArrayList();
        ObservableList<String> recentMediaPaths = FXCollections.observableArrayList();

        Label molly = new Label("MOLLY");
        molly.getStyleClass().add("molly-logo");

        Label mediaText = new Label(" MEDIA");
        mediaText.getStyleClass().add("media-logo");

        HBox logoTitle = new HBox(2);
        logoTitle.getChildren().addAll(molly, mediaText);
        Label tagline = new Label("Play. Listen. Watch. Enjoy");
        tagline.getStyleClass().add("tagline");

        Label status = new Label("READY");

        Label playlistTitle = new Label("MEDIA LIBRARY");
        playlistTitle.getStyleClass().add("playlist-title");

        Button recentBtn = new Button("RECENT");
        Button videoBtn = new Button("VIDEO");
        Button audioBtn = new Button("AUDIO");
        Button playlistBtn = new Button("PLAYLIST");

        recentBtn.getStyleClass().addAll("library-tab", "active-tab");
        videoBtn.getStyleClass().add("library-tab");
        audioBtn.getStyleClass().add("library-tab");
        playlistBtn.getStyleClass().add("library-tab");

        recentBtn.setMaxWidth(Double.MAX_VALUE);
        videoBtn.setMaxWidth(Double.MAX_VALUE);
        audioBtn.setMaxWidth(Double.MAX_VALUE);
        playlistBtn.setMaxWidth(Double.MAX_VALUE);

        HBox libraryTabs = new HBox(5);
        libraryTabs.getChildren().addAll(recentBtn,videoBtn,audioBtn,playlistBtn);
        libraryTabs.setAlignment(Pos.CENTER);
        HBox.setHgrow(recentBtn, Priority.ALWAYS);
        HBox.setHgrow(videoBtn, Priority.ALWAYS);
        HBox.setHgrow(audioBtn, Priority.ALWAYS);
        HBox.setHgrow(playlistBtn, Priority.ALWAYS);

        playlist.add("video.mp4");
        playlistPaths.add(getClass().getResource("/media/video.mp4").toExternalForm());

        allMedia.add("video.mp4");
        allMediaPaths.add(getClass().getResource("/media/video.mp4").toExternalForm());


        ListView<String> playlistView = new ListView<>(playlist);

        playlistView.setPrefWidth(200);

        String videoPath = getClass().getResource("/media/video.mp4").toExternalForm();

        Media media = new Media(videoPath);

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(1050);
        mediaView.setFitHeight(350);
        mediaView.setPreserveRatio(true);

        videoBtn.setOnAction(e -> {

            playlist.clear();
            playlistPaths.clear();

            videoBtn.getStyleClass().add("active-tab");
            recentBtn.getStyleClass().remove("active-tab");
            audioBtn.getStyleClass().remove("active-tab");
            playlistBtn.getStyleClass().remove("active-tab");

            for (int i = 0; i < allMedia.size(); i++) {

                String name = allMedia.get(i);

                if (name.toLowerCase().endsWith(".mp4")) {

                    playlist.add(name);
                    playlistPaths.add(allMediaPaths.get(i));
                }
            }
        });

        recentBtn.setOnAction(e -> {

            playlist.setAll(recentMedia);
            playlistPaths.setAll(recentMediaPaths);

            recentBtn.getStyleClass().add("active-tab");

            videoBtn.getStyleClass().remove("active-tab");
            audioBtn.getStyleClass().remove("active-tab");
            playlistBtn.getStyleClass().remove("active-tab");
        });

        audioBtn.setOnAction(e -> {

            playlist.clear();
            playlistPaths.clear();

            for (int i = 0; i < allMedia.size(); i++) {

                String name = allMedia.get(i).toLowerCase();

                if (name.endsWith(".mp3") || name.endsWith(".wav")  || name.endsWith(".m4a")) {
                    playlist.add(allMedia.get(i));
                    playlistPaths.add(allMediaPaths.get(i));
                }
            }

            audioBtn.getStyleClass().add("active-tab");
            recentBtn.getStyleClass().remove("active-tab");
            videoBtn.getStyleClass().remove("active-tab");
            playlistBtn.getStyleClass().remove("active-tab");
        });

        playlistBtn.setOnAction(e -> {

            playlist.setAll(allMedia);
            playlistPaths.setAll(allMediaPaths);

            playlistBtn.getStyleClass().add("active-tab");
            recentBtn.getStyleClass().remove("active-tab");
            videoBtn.getStyleClass().remove("active-tab");
            audioBtn.getStyleClass().remove("active-tab");
        });

        Label settingsTitle = new Label("⚙ PLAYBACK SETTINGS");
        settingsTitle.getStyleClass().add("settings-title");

        Label speedLabel = new Label("Playback Speed");
        speedLabel.getStyleClass().add("setting-label");

        Button speed05 = new Button("0.5x");
        Button speed10 = new Button("1.0x");
        Button speed15 = new Button("1.5x");
        Button speed20 = new Button("2.0x");

        speed05.getStyleClass().add("speed-button");
        speed10.getStyleClass().addAll("speed-button", "active-speed");
        speed15.getStyleClass().add("speed-button");
        speed20.getStyleClass().add("speed-button");

        speed05.setOnAction(e -> {
            if (mediaPlayer != null) {

                playbackSpeed = 0.5;
                mediaPlayer.setRate(playbackSpeed);

                speed05.getStyleClass().add("active-speed");
                speed10.getStyleClass().remove("active-speed");
                speed15.getStyleClass().remove("active-speed");
                speed20.getStyleClass().remove("active-speed");
            }
        });

        speed10.setOnAction(e -> {
            if (mediaPlayer != null) {

                playbackSpeed = 1.0;
                mediaPlayer.setRate(playbackSpeed);

                speed10.getStyleClass().add("active-speed");
                speed05.getStyleClass().remove("active-speed");
                speed15.getStyleClass().remove("active-speed");
                speed20.getStyleClass().remove("active-speed");
            }
        });

        speed15.setOnAction(e -> {
            if (mediaPlayer != null) {

                playbackSpeed = 1.5;
                mediaPlayer.setRate(playbackSpeed);

                speed15.getStyleClass().add("active-speed");
                speed05.getStyleClass().remove("active-speed");
                speed10.getStyleClass().remove("active-speed");
                speed20.getStyleClass().remove("active-speed");
            }
        });

        speed20.setOnAction(e -> {
            if (mediaPlayer != null) {

                playbackSpeed = 2.0;
                mediaPlayer.setRate(playbackSpeed);

                speed20.getStyleClass().add("active-speed");
                speed05.getStyleClass().remove("active-speed");
                speed10.getStyleClass().remove("active-speed");
                speed15.getStyleClass().remove("active-speed");
            }
        });

        Label resolutionLabel = new Label("Resolution");
        resolutionLabel.getStyleClass().add("setting-label");

        Button autoBtn = new Button("AUTO");
        Button res360Btn = new Button("360p");
        Button res480Btn = new Button("480p");
        Button res720Btn = new Button("720p");

        autoBtn.getStyleClass().addAll("resolution-button", "active-resolution");
        res360Btn.getStyleClass().add("resolution-button");
        res480Btn.getStyleClass().add("resolution-button");
        res720Btn.getStyleClass().add("resolution-button");

        autoBtn.setOnAction(e -> {

            mediaView.setFitWidth(1050);
            mediaView.setFitHeight(350);

            autoBtn.getStyleClass().add("active-resolution");
            res360Btn.getStyleClass().remove("active-resolution");
            res480Btn.getStyleClass().remove("active-resolution");
            res720Btn.getStyleClass().remove("active-resolution");
        });


        res360Btn.setOnAction(e -> {

            mediaView.setFitWidth(640);
            mediaView.setFitHeight(360);

            res360Btn.getStyleClass().add("active-resolution");
            autoBtn.getStyleClass().remove("active-resolution");
            res480Btn.getStyleClass().remove("active-resolution");
            res720Btn.getStyleClass().remove("active-resolution");
        });


        res480Btn.setOnAction(e -> {

            mediaView.setFitWidth(854);
            mediaView.setFitHeight(480);

            res480Btn.getStyleClass().add("active-resolution");
            autoBtn.getStyleClass().remove("active-resolution");
            res360Btn.getStyleClass().remove("active-resolution");
            res720Btn.getStyleClass().remove("active-resolution");
        });


        res720Btn.setOnAction(e -> {

            mediaView.setFitWidth(1280);
            mediaView.setFitHeight(720);

            res720Btn.getStyleClass().add("active-resolution");
            autoBtn.getStyleClass().remove("active-resolution");
            res360Btn.getStyleClass().remove("active-resolution");
            res480Btn.getStyleClass().remove("active-resolution");
        });

        Label subtitleLabel = new Label("Captions / Subtitles");
        subtitleLabel.getStyleClass().add("setting-label");

        Button subtitleBtn = new Button("OFF");
        subtitleBtn.getStyleClass().add("subtitle-button");

        Button loadSubtitleBtn = new Button("+ SUBTITLE");
        loadSubtitleBtn.getStyleClass().add("subtitle-button");

        HBox subtitleControls = new HBox(8);
        subtitleControls.getChildren().addAll(subtitleBtn,loadSubtitleBtn);

        HBox resolutionButtons = new HBox(6);
        resolutionButtons.getChildren().addAll(autoBtn,res360Btn,res480Btn,res720Btn);

        HBox speedButtons = new HBox(6);
        speedButtons.getChildren().addAll(speed05, speed10, speed15, speed20);

        VBox settingsBox = new VBox(12);
        settingsBox.getChildren().addAll(settingsTitle,speedLabel,speedButtons,resolutionLabel,resolutionButtons,subtitleLabel,subtitleControls);
        settingsBox.getStyleClass().add("settings-box");
        settingsBox.setMinWidth(260);
        settingsBox.setMaxWidth(Double.MAX_VALUE);


        Label audioIcon = new Label("♫");
        audioIcon.getStyleClass().add("audio-icon");

        Label audioTitle = new Label("MOLLY MEDIA AUDIO");
        audioTitle.getStyleClass().add("audio-title");

        Label audioMessage = new Label("Now playing your audio");
        audioMessage.getStyleClass().add("audio-message");

        HBox equalizer = new HBox(5);
        equalizer.setAlignment(Pos.CENTER);

        for (int i = 0; i < 9; i++) {

            Region bar = new Region();

            bar.setPrefWidth(6);
            bar.setPrefHeight(20);

            bar.getStyleClass().add("equalizer-bar");

            equalizer.getChildren().add(bar);
        }

        Random random = new Random();

        Timeline equalizerAnimation = new Timeline(
                new KeyFrame(Duration.millis(180), e -> {

                    for (javafx.scene.Node node : equalizer.getChildren()) {

                        Region bar = (Region) node;

                        double newHeight = 10 + random.nextInt(35);

                        bar.setPrefHeight(newHeight);
                    }
                })
        );

        equalizerAnimation.setCycleCount(Timeline.INDEFINITE);


        VBox audioScreen = new VBox(12);
        audioScreen.getChildren().addAll(audioIcon, audioTitle,equalizer,audioMessage);

        audioScreen.setAlignment(Pos.CENTER);
        audioScreen.setMaxWidth(Double.MAX_VALUE);
        audioScreen.getStyleClass().add("audio-screen");
        audioScreen.setVisible(false);
        audioScreen.setManaged(false);

        StackPane mediaContainer = new StackPane();

        mediaContainer.getChildren().addAll(mediaView,audioScreen);
        mediaContainer.setPrefHeight(430);
        mediaContainer.setMaxHeight(Double.MAX_VALUE);
        mediaContainer.setMaxWidth(Double.MAX_VALUE);
        mediaContainer.getStyleClass().add("media-container");

        VBox videoBox = new VBox();
        videoBox.getChildren().addAll(mediaContainer);
        videoBox.setAlignment(Pos.CENTER);
        videoBox.setPadding(new Insets(20));
        VBox.setVgrow(mediaContainer, Priority.ALWAYS);
        videoBox.getStyleClass().add("video-box");

        Label nowPlaying = new Label("Now Playing: video.mp4");
        nowPlaying.getStyleClass().add("now-playing");

        Label currentTime = new Label("00:00");
        Label totalTime = new Label("00:00");

        currentTime.getStyleClass().add("time-label");
        totalTime.getStyleClass().add("time-label");

        Slider progressSlider = new Slider();
        progressSlider.setMin(0);

        Slider volumeSlider = new Slider(0,1,0.5);
        volumeSlider.setPrefWidth(120);

        mediaPlayer.setVolume(0.5);

        volumeSlider.valueProperty().addListener((obs,oldValue,newValue)->{
            mediaPlayer.setVolume(newValue.doubleValue());
        });

        progressSlider.setOnMouseReleased(e->{
            mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
        });

        setupMediaProgress(mediaPlayer,progressSlider,currentTime,totalTime);

        playlistView.getSelectionModel().selectedItemProperty().addListener((
                observable,oldValue,newValue
                )->{

            if (newValue != null){

                if (mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }
             int selectedIndex = playlistView.getSelectionModel().getSelectedIndex();
                String selectedPath = playlistPaths.get(selectedIndex);

                Media selectedMedia = new Media(selectedPath);

                mediaPlayer = new MediaPlayer(selectedMedia);
                mediaPlayer.setRate(playbackSpeed);

                mediaView.setMediaPlayer(mediaPlayer);

                String lowerName = newValue.toLowerCase();

                if (lowerName.endsWith(".mp3") || lowerName.endsWith(".m4a") || lowerName.endsWith(".wav") ) {

                    mediaView.setVisible(false);
                    mediaView.setManaged(false);

                    audioScreen.setVisible(true);
                    audioScreen.setManaged(true);

                    audioMessage.setText(newValue);


                    equalizerAnimation.play();

                } else {

                    equalizerAnimation.stop();

                    mediaView.setVisible(true);
                    mediaView.setManaged(true);

                    audioScreen.setVisible(false);
                    audioScreen.setManaged(false);
                }

                mediaPlayer.setOnEndOfMedia(()->{

                    int nextIndex = playlistView.getSelectionModel().getSelectedIndex() + 1;

                    if (nextIndex < playlist.size()){
                        playlistView.getSelectionModel().select(nextIndex);
                    } else{
                        playlistView.getSelectionModel().select(0);
                    }
                });

                progressSlider.setValue(0);

                currentTime.setText("00:00");
                totalTime.setText("00:00");

                setupMediaProgress(mediaPlayer,progressSlider,currentTime,totalTime);

                nowPlaying.setText("Now Playing: " + newValue);

                mediaPlayer.setVolume(volumeSlider.getValue());

                mediaPlayer.play();

                status.setText("PLAYING");

            }

        });

        Button playBtn = new Button("▶");
        playBtn.getStyleClass().add("control-button");

        Button pauseBtn = new Button("⏸");
        pauseBtn.getStyleClass().add("control-button");

        Button stopBtn = new Button("⏹");
        stopBtn.getStyleClass().add("control-button");

        Button addBtn = new Button("+ ADD MEDIA");
        addBtn.getStyleClass().add("playlist-button");
        addBtn.getStyleClass().add("add-media-button");

        Button removeBtn = new Button("- REMOVE");
        removeBtn.getStyleClass().add("playlist-button");

        playBtn.setOnAction(e->{

            if (mediaPlayer != null){
                if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration()
                )){
                    mediaPlayer.seek(Duration.ZERO);
                }
                mediaPlayer.play();
                equalizerAnimation.play();
                status.setText("PLAYING");
            }
        });


        pauseBtn.setOnAction(e->{
            if (mediaPlayer != null) {

                mediaPlayer.pause();
                equalizerAnimation.pause();
                status.setText("PAUSED");
            }
        });

        stopBtn.setOnAction(e->{

            if (mediaPlayer != null) {

                mediaPlayer.stop();
                equalizerAnimation.stop();
                status.setText("STOPPED");
            }

            for (javafx.scene.Node node : equalizer.getChildren()) {
                Region bar = (Region) node;
                bar.setPrefHeight(20);
            }
            status.setText("STOPPED");
        });

        addBtn.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Choose Media File");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "Media Files", "*.mp4", "*.mp3", "*.wav", "*.m4a"
            ));

            File file = fileChooser.showOpenDialog(stage);

            if (file !=null){

                allMedia.add(file.getName());
                allMediaPaths.add(file.toURI().toString());

                playlist.add(file.getName());
                playlistPaths.add(file.toURI().toString());

                recentMedia.add(0, file.getName());
                recentMediaPaths.add(0, file.toURI().toString());
            }

        });

        removeBtn.setOnAction(e->{

            int selected = playlistView.getSelectionModel().getSelectedIndex();

            if(selected >= 0 ){
                playlist.remove(selected);
                playlistPaths.remove(selected);

            }
        });

        Button volumeBtn = new Button("🔊");
        volumeBtn.getStyleClass().add("volume-button");

        volumeBtn.setOnAction(e -> {

            if (!mediaPlayer.isMute()) {

                previousVolume = mediaPlayer.getVolume();

                mediaPlayer.setMute(true);
                volumeSlider.setValue(0);

                volumeBtn.setText("🔇");
                status.setText("MUTED");

            } else {

                mediaPlayer.setMute(false);
                mediaPlayer.setVolume(previousVolume);
                volumeSlider.setValue(previousVolume);

                volumeBtn.setText("🔊");
                status.setText("PLAYING");
            }
        });

        Button previousBtn = new Button("⏮");
        previousBtn.getStyleClass().add("control-button");

        Button nextBtn = new Button("⏭");
        nextBtn.getStyleClass().add("control-button");

        Label shortcuts = new Label("SPACE Play/Pause .  S Stop .  N Next . P Previous .  ↑↓ Volume . M Mute");
        shortcuts.getStyleClass().add("shortcuts");

        HBox shortcutArea = new HBox();
        shortcutArea.getChildren().add(shortcuts);
        shortcutArea.setAlignment(Pos.CENTER);
        HBox.setHgrow(shortcutArea, Priority.ALWAYS);

        Region shortcutSpacer = new Region();
        shortcutSpacer.setPrefWidth(220);

        HBox shortcutRow = new HBox();
        shortcutRow.getChildren().addAll(shortcutArea,shortcutSpacer);


        previousBtn.setOnAction(e->{

            int previousIndex = playlistView.getSelectionModel().getSelectedIndex()-1;

            if(previousIndex >=0){
                playlistView.getSelectionModel().select(previousIndex);
            } else{
                playlistView.getSelectionModel().select(playlist.size() - 1);
            }
        });

        nextBtn.setOnAction(e->{

            int nextIndex = playlistView.getSelectionModel().getSelectedIndex() +1;

            if (nextIndex < playlist.size()){
                playlistView.getSelectionModel().select(nextIndex);
            } else {
                playlistView.getSelectionModel().select(0);
            }
        });

        VBox logoBox = new VBox(2);
        logoBox.getChildren().addAll(logoTitle,tagline);

        HBox header = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(logoBox, spacer, status);
        header.setPadding(new Insets(18, 25, 18, 25));
        header.setAlignment(Pos.CENTER_LEFT);


        status.getStyleClass().add("status");
        header.getStyleClass().add("header");

        HBox volumeBox = new HBox(10);
        volumeBox.getChildren().addAll(volumeBtn, volumeSlider);

        HBox playbackControls = new HBox(15);
        playbackControls.getChildren().addAll(previousBtn,playBtn,pauseBtn,stopBtn, nextBtn);
        playbackControls.setAlignment(Pos.CENTER);

        HBox mainControls = new HBox(25);
        mainControls.getChildren().addAll(playbackControls,volumeBox);
        mainControls.setAlignment(Pos.CENTER);
        volumeBox.setAlignment(Pos.CENTER);


        VBox playbackSection = new VBox(6);
        playbackSection.getChildren().addAll(mainControls,shortcuts);
        playbackSection.setAlignment(Pos.CENTER);


        HBox controls = new HBox();
        controls.getChildren().add(playbackSection);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5));
        controls.getStyleClass().add("controls");

        HBox mediaButtons = new HBox(10);
        addBtn.setMaxWidth(Double.MAX_VALUE);
        removeBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(addBtn, Priority.ALWAYS);
        HBox.setHgrow(removeBtn, Priority.ALWAYS);
        mediaButtons.getChildren().addAll(addBtn,removeBtn);

        VBox playlistBox = new VBox(10);
        playlistBox.setPadding(new Insets(15));
        playlistBox.getStyleClass().add("playlist-box");
        playlistBox.getChildren().addAll(playlistTitle,libraryTabs,playlistView,mediaButtons);
        playlistBox.setPrefWidth(330);
        playlistBox.setMinWidth(330);
        playlistView.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(playlistView, Priority.ALWAYS);


        HBox progressArea = new HBox(10);
        progressArea.getChildren().addAll(currentTime,progressSlider,totalTime);
        progressArea.setAlignment(Pos.CENTER);
        HBox.setHgrow(progressArea, Priority.ALWAYS);
        progressSlider.setPrefWidth(500);
        Region progressSpacer = new Region();
        progressSpacer.setPrefWidth(220);


        HBox timeBox = new HBox();
        timeBox.getChildren().addAll(progressArea,progressSpacer);
        timeBox.setAlignment(Pos.CENTER);

        VBox bottomBox = new VBox(10);
        bottomBox.setPadding(new Insets(8, 15, 6, 15));
        bottomBox.getChildren().addAll(nowPlaying,timeBox,controls);
        bottomBox.getStyleClass().add("controls");

        VBox leftSide = new VBox(0);
        leftSide.getChildren().addAll(videoBox, bottomBox);
        VBox.setVgrow(videoBox, Priority.ALWAYS);
        videoBox.setMaxWidth(Double.MAX_VALUE);
        videoBox.setMaxHeight(Double.MAX_VALUE);


        VBox rightSide = new VBox(12);
        rightSide.getChildren().addAll(playlistBox,settingsBox);
        rightSide.setPrefWidth(330);
        rightSide.setMinWidth(330);
        rightSide.setMaxWidth(330);

        VBox.setVgrow(playlistBox, Priority.ALWAYS);
        playlistBox.setMaxHeight(Double.MAX_VALUE);
        settingsBox.setPrefHeight(240);
        settingsBox.setMinHeight(240);


        HBox mainContent = new HBox(12);
        mainContent.getChildren().addAll(leftSide,rightSide);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        leftSide.setMaxWidth(Double.MAX_VALUE);


        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(mainContent);
        root.getStyleClass().add("root-pane");


        Scene scene = new Scene(root,1100,750);

        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {

            switch (event.getCode()) {

                case SPACE:
                    if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                        mediaPlayer.pause();
                        status.setText("PAUSED");
                    } else {
                        mediaPlayer.play();
                        status.setText("PLAYING");
                    }
                    break;

                case S:
                    mediaPlayer.stop();
                    status.setText("STOPPED");
                    break;

                case N:
                    if (playlist.isEmpty()){
                        break;
                    }
                    int nextIndex = playlistView.getSelectionModel().getSelectedIndex()+1;

                    if (nextIndex < playlist.size()){
                        playlistView.getSelectionModel().select(nextIndex);
                    }
                    else {
                        playlistView.getSelectionModel().select(0);
                    }
                    break;

                case P:

                    if (playlist.isEmpty()){
                        break;
                    }
                    int previousIndex = playlistView.getSelectionModel().getSelectedIndex() -1;

                    if (previousIndex >=0){
                        playlistView.getSelectionModel().select(previousIndex);
                    }
                    else {
                        playlistView.getSelectionModel().select(playlist.size() - 1);
                    }
                    break;

                case UP:
                    double volumeUp = mediaPlayer.getVolume() + 0.1;

                    if (volumeUp > 1.0) {
                        volumeUp = 1.0;
                    }

                    mediaPlayer.setVolume(volumeUp);
                    volumeSlider.setValue(volumeUp);
                    event.consume();
                    break;

                case DOWN:
                    double volumeDown = mediaPlayer.getVolume() - 0.1;

                    if (volumeDown < 0.0) {
                        volumeDown = 0.0;
                    }

                    mediaPlayer.setVolume(volumeDown);
                    volumeSlider.setValue(volumeDown);

                    System.out.println("Volume: " + mediaPlayer.getVolume());
                    event.consume();
                    break;

                case M:

                    if (!mediaPlayer.isMute()){
                     previousVolume = mediaPlayer.getVolume();

                     mediaPlayer.setMute(true);

                     volumeSlider.setValue(0);

                     status.setText("MUTED");
                    } else {
                        mediaPlayer.setMute(false);

                        mediaPlayer.setVolume(previousVolume);

                        volumeSlider.setValue(previousVolume);

                        status.setText("PLAYING");
                    }
                    break;
            }
        });


        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("MOLLY MEDIA");
        stage.setScene(scene);
        stage.show();
    }
    public void setupMediaProgress(
            MediaPlayer player,
            Slider progressSlider,
            Label currentTime,
            Label totalTime){

        player.setOnReady(() ->{
            Duration duration = player.getTotalDuration();

            progressSlider.setMax(duration.toSeconds());

            totalTime.setText(formatTime(duration));
        });

        player.currentTimeProperty().addListener((obs,oldTime,newTime)->{

            if (!progressSlider.isValueChanging()){
                progressSlider.setValue(newTime.toSeconds());
            }
            currentTime.setText(formatTime(newTime));
        });

        progressSlider.setOnMouseReleased(e->{
            player.seek(Duration.seconds(progressSlider.getValue()));
        });
    }


    public static void main( String[] args){
        launch();
    }
}

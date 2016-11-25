package ru.velkomfood.mrp.frontend.report;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.velkomfood.mrp.frontend.report.config.Configurator;
import ru.velkomfood.mrp.frontend.report.view.MainView;

@ComponentScan
public class Launcher extends Application {

	private final String TITLE = "Планирование потребности в материалах";

	AnnotationConfigApplicationContext context;
	MainView mainView;

	@Override
	public void init() {
		context = new AnnotationConfigApplicationContext(Configurator.class);
		mainView = (MainView) context.getBean("StartForm");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		mainView.setPrimaryStage(primaryStage);
		mainView.setMainTitle(TITLE);
		mainView.createSceneAndPane();
		mainView.showMainWindow();

	}

	@Override
	public void stop() {

	}

	public static void main(String[] args) {
		launch(args);
	}
}
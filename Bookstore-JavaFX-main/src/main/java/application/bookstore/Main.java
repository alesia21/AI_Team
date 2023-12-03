package application.bookstore;

import application.bookstore.controllers.ControllerCommon;
import application.bookstore.controllers.LoginController;
import application.bookstore.models.*;
import application.bookstore.views.LoginView;
import application.bookstore.views.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Main extends Application {

    public static void main(String[] args) {
        FileHandler fh;
        try {
            fh = new FileHandler("data/bookstoreLOG.log");
            ControllerCommon.LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            ControllerCommon.LOGGER.info("Starting APP...");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        loadData();
        createAdminAndData(); // create user:admin password:admin and sample data if it is the first time running (data/users.txt does not exist)
        launch(args);
    }

    public static void createAdminAndData(){
        File f = new File(User.FILE_PATH);
        if (!f.exists()){
            ControllerCommon.LOGGER.info("Creating startup Data...");
            new File(BaseModel.FOLDER_PATH).mkdirs();
            User u = new User("admin", "admin", Role.ADMIN);
            ControllerCommon.LOGGER.log(Level.INFO, u.saveInFile());
            /*u = new User("manager", "manager", Role.MANAGER);
            ControllerCommon.LOGGER.log(Level.INFO, u.saveInFile());
            u = new User("librarian", "librarian", Role.LIBRARIAN);
            ControllerCommon.LOGGER.log(Level.INFO, u.saveInFile());
            Author a = new Author("Eric", "Thomas");
            a.saveInFile();
            Book b = new Book("1234567891230", "You owe you", 100, 5, 5.2f, a);
            b.saveInFile();
            b = new Book("1234567891231", "Atomic Habits", 8, 7, 7.5f, a);
            b.saveInFile();
            b = new Book("1234567891232", "Autobiography", 10, 6, 6.4f, a);
            b.saveInFile();
            b = new Book("1234567891233", "Think and Grow Rich", 14, 5, 5.2f, a);
            b.saveInFile();
            b = new Book("1234567891234", "48 Laws of Power", 12, 5, 5.2f, a);
            b.saveInFile();
            a=new Author("Napoleon", "Hill");
            a.saveInFile();
            b = new Book("1234567891235", "The Compound Effect", 15, 5, 5.2f, a);
            b.saveInFile();
            b = new Book("1234567891236", "The Richest Man in Babylon", 10, 6, 6.3f, a);
            b.saveInFile();*/
        }
    }

    private static void loadData(){
        ControllerCommon.LOGGER.info("Loading data Files...");
        User.getUsers();
        Author.getAuthors();
        Book.getBooks();
        Order.getOrders();
    }

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        new LoginController(loginView, stage);
        Scene scene = new Scene(loginView.getView(), MainView.width, MainView.height);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
    }

}

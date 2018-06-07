import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.TimerTask


// fxml と関連付けてよびだしてます
class MyApplication : Application() {

    override fun start(primaryStage: Stage) {

        primaryStage.title = "STOPWATCH"
        primaryStage.scene = Scene(FXMLLoader.load<Parent>(this.javaClass.getResource("fxml/stopwatch.fxml")))
        primaryStage.show()

    }
}

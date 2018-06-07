import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import java.awt.event.ActionEvent
import java.net.URL
import java.util.*
import java.util.TimerTask

// fxmlの制御はここで行う
class Controller : Initializable {
    var timeValue = 0                               // 秒カウンター
    private val MyTimeerTrigger : TimerTest2 = TimerTest2(this)

    //lateinit(遅延初期化オプション)を付けることで、nullチェックを避ける
    @FXML lateinit var timeText: Label
    @FXML lateinit var cmdStart: Button
    @FXML lateinit var cmdStop: Button
    @FXML lateinit var cmdReset: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        // STARTボタン
        this.cmdStart.onAction = EventHandler {
            MyTimeerTrigger.timerStart()
        }
        this.cmdStop.onAction =  EventHandler {
            MyTimeerTrigger.timerStop()
        }
        this.cmdReset.onAction =  EventHandler {
            MyTimeerTrigger.timerStop()
            timeValue = 0
            timeToText(timeValue)?.let { timeText.setText(it) }
        }
    }

    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null                                    // 時刻が0未満の場合 null
        } else if (time == 0) {
            "00:00:00"                              // ０なら
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)  // 表示に整形
        }
    }

    //
    fun timUpProcess() {
        timeValue++
        timeToText(timeValue)?.let {        // timeToText()で表示データを作り
            // タイマーは別スレッドなので、Platform.runLaterを使用しないとエラーになる
            Platform.runLater { timeText.setText(it) }
        }
    }

}

// 1秒毎に timUpProcess()を呼び出すクラス
internal class TimerTest2(val mc: Controller) {
    var timer : Timer? = null
    init {
        // timer.scheduleAtFixedRate(new TestTask(this), 1000,1000);
    }

    fun actionPerformed(e: ActionEvent) {}

    fun timerStart() {
        if (null == timer) {
            timer = Timer(true)
            timer?.schedule(TestTask(mc), 1000, 1000)
        } else {
            println(timer.toString())
        }
    }

    fun timerStop() {
        if (null != timer) {
            timer?.cancel()
            timer?.purge()
            timer =null
        }
    }


    // 時間経過時の処理
    internal class TestTask(var tt: Controller) : TimerTask() {
        override fun run() {
            tt.timUpProcess()
        }
    }
}

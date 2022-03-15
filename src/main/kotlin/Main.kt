import function.DealMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.BotFactory

/**
 *@author ysh
 *@time 2022/1/19  23:02
 *@signature 我将追寻并获取我想要的答案
 */
suspend fun main() {


    
    val bot = BotFactory.newBot(ControlCenter.botQQ, "不是真的密码别想了") {
        fileBasedDeviceInfo("resources/device.json")
    }


    ControlCenter.bot = bot

    bot.login()
    PluginMain.onEnable()


    val scope = CoroutineScope(Job())
    val job = scope.launch {


        launch {
            while (true) {
                if (isBelong("12:00", "23:59") && !ControlCenter.isLiuFinished) {
//                if (isBelong("19:46", "20:30") && !ControlCenter.isOneFinished) {
                    println("开始检测")
                    //开始检测
                    DealMessage.checkGroupCard(ControlCenter.studentsLiu, ControlCenter.noticeGroup[0])

                    delay(1000 * 60 * 60 * 1)
//                    delay(1000 * 60  * 5)
//                    delay(1000 * 5)
//                        delay(1000 )
                } else {
                    delay(1000 * 60 * 10)
//                    delay(1000 * 10)
                }
            }
        }
        launch {
            while (true) {
                if (isBelong("12:00", "23:59") && !ControlCenter.isDingFinished) {
//                if (isBelong("19:46", "20:30") && !ControlCenter.isOneFinished) {
                    println("开始检测")
                    //开始检测
                    DealMessage.checkGroupCard(ControlCenter.studentsDing, ControlCenter.noticeGroup[1])
                    delay(1000 * 60 * 60 * 1)
//                    delay(1000 * 60  * 5)
//                    delay(1000 * 5)
//                        delay(1000 )
                } else {
                    delay(1000 * 60 * 10)
//                    delay(1000 * 10)
                }
            }
        }

        launch {
            while (true) {
                if (isBelong("00:00", "01:00")) {
                    ControlCenter.isLiuFinished = false
                    ControlCenter.isDingFinished = false
                    for (stu in ControlCenter.studentsLiu) {
                        stu.isTodayChecked = false
                    }
                    for (stu in ControlCenter.studentsDing) {
                        stu.isTodayChecked = false
                    }
                    delay(1000 * 60 * 45)
                } else {
                    delay(1000 * 60 * 30)
                }
            }
        }


        //重置学生信息

    }

    job.join()
}


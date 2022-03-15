import net.mamoe.mirai.Bot
import bean.StudentBean

/**
 * @Date : 2022/1/19   19:38
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 *  1.build.gradle中加入插件
 *  2.gradle build在build包下的distributions的MiraiY.zip再解压
 *  3.把resources放再bin包下
 *  4.传服务器chmod +x MiraiY
 *  5.nohup ./MiraiY &
 **/
object ControlCenter {

    //控制bot
    lateinit var bot: Bot

    //学生信息
    lateinit var studentsLiu: List<StudentBean>
    lateinit var studentsDing: List<StudentBean>


    //主人QQ，控制是否推送消息
    val masterQQ = listOf<Long>(1356542512L)


    //    val botQQ = 3327552169L
    val botQQ = 1140592853L

    //    val botQQ = 3449785723L
    var isSendMessage = true
    var atLastTime = 0L
    var atTime = 0

    //管理的群，哪些群需要推送消息
//    val noticeGroup = listOf<Long>(1057164951)
    val noticeGroup = listOf<Long>(737227126, 547240313, 941266051)
//    val noticeGroup = listOf<Long>(939765719,941266051,941266051)

    //是否全部人都打卡
    var isLiuFinished = true
    var isDingFinished = true


}
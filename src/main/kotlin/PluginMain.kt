import bean.CardStudentBean
import function.DealMessage
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.action.Nudge.Companion.sendNudge
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import function.ControlMessage
import util.ExcelReader
import java.io.File

object PluginMain {

    fun onEnable(){
            //配置文件目录 "${dataFolder.absolutePath}/"
            val classLiuFilePath = "resources/classExcel/stu_liu.xlsx"
            val classDingFilePath = "resources/classExcel/stu_ding.xlsx"
            val classTaoFilePath = "resources/classExcel/stu_tao.xlsx"
            ControlCenter.studentsLiu = ExcelReader.readStudentExcel(File(classLiuFilePath))
            ControlCenter.studentsDing = ExcelReader.readStudentExcel(File(classDingFilePath))
            ControlCenter.studentTao = ExcelReader.readStudentExcel(File(classTaoFilePath))


            //同意添加好友
            //todo 加判断是否是在群中添加的好友
            globalEventChannel().subscribeAlways<NewFriendRequestEvent> {
                println("${this.fromId}申请加好友")
                this.accept()
            }

            //控制是否推送消息
            globalEventChannel().subscribeAlways<FriendMessageEvent> { ControlMessage.controlMessage(this)  }

            globalEventChannel().subscribeAlways<NudgeEvent> {
                if(this.target == bot)
                    this.subject.sendNudge(this.from.nudge())
            }

            globalEventChannel().subscribeAlways<GroupMessageEvent> {
                /**
                 * 判断群员是否在叫小黄
                 */
                DealMessage.groupMessage(message, group, sender)

                //群消息
                //复读示例
//            if (message.contentToString().startsWith("123")) {
//                sender.group.sendMessage(message.contentToString().replace("123", "11111"))
//            }
//
//            if (message.contentToString() == "hi") {
//                //群内发送
//                sender.group.sendMessage("hi")
//                //向发送者私聊发送消息
//                sender.sendMessage("hi")
//                //不继续处理
//                return@subscribeAlways
//            }
//            //分类示例
//            message.forEach {
//                println("-----------------")
//                println(it.contentToString())
//                println("-----------------")
//                //循环每个元素在消息里
//                if (it is At) {
//                    if (it.target == 1356542512L) {
//                        group.sendMessage("不要打扰叶哥哥，有什么事冲我来")
//                    } else if (it.target == 1140592853L) {
//                        group.sendMessage(At(sender.id) + "做咩啊")
//                    }
//                }
//                if (it is Image) {
////                    //如果消息这一部分是图片
////                    val url = it.queryUrl()
////                    group.sendMessage("图片,${it.imageId}")
//////                    group.sendMessage("图片，下载地址$url")
////                    val file =
////                        File("E:\\An\\mirai_plugin_example\\src\\main\\resources\\image\\v2-00f4ac70104eb07b8b100eba6d2e328a_b.jpg")
////                    val image = group.uploadImage(file.toExternalResource())
////                    group.sendMessage(image)
//                }
//                if (it is PlainText) {
//                    //如果消息这一部分是纯文本
////                    group.sendMessage("纯文本，内容:${it.content}")
//                }
//            }
            }

//            /**
//             * 接受好友信息
//             */
//            globalEventChannel().subscribeAlways<FriendMessageEvent> {
//                val file =
//                    File("E:\\An\\mirai_plugin_example\\src\\main\\resources\\image\\v2-00f4ac70104eb07b8b100eba6d2e328a_b.jpg")
//                val image = sender.uploadImage(file.toExternalResource())
//                sender.sendMessage(image)
//                sender.sendMessage("hi")
//            }

            /**
             * 好友申请
             */
            globalEventChannel().subscribeAlways<NewFriendRequestEvent> {
                //自动同意好友申请
                accept()
            }

            /**
             * 加群申请
             */
            globalEventChannel().subscribeAlways<BotInvitedJoinGroupRequestEvent> {
                //自动同意加群申请
                accept()
            }

            /**
             * 陌生人消息
             */
            globalEventChannel().subscribeAlways<StrangerMessageEvent> {
                sender.sendMessage("我爹说了不能和陌生人讲话")
            }

            /**
             * 权限被改变
             */
            globalEventChannel().subscribeAlways<BotGroupPermissionChangeEvent> {
                /**
                 * 由成员变为管理员
                 */
                if (origin == MemberPermission.MEMBER && new == MemberPermission.ADMINISTRATOR) {
                    val file =
//                        File("E:\\An\\mirai_plugin_example\\src\\main\\resources\\image\\yellow_chick_thanksboss.jpg")
                        File("resources/image/yellow_chick_thanksboss.jpg")
                    val image = group.uploadImage(file.toExternalResource())
                    group.sendMessage(
                        At(group.owner).followedBy(PlainText("谢谢老板"))
                                + image
                    )
                    /**
                     * 变为群主
                     */
                } else if (origin == MemberPermission.MEMBER || origin == MemberPermission.MEMBER && new == MemberPermission.OWNER) {
                    group.sendMessage(PlainText("群主是傻逼"))
                    /**
                     * 被下掉管理员
                     */
                } else if (origin == MemberPermission.ADMINISTRATOR && new == MemberPermission.MEMBER) {
                    val file =
//                        File("E:\\An\\mirai_plugin_example\\src\\main\\resources\\image\\yellow_chick_manager_to_member.jpg")
                        File("resources/image/yellow_chick_manager_to_member.jpg")
                    val image = group.uploadImage(file.toExternalResource())
                    group.sendMessage(PlainText("阿黄做错了什么"))
                    group.sendMessage(image)
                }
            }
            /**
             * 加入新群
             */
            globalEventChannel().subscribeAlways<BotJoinGroupEvent> {
//            group.sendMessage(PlainText("需要我时请@我或叫我的名字，出什么问题别找我，找我爹") + At(1356542512L))
                group.sendMessage(PlainText("大家好，我是小黄鸡bot，为不打扰大家日常聊天，叫我时请在前面@我或输入我名字的关键字"))
            }
    }

    private fun globalEventChannel(): GlobalEventChannel {
        return GlobalEventChannel
    }

}
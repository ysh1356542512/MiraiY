package function

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import ControlCenter
import ControlCenter.noticeGroup
import bean.StudentBean
import checkCheckInToday
import java.io.File
import java.lang.Exception

/**
 * @Date : 2022/1/19   15:41
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
object DealMessage {

    suspend fun groupMessage(message: MessageChain, group: Group, sender: Member) {
        println("time is ${ControlCenter.atTime} lasttime is ${ControlCenter.atLastTime}")
        if (message.contains(At(ControlCenter.botQQ)) ||
            message.contentToString().contains("小黄") ||
            message.contentToString().contains("鸡儿") ||
            message.contentToString().contains("阿黄") ||
            message.contentToString().contains("黄黄") ||
            message.contentToString().contains("小鸡")
        ) {
            if (message.contains(At(ControlCenter.botQQ))) {
                if (System.currentTimeMillis() - ControlCenter.atLastTime >= 60000L) {
                    ControlCenter.atTime = 0
                }

                ControlCenter.atTime++
                if (ControlCenter.atTime <= 3) {
                    when ((0..2).random()) {
                        0 -> {
                            group.sendMessage("爷爷在此")
                            val file =
                                File("resources/image/yellow_chick_intro_7.gif")
                            val intro1 = group.uploadImage(file.toExternalResource())
                            group.sendMessage(intro1)
                        }
                        1 -> {
                            group.sendMessage("呕吼")
                            val file =
                                File("resources/image/yellow_chick_intro_8.gif")
                            val intro1 = group.uploadImage(file.toExternalResource())
                            group.sendMessage(intro1)
                        }
                        else -> {
                            group.sendMessage("发生甚么事了")
                            val file =
                                File("resources/image/yellow_chick_intro_9.gif")
                            val intro1 = group.uploadImage(file.toExternalResource())
                            group.sendMessage(intro1)
                        }
                    }
                } else {
                    when ((0..1).random()) {
                        0 -> {
                            group.sendMessage("太激烈了，让阿黄休息一分钟吧")
                            val file =
                                File("resources/image/yellow_chick_intro_1.jpg")
                            val intro1 = group.uploadImage(file.toExternalResource())
                            group.sendMessage(intro1)
                        }
                        1 -> {
                            group.sendMessage("别@了，要受不了了")
                            val file =
                                File("resources/image/yellow_chick_manager_to_member.jpg")
                            val intro2 = group.uploadImage(file.toExternalResource())
                            group.sendMessage(intro2)
                        }
                    }
                }
                ControlCenter.atLastTime = System.currentTimeMillis()
            }

            message.forEach {
                if (it is PlainText) {
                    it.contentToString().let {
                        when {
                            /**
                             * 介绍自己
                             */
                            it.contains("你") && it.contains("谁") ||
                                    it.contains("介绍") || it.contains("小黄是谁") -> {
                                group.sendMessage("我是小黄鸡儿，一个苦逼的打工小鸡儿")
                                when ((0..2).random()) {
                                    0 -> {
                                        val file =
                                            File("resources/image/yellow_chick_intro_1.jpg")
                                        val intro1 = group.uploadImage(file.toExternalResource())
                                        group.sendMessage(intro1)
                                    }
                                    1 -> {
                                        val file =
                                            File("resources/image/yellow_chick_intro_2.jpg")
                                        val intro1 = group.uploadImage(file.toExternalResource())
                                        group.sendMessage(intro1)
                                    }
                                    else -> {
                                        val file =
                                            File("resources/image/yellow_chick_intro_3.jpg")
                                        val intro1 = group.uploadImage(file.toExternalResource())
                                        group.sendMessage(intro1)
                                    }
                                }
                            }
                            /**
                             * 呼叫小黄
                             */
                            it.contains("在吗") || it.contains("呼叫") || it.contains("召唤")
                                    || it.contains("在不在") || it == "小黄" -> {
                                when ((0..2).random()) {
                                    0 -> {
                                        group.sendMessage("爷爷在此")
                                    }
                                    1 -> {
                                        group.sendMessage("小黄在")
                                    }
                                    else -> {
                                        group.sendMessage("发生甚么事了")
                                    }
                                }
                            }
                            /**
                             * 介绍自己的功能
                             */
                            it.contains("会做") || it.contains("会干") || it.contains("功能") || it.contains("能做")
                                    || it.contains("能干") -> {
                                group.sendMessage("我只是一个被资本压榨的打杂的苦力")
                            }
                            /**
                             * 辱骂小黄
                             */
                            it.contains("傻") || it.contains("智障") || it.contains("畜生") || it.contains("蠢") ||
                                    it.contains("笨") || it.contains("儿子") || it.contains("爸爸") -> {
                                group.sendMessage("你再骂?")
                            }
                            /**
                             * 表演才艺
                             */
                            it.contains("表演才艺") || it.contains("跳舞") || it.contains("跳个舞") -> {

                            }
                            /**
                             * 小黄可爱
                             */
                            it.contains("可爱") || it.contains("好玩") || it.contains("有意思") -> {

                            }
                            else -> {
//                                group.sendMessage("叶圣豪这个懒鬼还没有给我加这个功能，火速给他发加急")
                            }
                        }
                    }
                }
            }
        }
    }

    suspend fun checkGroupCard(students:List<StudentBean>,groupID:Long) {
        if (!ControlCenter.isSendMessage) {
            println("消息推送处于关闭状态")
            return
        }
        val messageBuilder = MessageChainBuilder()
        var temp = "111"
        var classOne = false
        messageBuilder.add(PlainText("请以下人员及时打卡:"))
        for (stu in students) {
            if (!stu.isTodayChecked) {
                try {
                    val result = checkCheckInToday(stu.studentNum)
                    println("${stu.name} 今日是否打卡: $result")
                    if (result) {
                        stu.isTodayChecked = true
                    } else {
                        if (temp != stu.classNum) {
                            temp = stu.classNum

                            messageBuilder.add(PlainText("\n\n${stu.className} ${stu.classNum}:\n"))
                        }
                        when (groupID) {
                            ControlCenter.noticeGroup[1] ->{
                                messageBuilder.add(At(stu.qq.toLong())+" ")
//                                messageBuilder.add(PlainText("@${stu.name} "))
                            }
                            ControlCenter.noticeGroup[0] ->{
                                messageBuilder.add(PlainText("@${stu.name} "))
                            }
                        }
                        if (ControlCenter.bot.getGroup(groupID)?.get(stu.qq.toLong()) != null) {
//                            ControlCenter.bot.getGroup(groupID)?.getOrFail(stu.qq.toLong())?.sendMessage("请及时完成打卡")
                        }else{
                            println("${stu.name}不在群$groupID 中")
//                            ControlCenter.bot.getFriend(ControlCenter.masterQQ[0])?.sendMessage("${stu.name}不在群$groupID 中")
                        }
//                        messageBuilder.add(At(stu.qqNum.toLong()))
                        classOne = true
                    }
                } catch (e: Exception) {
                    ControlCenter.bot.getFriend(1356542512L)?.sendMessage(e.message.toString())
                    return
                }
            }
        }
        if (classOne) {
            ControlCenter.bot.getGroup(groupID)?.sendMessage(messageBuilder.toMessageChain())
        }else{
            ControlCenter.bot.getGroup(groupID)?.sendMessage("所有人已打卡")
            if (groupID == ControlCenter.noticeGroup[0]) {
                ControlCenter.isLiuFinished = true
            }else{
                ControlCenter.isDingFinished = true
            }

        }


    }
}
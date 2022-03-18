package function

import net.mamoe.mirai.event.events.MessageEvent
import ControlCenter
import checkCard
import checkCheckInToday
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.toMessageChain


//控制消息
object ControlMessage {

    /**
     * 控制是否推送消息
     */
    suspend fun controlMessage(event: MessageEvent) {
        val msgContent = event.message.contentToString()
        val sendId = event.sender.id
        if (ControlCenter.masterQQ.contains(sendId)) {
            when (msgContent) {
                "help", "查询", "功能", "查询功能" -> {
                    event.subject.sendMessage(
                        "输入" +
                                "\n开启推送 -> 开启消息通知" +
                                "\n关闭推送 -> 关闭消息通知" +
                                "\n发送推送test -> 发送推送给通信一班" +
                                "\ntest -> 测试自己数据" +
                                "\n推送丁 -> 发送推送到丁"+
                                "\n推送刘 -> 发送推送到刘"+
                                "\n推送涛 -> 发送推送到涛"+
                                "\n状态 -> 查看完成状态和推送状态" +
                                "\n打卡完成丁 -> 设置丁老师部队已完成 " +
                                "\n打卡未完成丁 -> 设置丁老师部队未完成" +
                                "\n打卡完成刘 ->设置刘老师部队已完成" +
                                "\n打卡未完成刘 ->设置刘老师部队未完成"+
                                "\n打卡完成涛 ->设置涛哥部队已完成" +
                                "\n打卡未完成涛 ->设置涛哥部队未完成"+
                                "\nmode0 -> 切换检测mode为0"+
                                "\nmode1 -> 切换检测mode为1"+
                                "\ncard名单 -> 显示自动打卡名单"
                    )
                }
                "开启推送" -> {
                    ControlCenter.isSendMessage = true
                    event.subject.sendMessage("推送已开启")
                }
                "关闭推送" -> {
                    ControlCenter.isSendMessage = false
                    event.subject.sendMessage("推送已关闭")
                }
                "发送推送test" -> {
                    DealMessage.checkGroupCard(ControlCenter.studentsLiu, ControlCenter.noticeGroup[2])
                    event.subject.sendMessage("推送已发送")
                }
                "test" -> {
                    val message = MessageChainBuilder()
                    message.add(PlainText("打卡状态为"))
                    for (stu in ControlCenter.studentsCard) {
                        val result = checkCard(stu.xh)
                        message.add(PlainText("\n${stu.name}打卡状态为：$result"))
                    }
                    event.subject.sendMessage(message.toMessageChain())
                }
                "推送刘"->{
                    DealMessage.checkGroupCard(ControlCenter.studentsLiu, ControlCenter.noticeGroup[0])
                }
                "推送丁"->{
                    DealMessage.checkGroupCard(ControlCenter.studentsDing, ControlCenter.noticeGroup[1])
                }
                "推送涛"->{
                    DealMessage.checkGroupCard(ControlCenter.studentTao, ControlCenter.noticeGroup[2])
                }
                "状态" -> {
                    event.subject.sendMessage(
                        "推送状态 -> ${ControlCenter.isSendMessage}" +
                                "\n丁打卡状态 -> ${ControlCenter.isDingFinished}"+
                                "\n刘打卡状态 -> ${ControlCenter.isLiuFinished}"+
                                "\n涛打卡状态 -> ${ControlCenter.isTaoFinished}"+
                                "\n检测模式为 -> ${ControlCenter.detectMode}"
                    )
                }
                "打卡完成丁" -> {
                    ControlCenter.isDingFinished = true
                    event.subject.sendMessage("打卡状态切换为${ControlCenter.isDingFinished}")
                }
                "打卡未完成丁" -> {
                    ControlCenter.isDingFinished = false
                    event.subject.sendMessage("打卡状态切换为${ControlCenter.isDingFinished}")
                }
                "打卡完成刘" -> {
                    ControlCenter.isLiuFinished = true
                    event.subject.sendMessage("打卡状态切换为${ControlCenter.isLiuFinished}")
                }
                "打卡未完成刘" -> {
                    ControlCenter.isLiuFinished = false
                    event.subject.sendMessage("打卡状态切换为${ControlCenter.isLiuFinished}")
                }
                "打卡完成涛" -> {
                    ControlCenter.isTaoFinished = true
                    event.subject.sendMessage("打卡状态切换为${ControlCenter.isTaoFinished}")
                }
                "打卡未完成涛" -> {
                    ControlCenter.isTaoFinished = false
                    event.subject.sendMessage("打卡状态切换为${ControlCenter.isTaoFinished}")
                }
                "mode0" ->{
                    ControlCenter.detectMode = 0
                    event.subject.sendMessage("mode已切换为${ControlCenter.detectMode}")
                }
                "mode1" ->{
                    ControlCenter.detectMode = 1
                    event.subject.sendMessage("mode已切换为${ControlCenter.detectMode}")
                }
                "card名单"->{
                    for(index in 0 until ControlCenter.studentsCard.size)
                    event.subject.sendMessage("姓名：${ControlCenter.studentsCard[index].name}" +
                            "\n学号：${ControlCenter.studentsCard[index].xh}" +
                            "\n性别：${ControlCenter.studentsCard[index].xb}" +
                            "\n地址：${ControlCenter.studentsCard[index].xxdz}")
                }
            }
        } else {
            event.subject.sendMessage("小黄鸡暂不对外人开放")
        }
    }
}

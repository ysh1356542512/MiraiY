package org.example.mirai.plugin.function

import net.mamoe.mirai.event.events.MessageEvent
import ControlCenter
import checkCheckInToday
import function.DealMessage


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
                                "\n状态 -> 查看完成状态和推送状态" +
                                "\n打卡完成丁 -> 设置丁老师部队已完成 " +
                                "\n打卡未完成丁 -> 设置丁老师部队未完成" +
                                "\n打卡完成刘 ->设置刘老师部队已完成" +
                                "\n打卡未完成刘 ->设置刘老师部队未完成"
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
                    val result = checkCheckInToday("2020210267")
                    event.subject.sendMessage("叶圣豪打卡状态为：$result")
                }
                "推送刘"->{
                    DealMessage.checkGroupCard(ControlCenter.studentsLiu, ControlCenter.noticeGroup[0])
                }
                "推送丁"->{
                    DealMessage.checkGroupCard(ControlCenter.studentsDing, ControlCenter.noticeGroup[1])
                }
                "状态" -> {
                    event.subject.sendMessage(
                        "推送状态 -> ${ControlCenter.isSendMessage}" +
                                "\n丁打卡状态 -> ${ControlCenter.isDingFinished}"+
                                "\n刘打卡状态 -> ${ControlCenter.isLiuFinished}"
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
            }
        } else {
            event.subject.sendMessage("小黄鸡暂不对外人开放")
        }
    }
}
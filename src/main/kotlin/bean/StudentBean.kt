package bean

/**
 * @Date : 2022/1/18   22:29
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
data class StudentBean(
    val name: String,  // 学生名字 用来@
    val studentNum: String, //  学生学号
    val className: String,  //  学生班级
    val classNum: String,   //  学生班级编号
    val qq:String   //  学生QQ 用来@
) {
    var isTodayChecked = false // 今日是否打卡，若今日已打则当天下次检测直接跳过
}
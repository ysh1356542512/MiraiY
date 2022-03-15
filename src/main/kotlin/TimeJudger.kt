import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Date : 2022/1/19   19:29
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
fun isBelong(start: String, end: String): Boolean {
    val df = SimpleDateFormat("HH:mm") //设置日期格式
    var now: Date? = null
    var beginTime: Date? = null
    var endTime: Date? = null
    try {
        now = df.parse(df.format(Date()))
        beginTime = df.parse(start)
        endTime = df.parse(end)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return belongCalendar(now, beginTime, endTime)
}

/**
 * 判断时间是否在时间段内
 * @param nowTime
 * @param beginTime
 * @param endTime
 * @return
 */
fun belongCalendar(nowTime: Date?, beginTime: Date?, endTime: Date?): Boolean {
    val date = Calendar.getInstance()
    date.time = nowTime
    val begin = Calendar.getInstance()
    begin.time = beginTime
    val end = Calendar.getInstance()
    end.time = endTime
    return if (date.after(begin) && date.before(end)) {
        true
    } else {
        false
    }
}
package util

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import bean.StudentBean
import java.io.File

/**
 * @Date : 2022/1/18   22:38
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
object ExcelReader {
    fun readStudentExcel(file: File): List<StudentBean> {
        val stuList = mutableListOf<StudentBean>()
        // 工作表
        val workbook: Workbook = WorkbookFactory.create(file)

        // 表个数。
        val numberOfSheets = workbook.numberOfSheets

        // 遍历表。
        for (i in 0 until numberOfSheets) {
            val sheet = workbook.getSheetAt(i)

            // 行数。
            val rowNumbers = sheet.lastRowNum + 1

            // Excel第一行。
            val temp = sheet.getRow(0) ?: continue

            val builder = StringBuilder()
            // 读数据。
            for (row in 1 until rowNumbers) {
                builder.clear()
                val r = sheet.getRow(row)
                val name = r.getCell(0).toString()
                builder.append(r.getCell(1).toString())
//                if (!builder.contains("2020")) continue
                if (builder.contains("E")) {
                    builder.deleteCharAt(1)
                    builder.delete(builder.length - 2, builder.length)
                }
                for (i in 1..(10 - builder.length)) {
                    builder.append("0")
                }

                if (builder.length != 10) {
                    println("!!!!!!!!!数据读入错误")
                }
                val stuNum = builder.toString()
                builder.clear()
                val className = r.getCell(2).toString()
                val classNum = r.getCell(3).toString()
                builder.append(r.getCell(4).toString())
//                if (!builder.contains("2020")) continue
                println("$name  $stuNum  ${builder.length} ${builder.toString()}")
                if (builder.contains("E9")) {
                    builder.deleteCharAt(1)
                    builder.delete(builder.length - 2, builder.length)
                    for (i in 1..(10 - builder.length)) {
                        builder.append("0")
                    }
//                    val qq = builder.toString().toLong()/10
                }
                if (builder.contains("E8")) {
                    builder.deleteCharAt(1)
                    builder.delete(builder.length - 2, builder.length)
//                    val qq = builder.toString().toLong()/10
                }
                val qq = builder.toString()
                builder.clear()

                println("$name  $stuNum  ${builder.length} $className $classNum $qq")
                stuList.add(StudentBean(name, stuNum, className, classNum, qq))
            }
        }
        return stuList
    }
}
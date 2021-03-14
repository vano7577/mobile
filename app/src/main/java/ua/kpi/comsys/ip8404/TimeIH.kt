package ua.kpi.comsys.ip8404

import java.lang.Math.ceil
import java.time.LocalDateTime
import java.util.*

class TimeIH {
    constructor(c_hours : Int =0, c_minutes:Int = 0,  c_seconds : Int =0){
        if(!((c_hours in 0..23)&&(c_minutes in 0..59)&&(c_seconds in 0..59))) {
        error("не корректное время")}
        hours=c_hours
        minutes=c_minutes
        seconds=c_seconds

    }
    var hours : Int
    var minutes : Int
    var seconds : Int
    private fun get_12_format(hours : Int): String{
        val ZZ : String
        if (hours in 0..11) ZZ= "AM"
        else {ZZ = "PM"

        }
        return ZZ
    }
    fun method_a() : String {
        var a : Int = 0
        var time_of_day:String = get_12_format(hours)
        if(time_of_day == "PM") a = hours - 12 else a = hours
        var ret_val : String = "$a:$minutes:$seconds $time_of_day"
        println(ret_val)
        return ret_val
    }
    fun method_b(in_time : TimeIH) : TimeIH? {
        var c_time=TimeIH(22,10,11)
        var ret_val = this.method_d(in_time,c_time)
        return ret_val
    }
    fun method_c(in_time : TimeIH) : TimeIH?{
        var c_time=TimeIH(22,10,11)
        var ret_val = this.method_e(c_time,in_time)
        return ret_val
    }
    fun method_d(in_time1 : TimeIH,in_time2 : TimeIH) : TimeIH?{
        var s_seconds :Int = in_time1.seconds+in_time2.seconds
        var s_minutes :Int= in_time1.minutes+in_time2.minutes
        var s_hours:Int = in_time1.hours+in_time2.hours
        if(s_seconds > 59)
        {this.seconds = s_seconds - 60
            s_minutes+=1
        }else this.seconds = s_seconds
        if(s_minutes > 59)
        {this.minutes = s_minutes- 60
            s_hours+=1
        }else this.minutes = s_minutes
        if(s_hours > 23) {
            this.hours = s_hours - 24
        }else this.hours = s_hours
        this.method_a()
        return  this
    }
    fun method_e(in_time1 : TimeIH,in_time2 : TimeIH) : TimeIH?{
        var s_seconds = in_time1.seconds-in_time2.seconds
        var s_minutes = in_time1.minutes-in_time2.minutes
        var s_hours = in_time1.hours-in_time2.hours
        if(s_seconds < 0)
        {   this.seconds = s_seconds + 60
            s_minutes-=1
        }else this.seconds = s_seconds
        if(s_minutes < 0)
        {this.minutes = s_minutes + 60
            s_hours-=1
        }else this.minutes = s_minutes
        if(s_hours < 0) {
            this.hours = s_hours + 24
        }else this.hours = s_hours
        this.method_a()
        return  this
    }
    companion object {
        fun from_date(date: Date): TimeIH = with(GregorianCalendar()) {
            time = date
            val hours = get(Calendar.HOUR_OF_DAY)
            val minutes = get(Calendar.MINUTE)
            val seconds = get(Calendar.SECOND)
            TimeIH(hours, minutes, seconds)
        }
    }
}

fun main() {

    val students_list = "Дмитренко Олександр - ІП-84; " +
            "Матвійчук Андрій - ІВ-83; " +
            "Лесик Сергій - ІО-82; " +
            "Ткаченко Ярослав - ІВ-83; " +
            "Аверкова Анастасія - ІО-83; " +
            "Соловйов Даніїл - ІО-83; " +
            "Рахуба Вероніка - ІО-81; " +
            "Кочерук Давид - ІВ-83; " +
            "Лихацька Юлія- ІВ-82; " +
            "Головенець Руслан - ІВ-83; " +
            "Ющенко Андрій - ІО-82; " +
            "Мінченко Володимир - ІП-83; " +
            "Мартинюк Назар - ІО-82; " +
            "Базова Лідія - ІВ-81; " +
            "Снігурець Олег - ІВ-81; " +
            "Роман Олександр - ІО-82; " +
            "Дудка Максим - ІО-81; " +
            "Кулініч Віталій - ІВ-81; " +
            "Жуков Михайло - ІП-83; " +
            "Грабко Михайло - ІВ-81; " +
            "Іванов Володимир - ІО-81; " +
            "Востриков Нікіта - ІО-82; " +
            "Бондаренко Максим - ІВ-83; " +
            "Скрипченко Володимир - ІВ-82; " +
            "Кобук Назар - ІО-81; " +
            "Дровнін Павло - ІВ-83; " +
            "Тарасенко Юлія - ІО-82; " +
            "Дрозд Світлана - ІВ-81; " +
            "Фещенко Кирил - ІО-82; " +
            "Крамар Віктор - ІО-83; " +
            "Іванов Дмитро - ІВ-82"

    val groups = students_list
            .split(';')
            .map { it.split("- ").map(String::trim) }
            .groupBy { it[1] }
            .mapValues { (_, v) -> v.map { it[0] } }

    println("Задание №1")
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – відсортований масив студентів, які відносяться до відповідної групи
    println(groups)

    val points = listOf(12, 12, 12, 12, 12, 12, 12, 16)
    val stud_points = groups
            .mapValues { (_, students) ->
                students.map {
                    it to List(points.size) { i -> randomValue(points[i]) }
                }.groupBy {
                    it.first
                }.mapValues { (_, v) -> v.first().second }
            }

    println("Задание №2")
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – словник, де:
    //   - ключ – студент, який відносяться до відповідної групи
    //   - значення – масив з оцінками студента (заповніть масив випадковими значеннями, використовуючи функцію `randomValue(maxValue: Int) -> Int`)
    println(stud_points)

    val sum_points = stud_points.mapValues { (_, v) ->
        v.mapValues { (_, marks) -> marks.sum() }
    }

    println("Задание №3")
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – словник, де:
    //   - ключ – студент, який відносяться до відповідної групи
    //   - значення – сума оцінок студента
    println(sum_points)

    val avg_group = sum_points.mapValues { (_, v) ->
        v.values.sum().toFloat() / (v.size * points.size)
    }

    println("Задание №4")
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – середня оцінка всіх студентів групи
    println(avg_group)

    val passedPerGroup = sum_points.mapValues { (_, v) ->
        v.filterValues { it >= 60 }.keys
    }

    println("Задание №5")
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – масив студентів, які мають >= 60 балів
    println(passedPerGroup)

    var time_default = TimeIH()
    var time_set = TimeIH(10,12,13)
    var c_time=TimeIH(22,10,11)
    val createdAt = Date()
    var time_date = TimeIH.from_date(createdAt)

    println("Задание №1 TimeIH")
    time_default.method_a()
    time_date.method_a()
    time_set.method_a()
    println("Задание №2 TimeIH")
    time_default.method_b(time_set)
    println("Задание №3 TimeIH")
    time_default.method_c(time_set)
    println("Задание №4 TimeIH")
    time_default.method_d(c_time,time_set)
    println("Задание №5 TimeIH")
    time_default.method_e(c_time,time_set)
}

fun randomValue(maxValue: Int): Int {
    val random = Random()
    return when (random.nextInt(6)) {
        1 -> ceil(maxValue * 0.7).toInt()
        2 -> ceil(maxValue * 0.9).toInt()
        3, 4, 5 -> maxValue
        else -> 0
    }
}

package com.example.aston_intensive_course_work.domain.utils

fun getIdListFromUrlList(urlList: List<String>): List<Int> {
    val idList = ArrayList<Int>()

    for (item in urlList) {
        val list = item.split("/")
        idList.add(list[list.size - 1].toInt())
    }

    return idList
}
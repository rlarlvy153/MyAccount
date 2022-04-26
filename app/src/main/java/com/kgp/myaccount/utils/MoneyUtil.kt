package com.kgp.myaccount.utils

object MoneyUtil {
    fun formatMoney(money: Long): String {
        val moneyStringBuilder = StringBuilder()

        val absMoney = kotlin.math.abs(money).toString()
        //TODO 스트링 포메팅 유틸 등으로 이동
        absMoney.reversed().forEachIndexed { index, c ->
            if (index != 0 && index % 3 == 0) {
                moneyStringBuilder.append(',')
            }
            moneyStringBuilder.append(c)
        }
        if (money < 0) {
            moneyStringBuilder.append('-')
        }
        return moneyStringBuilder.toString().reversed()
    }
}
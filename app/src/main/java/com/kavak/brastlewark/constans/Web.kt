package com.kavak.brastlewark.constans

object Web {

        const val LOG_API = "logAPI"
        const val TIMEOUT_MS: Long = 25000
        const val PAGING_ITEMS = 15

        val URL_BASE: String ="https://raw.githubusercontent.com/"
        private const val API: String = "rrafols/mobile_test/master"
        const val EP_DATA = "/data.json"


        const val URL_SERVICE_GET_CITIZENS: String = "$API$EP_DATA"

}
package com.kavak.brastlewark.constans

import androidx.paging.PagedList

object Constants {

    const val DB_NAME = "BrastlewarkDB"

    const val SPM_FILTER_AGE = "KEY_MIN_AGE"
    const val SPM_FILTER_WEIGHT = "KEY_FILTER_WEIGHT"
    const val SPM_FILTER_HEIGHT = "KEY_FILTER_HEIGHT"

    const val TAG_FRAGMENT_DIALOG_DETAIL = "TAG_FRAGMENT_DIALOG_DETAIL"
    const val TAG_FRAGMENT_DIALOG_FILTER = "TAG_FRAGMENT_DIALOG_FILTER"
    const val REQUEST_CODE_DIALOG_FILTER = 100

    val pageConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(20)
        .setPrefetchDistance(10)
        .setInitialLoadSizeHint(40)
        .build()
}
package com.kavak.brastlewark.interfaces

import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.data.remote.interfaces.IWebErrorListener

interface IHome {
    interface UseCases {
        /**
         * Method used to fetch the available citizens
         */
        fun getCitizens()

        /**
         * Method used to show details of the citizen
         */
        fun onCitizenSelected(citizen: Citizen)
    }

    interface RequestListener : IWebErrorListener {
        fun onFetchResponse(
            payload: List<Citizen>?
        )
    }

}

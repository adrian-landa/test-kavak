package com.kavak.brastlewark.interfaces

interface IRecyclerListener<T> {
    /**
     * Method used to communicate to component that an item has been clicked
     */
    fun onItemClick(item: T)
}
package com.example.psinterviewapp.ui

interface ListExpansionUpdater<T> {

    fun expandCollapseListItem(item: T)
}
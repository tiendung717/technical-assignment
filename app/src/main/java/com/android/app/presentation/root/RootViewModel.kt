package com.android.app.presentation.root

import androidx.lifecycle.ViewModel
import com.android.designsystem.components.BottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor() : ViewModel() {

    val navItems: List<BottomNavigationItem> = BottomTab.entries.map { tab ->
        BottomNavigationItem(text = tab.title, icon = tab.icon)
    }

    val selectedTab: StateFlow<BottomTab>
        field = MutableStateFlow(BottomTab.DISCOVER)

    fun navItemFor(tab: BottomTab): BottomNavigationItem = navItems[tab.ordinal]

    fun onNavItemClicked(item: BottomNavigationItem): BottomTab? {
        val tab = BottomTab.entries[navItems.indexOf(item)]
        if (tab == selectedTab.value) {
            return null
        }
        selectedTab.value = tab
        return tab
    }

    fun onTabDisplayed(tab: BottomTab) {
        selectedTab.value = tab
    }
}

package com.android.app.presentation.root

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RootViewModelTest {

    private val viewModel = RootViewModel()

    @Test
    fun navItems_matchTabsInOrder() {
        assertEquals(BottomTab.entries.size, viewModel.navItems.size)

        BottomTab.entries.forEachIndexed { index, tab ->
            assertEquals(tab.title, viewModel.navItems[index].text)
            assertEquals(tab.icon, viewModel.navItems[index].icon)
        }
    }

    @Test
    fun selectedTab_defaultsToDiscover() {
        assertEquals(BottomTab.DISCOVER, viewModel.selectedTab.value)
    }

    @Test
    fun navItemFor_returnsItemAtTabPosition() {
        BottomTab.entries.forEach { tab ->
            assertEquals(viewModel.navItems[tab.ordinal], viewModel.navItemFor(tab))
        }
    }

    @Test
    fun onNavItemClicked_selectsTabAndReturnsIt() {
        val watchList = viewModel.navItemFor(BottomTab.WATCH_LIST)

        val selected = viewModel.onNavItemClicked(watchList)

        assertEquals(BottomTab.WATCH_LIST, selected)
        assertEquals(BottomTab.WATCH_LIST, viewModel.selectedTab.value)
    }

    @Test
    fun onNavItemClicked_alreadySelected_returnsNullAndKeepsSelection() {
        val discover = viewModel.navItemFor(BottomTab.DISCOVER)

        val selected = viewModel.onNavItemClicked(discover)

        assertNull(selected)
        assertEquals(BottomTab.DISCOVER, viewModel.selectedTab.value)
    }

    @Test
    fun onNavItemClicked_switchingBack_selectsPreviousTabAgain() {
        viewModel.onNavItemClicked(viewModel.navItemFor(BottomTab.MY_TRADE_ME))

        val selected = viewModel.onNavItemClicked(viewModel.navItemFor(BottomTab.DISCOVER))

        assertEquals(BottomTab.DISCOVER, selected)
        assertEquals(BottomTab.DISCOVER, viewModel.selectedTab.value)
    }

    @Test
    fun onTabDisplayed_updatesSelectionWithoutAClick() {
        viewModel.onTabDisplayed(BottomTab.NOTIFICATION)

        assertEquals(BottomTab.NOTIFICATION, viewModel.selectedTab.value)
    }
}

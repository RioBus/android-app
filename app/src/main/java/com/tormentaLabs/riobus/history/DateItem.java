package com.tormentaLabs.riobus.history;

import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.List;

class DateItem implements ParentListItem {

    private String title;
    private List<Line> items;

    DateItem(String title, List<Line> items) {
        this.title = title;
        this.items = items;
    }

    String getTitle() {
        return this.title;
    }

    /**
     * Getter for the list of this parent list item's child list items.
     * <p>
     * If list is empty, the parent list item has no children.
     *
     * @return A {@link List} of the children of this {@link ParentListItem}
     */
    @Override
    public List<Line> getChildItemList() {
        return items;
    }

    /**
     * Getter used to determine if this {@link ParentListItem}'s
     * {@link View} should show up initially as expanded.
     *
     * @return true if expanded, false if not
     */
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}

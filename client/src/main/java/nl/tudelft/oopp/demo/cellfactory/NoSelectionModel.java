package nl.tudelft.oopp.demo.cellfactory;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

    /**
     * <p>Returns a <b>read-only</b> ObservableList of all selected indices. The
     * ObservableList will be updated  by the selection model to always reflect
     * changes in selection. This can be observed by adding a
     * {@link ListChangeListener} to the returned ObservableList. </p>
     *
     * @return the list of selected indices
     */
    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return FXCollections.emptyObservableList();
    }

    /**
     * <p>Returns a <b>read-only</b> ObservableList of all selected items. The
     * ObservableList will be updated further by the selection model to always reflect
     * changes in selection. This can be observed by adding a
     * {@link ListChangeListener} to the returned ObservableList. </p>
     *
     * @return the list of selected items
     */
    @Override
    public ObservableList<T> getSelectedItems() {
        return FXCollections.emptyObservableList();
    }

    /**
     * <p>This method allows for one or more selections to be set at the same time.
     * It will ignore any value that is not within the valid range (i.e. greater
     * than or equal to zero, and less than the total number of items in the
     * underlying data model). Any duplication of indices will be ignored. </p>
     *
     * <p>If there is already one or more indices selected in this model, calling
     * this method will <b>not</b> clear these selections - to do so it is
     * necessary to first call clearSelection. </p>
     *
     * <p>The last valid value given will become the selected index / selected
     * item. </p>
     *
     * @param index   the first index to select
     * @param indices zero or more additional indices to select
     */
    @Override
    public void selectIndices(int index, int... indices) {

    }

    /**
     * <p>Convenience method to select all available indices.</p>
     */
    @Override
    public void selectAll() {

    }

    /**
     * A method that clears any selection prior to setting the selection to the
     * given index. The purpose of this method is to avoid having to call
     * {@link #clearSelection()} first, meaning that observers that are listening to
     * the {@link #selectedIndexProperty() selected index} property will not
     * see the selected index being temporarily set to -1.
     *
     * @param index The index that should be the only selected index in this
     *              selection model.
     */
    @Override
    public void clearAndSelect(int index) {

    }

    /**
     * <p>This will select the given index in the selection model, assuming the
     * index is within the valid range (i.e. greater than or equal to zero, and
     * less than the total number of items in the underlying data model). </p>
     *
     * <p>If there is already one or more indices selected in this model, calling
     * this method will <b>not</b> clear these selections - to do so it is
     * necessary to first call {@link #clearSelection()}. </p>
     *
     * <p>If the index is already selected, it will not be selected again, or
     * unselected. However, if multiple selection is implemented, then calling
     * select on an already selected index will have the effect of making the index
     * the new selected index (as returned by {@link #getSelectedIndex()}. </p>
     *
     * @param index The position of the item to select in the selection model.
     */
    @Override
    public void select(int index) {

    }

    /**
     * <p>This method will attempt to select the index that contains the given
     * object. It will iterate through the underlying data model until it finds
     * an item whose value is equal to the given object. At this point it will
     * stop iterating - this means that this method will not select multiple
     * indices. </p>
     *
     * @param obj The object to attempt to select in the underlying data model.
     */
    @Override
    public void select(T obj) {

    }

    /**
     * <p>This method will clear the selection of the item in the given index.
     * If the given index is not selected, nothing will happen. </p>
     *
     * @param index The selected item to deselect.
     */
    @Override
    public void clearSelection(int index) {

    }

    /**
     * <p>Clears the selection model of all selected indices. </p>
     */
    @Override
    public void clearSelection() {

    }

    /**
     * <p>Convenience method to inform if the given index is currently selected
     * in this SelectionModel. Is functionally equivalent to calling
     * <code>getSelectedIndices().contains(index)</code>. </p>
     *
     * @param index The index to check as to whether it is currently selected
     *              or not.
     * @return True if the given index is selected, false otherwise.
     */
    @Override
    public boolean isSelected(int index) {
        return false;
    }

    /**
     * This method is available to test whether there are any selected
     * indices/items. It will return true if there are <b>no</b> selected items,
     * and false if there are.
     *
     * @return Will return true if there are <b>no</b> selected items, and false if there are.
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * <p>This method will attempt to select the index directly before the current
     * focused index. If clearSelection is not called first, this method
     * will have the result of selecting the previous index, whilst retaining
     * the selection of any other currently selected indices.</p>
     *
     * <p>Calling this method will only succeed if:</p>
     *
     * <ul>
     *   <li>There is currently a lead/focused index.
     *   <li>The lead/focus index is not the first index in the control.
     *   <li>The previous index is not already selected.
     * </ul>
     *
     * <p>If any of these conditions is false, no selection event will take
     * place.</p>
     */
    @Override
    public void selectPrevious() {

    }

    /**
     * <p>This method will attempt to select the index directly after the current
     * focused index. If clearSelection is not called first, this method
     * will have the result of selecting the next index, whilst retaining
     * the selection of any other currently selected indices.</p>
     *
     * <p>Calling this method will only succeed if:</p>
     *
     * <ul>
     *   <li>There is currently a lead/focused index.
     *   <li>The lead/focus index is not the last index in the control.
     *   <li>The next index is not already selected.
     * </ul>
     *
     * <p>If any of these conditions is false, no selection event will take
     * place.</p>
     */
    @Override
    public void selectNext() {

    }

    /**
     * <p>This method will attempt to select the first index in the control. If
     * clearSelection is not called first, this method
     * will have the result of selecting the first index, whilst retaining
     * the selection of any other currently selected indices.</p>
     *
     * <p>If the first index is already selected, calling this method will have
     * no result, and no selection event will take place.</p>
     */
    @Override
    public void selectFirst() {

    }

    /**
     * <p>This method will attempt to select the last index in the control. If
     * clearSelection is not called first, this method
     * will have the result of selecting the last index, whilst retaining
     * the selection of any other currently selected indices.</p>
     *
     * <p>If the last index is already selected, calling this method will have
     * no result, and no selection event will take place.</p>
     */
    @Override
    public void selectLast() {

    }
}

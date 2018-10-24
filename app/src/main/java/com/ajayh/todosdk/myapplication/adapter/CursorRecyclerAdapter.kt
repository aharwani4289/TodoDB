package com.ajayh.todosdk.myapplication.adapter

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView


public abstract class CursorRecyclerAdapter<VH : RecyclerView.ViewHolder>(mContext: Context, c: Cursor) : RecyclerView.Adapter<VH>() {

    protected var mDataValid: Boolean = false
    protected var mCursor: Cursor? = init(c)
    protected var mRowIDColumn: Int = 0

    public fun getItem(position: Int) {
        if (mDataValid && mCursor != null) {
            mCursor!!.moveToPosition(position);
            mCursor
        } else {
            null
        }
    }

    private fun init(c: Cursor): Cursor {
        var cursorPresent: Boolean = (c != null)
        mDataValid = cursorPresent
        mRowIDColumn = if (cursorPresent) c.getColumnIndexOrThrow("_id") else -1
        setHasStableIds(true);
        return c;
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!mDataValid) {
            throw IllegalStateException("this should only be called when the cursor is valid");
        }

        if (!mCursor!!.moveToPosition(position)) {
            throw IllegalStateException("couldn't move cursor to position " + position);
        }

        onBindViewHolder(holder, mCursor!!);
    }

    public abstract fun onBindViewHolder(holder: VH, cursor: Cursor);

    public fun getCursor(): Cursor? {
        return mCursor;
    }

    override fun getItemCount(): Int {
        if (mDataValid && mCursor != null) {
            return mCursor!!.getCount();
        } else {
            return 0;
        }
    }

    override fun getItemId(position: Int): Long {
        try {
            if (hasStableIds() && mDataValid && mCursor != null) {
                if (mCursor!!.moveToPosition(position)) {
                    return mCursor!!.getLong(mRowIDColumn);
                } else {
                    return RecyclerView.NO_ID;
                }
            } else {
                return RecyclerView.NO_ID;
            }
        } catch (e: Exception) {
            return RecyclerView.NO_ID;
        }
    }

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
     * closed.
     *
     * @param cursor The new cursor to be used
     */
    public fun changeCursor(cursor: Cursor) {
        val old: Cursor? = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     *
     * @param newCursor The new cursor to be used.
     * @return Returns the previously set Cursor, or null if there wasa not one.
     * If the given new Cursor is the same instance is the previously set
     * Cursor, null is also returned.
     */
    public fun swapCursor(newCursor: Cursor): Cursor? {
        if (newCursor == mCursor) {
            return null
        }
        val oldCursor: Cursor = mCursor!!
        mCursor = newCursor
        var itemCount: Int = getItemCount()
        if (newCursor != null) {
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id")
            mDataValid = true
            // notify the observers about the new cursor
            notifyDataSetChanged()
        } else {
            mRowIDColumn = -1
            mDataValid = false
            // notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, itemCount)
        }
        return oldCursor
    }


    /**
     * <p>Converts the cursor into a CharSequence. Subclasses should override this
     * method to convert their results. The default implementation returns an
     * empty String for null values or the default String representation of
     * the value.</p>
     *
     * @param cursor the cursor to convert to a CharSequence
     * @return a CharSequence representing the value
     */
    public fun convertToString(cursor: Cursor): CharSequence {
        return if (cursor == null) "" else cursor.toString()
    }

}
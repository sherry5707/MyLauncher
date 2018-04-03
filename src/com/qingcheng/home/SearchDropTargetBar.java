/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qingcheng.home;

import android.animation.Animator;

import com.qingcheng.home.R;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.mediatek.launcher3.ext.LauncherLog;
/*
 * Ths bar will manage the transition between the QSB search bar and the delete drop
 * targets so that each of the individual IconDropTargets don't have to.
 */
public class SearchDropTargetBar extends FrameLayout implements DragController.DragListener {
    private static final String TAG = "SearchDropTargetBar";
    private static final int sTransitionInDuration = 240;
    private static final int sTransitionOutDuration = 190;

    private ObjectAnimator mDropTargetBarAnim;
    // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
    //private ObjectAnimator mQSBSearchBarAnim;
    // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    private static final AccelerateInterpolator sAccelerateInterpolator =
            new AccelerateInterpolator();

    private boolean mIsSearchBarHidden;
    // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
    //private View mQSBSearchBar;
    // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    private View mDropTargetBar;
    private ButtonDropTarget mInfoDropTarget;
    private ButtonDropTarget mDeleteDropTarget;
    private int mBarHeight;
    private boolean mDeferOnDragEnd = false;

    private Drawable mPreviousBackground;
    private boolean mEnableDropDownDropTargets;

    public SearchDropTargetBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchDropTargetBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(Launcher launcher, DragController dragController) {
        dragController.addDragListener(this);
        dragController.addDragListener(mInfoDropTarget);
        dragController.addDragListener(mDeleteDropTarget);
        dragController.addDropTarget(mInfoDropTarget);
        dragController.addDropTarget(mDeleteDropTarget);
        dragController.setFlingToDeleteDropTarget(mDeleteDropTarget);
        mInfoDropTarget.setLauncher(launcher);
        mDeleteDropTarget.setLauncher(launcher);
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        /*mQSBSearchBar = launcher.getQsbBar();
        if (mEnableDropDownDropTargets) {
            mQSBSearchBarAnim = LauncherAnimUtils.ofFloat(mQSBSearchBar, "translationY", -mBarHeight,
                    -mBarHeight);
        } else {
            mQSBSearchBarAnim = LauncherAnimUtils.ofFloat(mQSBSearchBar, "alpha", 0f, 0f);
        }
        setupAnimation(mQSBSearchBarAnim, mQSBSearchBar);*/
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }

    private void prepareStartAnimation(View v) {
        // Enable the hw layers before the animation starts (will be disabled in the onAnimationEnd
        // callback below)
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    private void setupAnimation(ObjectAnimator anim, final View v) {
        anim.setInterpolator(sAccelerateInterpolator);
        anim.setDuration(sTransitionInDuration);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Get the individual components
        mDropTargetBar = findViewById(R.id.drag_target_bar);
        mInfoDropTarget = (ButtonDropTarget) mDropTargetBar.findViewById(R.id.info_target_text);
        mDeleteDropTarget = (ButtonDropTarget) mDropTargetBar.findViewById(R.id.delete_target_text);

        mInfoDropTarget.setSearchDropTargetBar(this);
        mDeleteDropTarget.setSearchDropTargetBar(this);

        mEnableDropDownDropTargets =
            getResources().getBoolean(R.bool.config_useDropTargetDownTransition);

        // Create the various fade animations
        if (mEnableDropDownDropTargets) {
            LauncherAppState app = LauncherAppState.getInstance();
            DeviceProfile grid = app.getDynamicGrid().getDeviceProfile();
            mBarHeight = grid.searchBarSpaceHeightPx;
            mDropTargetBar.setTranslationY(-mBarHeight);
            mDropTargetBarAnim = LauncherAnimUtils.ofFloat(mDropTargetBar, "translationY",
                    -mBarHeight, 0f);

        } else {
            mDropTargetBar.setAlpha(0f);
            mDropTargetBarAnim = LauncherAnimUtils.ofFloat(mDropTargetBar, "alpha", 0f, 1f);
        }
        setupAnimation(mDropTargetBarAnim, mDropTargetBar);
    }

    public void finishAnimations() {
        prepareStartAnimation(mDropTargetBar);
        mDropTargetBarAnim.reverse();
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        //prepareStartAnimation(mQSBSearchBar);
        //mQSBSearchBarAnim.reverse();
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }

    /*
     * Shows and hides the search bar.
     */
    public void showSearchBar(boolean animated) {
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        /*boolean needToCancelOngoingAnimation = mQSBSearchBarAnim.isRunning() && !animated;
        if (!mIsSearchBarHidden && !needToCancelOngoingAnimation) return;
        if (animated) {
            prepareStartAnimation(mQSBSearchBar);
            mQSBSearchBarAnim.reverse();
        } else {
            mQSBSearchBarAnim.cancel();
            if (mEnableDropDownDropTargets) {
                mQSBSearchBar.setTranslationY(0);
            } else {
                mQSBSearchBar.setAlpha(0f);
            }
        }
        mIsSearchBarHidden = false;*/
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }
    public void hideSearchBar(boolean animated) {
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        /*boolean needToCancelOngoingAnimation = mQSBSearchBarAnim.isRunning() && !animated;
        if (mIsSearchBarHidden && !needToCancelOngoingAnimation) return;
        if (animated) {
            prepareStartAnimation(mQSBSearchBar);
            mQSBSearchBarAnim.start();
        } else {
            mQSBSearchBarAnim.cancel();
            if (mEnableDropDownDropTargets) {
                mQSBSearchBar.setTranslationY(-mBarHeight);
            } else {
                mQSBSearchBar.setAlpha(0f);
            }
        }
        mIsSearchBarHidden = true;*/
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }

    /*
     * Gets various transition durations.
     */
    public int getTransitionInDuration() {
        return sTransitionInDuration;
    }
    public int getTransitionOutDuration() {
        return sTransitionOutDuration;
    }

    /*
     * DragController.DragListener implementation
     */
    @Override
    public void onDragStart(DragSource source, Object info, int dragAction) {
        if (LauncherLog.DEBUG_DRAG) {
            LauncherLog.d(TAG, "onDragStart: source = " + source + ", info = " + info
                    + ", dragAction = " + dragAction + ", this = " + this);
        }

        // Animate out the QSB search bar, and animate in the drop target bar
        prepareStartAnimation(mDropTargetBar);
        mDropTargetBarAnim.start();
        

        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        /*if (!mIsSearchBarHidden) {
            prepareStartAnimation(mQSBSearchBar);
            mQSBSearchBarAnim.start();
        /// M. ALSP0190406, sometimes, the mIsSearchBarHidden is ture,
        /// but we need hide searchbar. It is like work around solution.
        } else {
            LauncherLog.d(TAG, "onDragStart: mIsSearchBarHidden is " + mIsSearchBarHidden);
            hideSearchBar(false);
        /// M.
        }*/
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }

    public void deferOnDragEnd() {
        mDeferOnDragEnd = true;
    }

    @Override
    public void onDragEnd() {
        if (LauncherLog.DEBUG_DRAG) {
            LauncherLog.d(TAG, "onDragEnd: mDeferOnDragEnd = " + mDeferOnDragEnd);
        }

        if (!mDeferOnDragEnd) {
            // Restore the QSB search bar, and animate out the drop target bar
            prepareStartAnimation(mDropTargetBar);
            mDropTargetBarAnim.reverse();

            // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
            //if (!mIsSearchBarHidden) {
            //    prepareStartAnimation(mQSBSearchBar);
            //    mQSBSearchBarAnim.reverse();
            //}
            // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
        } else {
            mDeferOnDragEnd = false;
        }
    }

    public void onSearchPackagesChanged(boolean searchVisible, boolean voiceVisible) {
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        /*if (LauncherLog.DEBUG_DRAG) {
            LauncherLog.d(TAG, "onSearchPackagesChanged: searchVisible = " + searchVisible
                    + ", voiceVisible = " + voiceVisible + ", mQSBSearchBar = " + mQSBSearchBar);
        }

        if (mQSBSearchBar != null) {
            Drawable bg = mQSBSearchBar.getBackground();
            if (bg != null && (!searchVisible && !voiceVisible)) {
                // Save the background and disable it
                mPreviousBackground = bg;
                mQSBSearchBar.setBackgroundResource(0);
            } else if (mPreviousBackground != null && (searchVisible || voiceVisible)) {
                // Restore the background
                mQSBSearchBar.setBackground(mPreviousBackground);
            }
        }*/
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }

    public Rect getSearchBarBounds() {
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 start
        /*if (mQSBSearchBar != null) {
            final int[] pos = new int[2];
            mQSBSearchBar.getLocationOnScreen(pos);

            final Rect rect = new Rect();
            rect.left = pos[0];
            rect.top = pos[1];
            rect.right = pos[0] + mQSBSearchBar.getWidth();
            rect.bottom = pos[1] + mQSBSearchBar.getHeight();
            return rect;
        } else {
            return null;
        }*/
    	return null;
        // Temporary delete, when add quick search function, reopen it Jing.Wu 20160920 end
    }
}
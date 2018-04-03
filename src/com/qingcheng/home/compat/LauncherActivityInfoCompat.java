/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.qingcheng.home.compat;

import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public abstract class LauncherActivityInfoCompat {

    LauncherActivityInfoCompat() {
    }

    public abstract ComponentName getComponentName();
    public abstract UserHandleCompat getUser();
    public abstract CharSequence getLabel();
    public abstract Drawable getIcon(int density);
    public abstract ApplicationInfo getApplicationInfo();
    public abstract long getFirstInstallTime();
    public abstract Drawable getBadgedIcon(int density);
}

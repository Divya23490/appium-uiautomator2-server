/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.uiautomator2.model.api;

import android.graphics.Rect;

public class ElementRectModel implements BaseModel {
    public Integer x;
    public Integer y;
    public Integer width;
    public Integer height;

    public ElementRectModel() {}

    public ElementRectModel(Rect rect) {
        this.x = rect.left;
        this.y = rect.top;
        this.width = rect.width();
        this.height = rect.height();
    }
}

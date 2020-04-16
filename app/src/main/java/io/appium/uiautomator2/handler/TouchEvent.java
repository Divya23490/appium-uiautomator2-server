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

package io.appium.uiautomator2.handler;

import android.graphics.Rect;

import io.appium.uiautomator2.model.api.touch.appium.TouchEventModel;
import io.appium.uiautomator2.model.api.touch.appium.TouchEventParams;

import androidx.test.uiautomator.UiObjectNotFoundException;

import io.appium.uiautomator2.common.exceptions.ElementNotFoundException;
import io.appium.uiautomator2.common.exceptions.InvalidElementStateException;
import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.AndroidElement;
import io.appium.uiautomator2.model.AppiumUIA2Driver;
import io.appium.uiautomator2.model.Session;
import io.appium.uiautomator2.utils.Logger;

import static io.appium.uiautomator2.utils.ModelUtils.toModel;
import static io.appium.uiautomator2.utils.ModelValidators.requireInteger;

public abstract class TouchEvent extends SafeRequestHandler {
    protected int clickX, clickY;
    protected AndroidElement element;
    protected TouchEventParams params;

    public TouchEvent(String mappedUri) {
        super(mappedUri);
    }

    @Override
    protected AppiumResponse safeHandle(IHttpRequest request) throws UiObjectNotFoundException {
        params = toModel(request, TouchEventModel.class).params;
        final String elementId = params.getUnifiedId();
        if (elementId != null && params.x == null && params.y == null) {
            /*
             * Finding centerX and centerY.
             */
            Session session = AppiumUIA2Driver.getInstance().getSessionOrThrow();
            element = session.getKnownElements().getElementFromCache(elementId);
            if (element == null) {
                throw new ElementNotFoundException();
            }
            final Rect bounds = element.getBounds();
            clickX = bounds.centerX();
            clickY = bounds.centerY();
        } else { // no element so extract x and y from params
            clickX = requireInteger(params, "x");
            clickY = requireInteger(params,"y");
        }

        if (executeTouchEvent()) {
            return new AppiumResponse(getSessionId(request));
        }
        throw new InvalidElementStateException("Cannot perform touch on the element");
    }

    protected abstract boolean executeTouchEvent() throws UiObjectNotFoundException, UiAutomator2Exception;

    protected void printEventDebugLine(final String methodName, final Integer... duration) {
        String extra = "";
        if (duration.length > 0) {
            extra = ", duration: " + duration[0];
        }
        Logger.debug("Performing " + methodName + " x: " + clickX + ", y: " + clickY + extra);
    }
}

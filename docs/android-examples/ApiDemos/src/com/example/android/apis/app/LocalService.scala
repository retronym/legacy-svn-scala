/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.example.android.apis.app

import _root_.android.app.{Notification, NotificationManager,
                           PendingIntent, Service}
import _root_.android.app.Service._
import _root_.android.content.Intent
import _root_.android.content.Context._
import _root_.android.os.{Binder, IBinder, Parcel}
import _root_.android.util.Log
import _root_.android.widget.Toast

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application.  The {@link LocalServiceController}
 * and {@link LocalServiceBinding} classes show how to interact with the
 * service.
 *
 * <p>Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service.  This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 */
class LocalService extends Service {
  private var mNM: NotificationManager = _

  /**
   * Class for clients to access.  Because we know this service always
   * runs in the same process as its clients, we don't need to deal with
   * IPC.
   */
  class LocalBinder extends Binder {
    def getService: LocalService = LocalService.this
  }
    
  override def onCreate() {
    mNM = getSystemService(NOTIFICATION_SERVICE).asInstanceOf[NotificationManager]

    // Display a notification about us starting.  We put an icon in the status bar.
    showNotification()
  }

  override def onStartCommand(intent: Intent, flags: Int, startId: Int): Int = {
    Log.i("LocalService", "Received start id " + startId + ": " + intent);
    // We want this service to continue running until it is explicitly
    // stopped, so return sticky.
    START_STICKY
  }

  override def onDestroy() {
    // Cancel the persistent notification.
    mNM.cancel(R.string.local_service_started)

    // Tell the user we stopped.
    Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show()
  }

  override def onBind(intent: Intent): IBinder = {
    mBinder
  }

  // This is the object that receives interactions from clients.  See
  // RemoteService for a more complete example.
  private final val mBinder = new LocalBinder

  /**
   * Show a notification while this service is running.
   */
  private def showNotification() {
    // In this sample, we'll use the same text for the ticker and the
    // expanded notification
    val text = getText(R.string.local_service_started)

    // Set the icon, scrolling text and timestamp
    val notification = new Notification(R.drawable.stat_sample, text,
                                        System.currentTimeMillis)

    // The PendingIntent to launch our activity if the user selects this
    // notification
    val contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, classOf[LocalServiceController]), 0)

    // Set the info for the views that show in the notification panel.
    notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                                    text, contentIntent)

    // Send the notification.
    // We use a layout id because it is a unique number.  We use it later to cancel.
    mNM.notify(R.string.local_service_started, notification)
  }
}

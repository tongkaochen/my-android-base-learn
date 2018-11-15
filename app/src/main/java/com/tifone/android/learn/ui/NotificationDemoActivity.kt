package com.tifone.android.learn.ui

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.session.MediaSession
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.support.v4.app.Person
import android.support.v4.app.RemoteInput
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Button
import android.widget.Toast
import com.tifone.android.learn.R

class NotificationDemoActivity : AppCompatActivity(), View.OnClickListener, View.OnSystemUiVisibilityChangeListener {

    private lateinit var mNotificationManager: NotificationManager

    companion object {
        const val NOTIFICATION_DEMO_CHANNEL_ID: String = "notification_demo_channel_id"
        const val MEDIA_PREVIOUS_ACTION = "media_previous_action"
        const val MEDIA_PAUSE_ACTION = "media_pause_action"
        const val MEDIA_NEXT_ACTION = "media_next_action"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(R.layout.notification_demo_layout)
        findViewById<Button>(R.id.send_base_notification_btn).setOnClickListener(this)
        findViewById<Button>(R.id.cancel_notification_btn).setOnClickListener(this)
        findViewById<Button>(R.id.send_expandable_notification_btn).setOnClickListener(this)
        initNotificationManager()
        intent?.let {
            val result: Bundle? = RemoteInput.getResultsFromIntent(it)
            result?.apply {
                Toast.makeText(this@NotificationDemoActivity, getString("reply_result"),
                        Toast.LENGTH_LONG).show()
                mNotificationManager.notify(1000, createMessagingNotification("NEW"))
            }
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window?.decorView?.setOnSystemUiVisibilityChangeListener(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onSystemUiVisibilityChange(visibility: Int) {
        if (visibility == View.VISIBLE) {
            Handler().postDelayed( {
                window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }, 2000)
        }
    }

    override fun onResume() {
        super.onResume()
        window?.decorView?.apply {
            val type = 1
            when(type) {
                1 -> {
                    // immersive
                    systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                }
                2 -> {
                    // lean back
                    systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                }
                3 -> {
                    // immersive sticky
                    systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                }
            }

            //supportActionBar?.hide()

        }
//        window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun onPause() {
        super.onPause()
        window?.decorView?.apply {
            systemUiVisibility = 0
            //supportActionBar?.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_favorite -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                window?.decorView?.apply {
                    systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                    supportActionBar?.hide()
                }

            }
            R.id.action_settings -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                window?.decorView?.apply {
                    systemUiVisibility = 0
                }
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }

        }

        return return super.onOptionsItemSelected(item)
    }

    private fun initNotificationManager() {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_DEMO_CHANNEL_ID,
                    "Demo channel", NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(true)
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    private fun createBaseNotification(): Notification {
        var builder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, NOTIFICATION_DEMO_CHANNEL_ID)

//        builder.apply {
//            setContentTitle("Base notification")
//            setContentText("Base text")
//            setSmallIcon(R.drawable.ic_control_notif)
//            setAutoCancel(true)
//            setNumber(100)
//        }
        builder = builder.also {
            it.apply {
                setContentTitle("Base notification")
                setContentText("Base text")
                setSmallIcon(R.drawable.ic_control_notif)
                setAutoCancel(true)
                setNumber(100)
            }
            title = "Notification demo activity"
        }
        return builder.build()
    }

    private fun createMessagingNotification(title: String): Notification {
        val person: Person = Person.Builder().run {
            setName(title)
            build()
        }
        val person2: Person = Person.Builder().run {
            setName("Jock")
            build()
        }
        val person3: Person = Person.Builder().run {
            setName("Make")
            build()
        }
        val intent = Intent()
        intent.setClass(this, NotificationDemoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, 1000,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteInput: RemoteInput = RemoteInput.Builder("reply_result").run {
            setLabel("Reply label")
            build()
        }
        var directReplyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_control_notif, "Reply me", pendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build()
        val builder = NotificationCompat.Builder(this, NOTIFICATION_DEMO_CHANNEL_ID).apply {
            setContentTitle("Messaging notification")
            setSmallIcon(R.drawable.ic_control_notif)
            setStyle(NotificationCompat.MessagingStyle(person).run {
                conversationTitle = "Demo touch"
                addMessage("Hi", SystemClock.currentThreadTimeMillis(), person)
                addMessage("How are you", SystemClock.currentThreadTimeMillis() - 1000, person2)
                addMessage("I am fine", SystemClock.currentThreadTimeMillis() - 2000, person3)
            })
            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            addAction(directReplyAction)
            setNumber(100)
            setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            setAutoCancel(true)
        }

        return builder.build()

    }

    private fun createExpandableNotification(): Notification {

        val person = Person.Builder().setName("Tager").build()
        val person2 = Person.Builder().setName("Tony").build()
        val person3 = Person.Builder().setName("Kuller").build()
        val msg1 = NotificationCompat.MessagingStyle.Message("Hialllll",
                SystemClock.currentThreadTimeMillis(), person)
        val msg2 = NotificationCompat.MessagingStyle.Message("KKKKKKKKKK",
                SystemClock.currentThreadTimeMillis(), person2)
        val msg3 = NotificationCompat.MessagingStyle.Message("DDDDDDDDDDD",
                SystemClock.currentThreadTimeMillis(), person3)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_DEMO_CHANNEL_ID).apply {
            setContentTitle("A expandable notification")
            setContentText("Expandable notification body text")
            setSmallIcon(R.drawable.ic_control_notif)
            setLargeIcon(bitmap)
            setGroup("Messaging")
            setGroupSummary(true)
            setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
            setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.big_text)))
            setStyle(NotificationCompat.InboxStyle().addLine("Line 111111").addLine("Line 22222"))
            setStyle(NotificationCompat.MessagingStyle(person).run {
                conversationTitle = "Talk something"
                addMessage(msg1)
                addMessage(msg2)
                addMessage(msg3)
            })
            setNumber(100)
            setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
        }
        return builder.build()
    }

    @SuppressLint("NewApi")
    private fun buildPendingIntentWithBackStack() {
        val resultIntent = Intent(this, NotificationDemoActivity::class.java)
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction(MEDIA_PREVIOUS_ACTION)
        filter.addAction(MEDIA_PAUSE_ACTION)
        filter.addAction(MEDIA_NEXT_ACTION)
        registerReceiver(mReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mReceiver)
    }

    private var mReceiver  = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action) {
                MEDIA_PREVIOUS_ACTION -> {
                    Toast.makeText(this@NotificationDemoActivity,
                            MEDIA_PREVIOUS_ACTION, Toast.LENGTH_LONG).show()
                }
                MEDIA_PAUSE_ACTION -> {
                    Toast.makeText(this@NotificationDemoActivity,
                            MEDIA_PAUSE_ACTION, Toast.LENGTH_LONG).show()
                }
                MEDIA_NEXT_ACTION -> {
                    Toast.makeText(this@NotificationDemoActivity,
                            MEDIA_NEXT_ACTION, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun createMediaNotification(): Notification {
        val mediaPreviousIntent = Intent()
        mediaPreviousIntent.action = MEDIA_PREVIOUS_ACTION
        val mediaPauseIntent = Intent()
        mediaPauseIntent.action = MEDIA_PAUSE_ACTION
        val mediaNextIntent = Intent()
        mediaNextIntent.action = MEDIA_NEXT_ACTION
        val mediaPreviousPendingIntent = PendingIntent.getBroadcast(this, 1000,
                mediaPreviousIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val mediaPausePendingIntent = PendingIntent.getBroadcast(this, 1000,
                mediaPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val mediaNextPendingIntent = PendingIntent.getBroadcast(this, 1000,
                mediaNextIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val mediaSession = MediaSession(this, "media_session")
        val builder = Notification.Builder(this, NOTIFICATION_DEMO_CHANNEL_ID).apply {
            setVisibility(Notification.VISIBILITY_PUBLIC)
            setSmallIcon(R.drawable.ic_control_notif)
            setContentTitle("My music")
            setContentText("music content")
            addAction(R.drawable.ic_previous, "Previous", mediaPreviousPendingIntent)
            addAction(R.drawable.ic_pause, "Pause", mediaPausePendingIntent)
            addAction(R.drawable.ic_next, "Next", mediaNextPendingIntent)
            setStyle(Notification.MediaStyle().setShowActionsInCompactView(1)
                    .setMediaSession(mediaSession.sessionToken))

        }
        return builder.build()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_base_notification_btn -> {
                val notification = createMessagingNotification("Me")
                mNotificationManager.notify(100, notification)
            }
            R.id.send_expandable_notification_btn -> {
                for (i in 1..4) {
                    var notification = createExpandableNotification()
                    mNotificationManager.notify(101 + i, notification)
                }
            }
            R.id.cancel_notification_btn -> {
                mNotificationManager.cancelAll()
            }
        }
    }
}
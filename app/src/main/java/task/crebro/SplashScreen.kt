package task.crebro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        hideSystemBars()

        photo.setBackgroundResource(R.drawable.splash)

        var handler = Handler()

        sharedPreferences = this.getSharedPreferences(Constant.SHAREPREFNAME, Context.MODE_PRIVATE)

        if (sharedPreferences?.getString(Constant.USERNAMEKEY,"")?.isEmpty() == true){
            handler.postDelayed(Runnable {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            },3000)
        }else{
            handler.postDelayed(Runnable {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },3000)
        }

    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

}
package task.crebro

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.main_activity.*
import org.json.JSONObject
import task.crebro.adapter.CategoryAdapter
import task.crebro.listener.ExpandCollapseListener
import task.crebro.model.Category
import task.crebro.model.CategoryList
import java.util.ArrayList
import android.content.Intent
import android.content.SharedPreferences

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.tasks.OnCompleteListener





class MainActivity : AppCompatActivity() {

    private val adapter = CategoryAdapter()

    lateinit var mGoogleSignInClient: GoogleSignInClient

    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Constant.WEBSPPLICATIONTOKENID)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mGoogleSignInClient.signOut();

                val preferences = getSharedPreferences(Constant.SHAREPREFNAME, MODE_PRIVATE)
                val editor = preferences.edit()
                editor.clear()
                editor.apply()
                finish()

            }
        })

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, Constant.BASEURL, null,
            { response ->

                if(response.getBoolean("success")){

                    total.append(" "+response.getJSONObject("data").getJSONObject("summary").getString("total"))
                    confirm.append(" "+response.getJSONObject("data").getJSONObject("summary").getString("confirmedCasesIndian"))
                    confirmforein.append(" "+response.getJSONObject("data").getJSONObject("summary").getString("confirmedCasesForeign"))
                    discharged.append(" "+response.getJSONObject("data").getJSONObject("summary").getString("discharged"))
                    death.append(" "+response.getJSONObject("data").getJSONObject("summary").getString("deaths"))
                    confirmedbutno.append(" "+response.getJSONObject("data").getJSONObject("summary").getString("confirmedButLocationUnidentified"))


                    source.append(" "+response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getString("source"))
                    total1.append(" "+response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getString("total"))
                    recovered.append(" "+response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getString("recovered"))
                    death1.append(" "+response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getString("deaths"))
                    active.append(" "+response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getString("active"))

                    var data = response.getJSONObject("data").getJSONArray("regional")

                    var actualdata= listOf<Category>();

                    for (i in 0 until response.getJSONObject("data").getJSONArray("regional").length()){
                        //do your stuff
                        actualdata = listOf(
                            Category(data.getJSONObject(i).getString("loc"), listOf(CategoryList(data.getJSONObject(i).getString("discharged"),data.getJSONObject(i).getString("confirmedCasesIndian"),data.getJSONObject(i).getString("confirmedCasesForeign"),data.getJSONObject(i).getString("deaths"),data.getJSONObject(i).getString("totalConfirmed")))),
                        )

                        rv_list.setHasFixedSize(true)
                        rv_list.layoutManager = LinearLayoutManager(this)
                        adapter.setExpandCollapseListener(object : ExpandCollapseListener {
                            override fun onListItemExpanded(position: Int) {
                            }

                            override fun onListItemCollapsed(position: Int) {

                            }

                        })
                        rv_list.adapter = adapter
                        adapter.setExpandableParentItemList(actualdata)
                    }

                }
            },
            { error ->
                // TODO: Handle error
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.app_bar_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

//                adapter.filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)

    }

}
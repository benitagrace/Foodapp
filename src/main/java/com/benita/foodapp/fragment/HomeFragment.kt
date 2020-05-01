package com.benita.foodapp.fragment


import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.benita.foodapp.R
import com.benita.foodapp.adapter.HomeRecyclerAdapter
import com.benita.foodapp.model.Food
import com.benita.foodapp.util.ConnectionManager
import org.json.JSONException
import kotlin.collections.HashMap
import java.util.*

class HomeFragment : Fragment() {

    lateinit var recyclerHome: RecyclerView

    lateinit var layoutManager:RecyclerView.LayoutManager

    lateinit var recyclerAdapter: HomeRecyclerAdapter

    lateinit var progressLayout: RelativeLayout

    lateinit var progressBar: ProgressBar

    val ResInfoList= arrayListOf<Food>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        recyclerHome=view.findViewById(R.id.recyclerHome)

        layoutManager=LinearLayoutManager(activity)

        progressLayout=view.findViewById(R.id.progressLayout)

        progressBar=view.findViewById(R.id.progressBar)

        progressLayout.visibility=View.VISIBLE

        val queue =Volley.newRequestQueue(activity as Context)

        val url="http://13.235.250.119/v2/register/fetch_result"

        if(ConnectionManager().checkConnectivity(activity as Context)){

            val jsonObjectRequest=object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {

                try{

                    progressLayout.visibility=View.GONE
                val success = it.getBoolean("success")

                if(success){

                    val data=it.getJSONArray("data")
                    for(i in 0 until data.length()){

                        val foodJsonObject=data.getJSONObject(i)
                        val foodObject=Food(
                             foodJsonObject.getString("id"),
                            foodJsonObject.getString("name"),
                            foodJsonObject.getString("rating"),
                            foodJsonObject.getString("cost_for_one"),
                            foodJsonObject.getString("image_url")

                        )
                        ResInfoList.add(foodObject)
                        recyclerAdapter= HomeRecyclerAdapter(activity as Context,ResInfoList)

                        recyclerHome.adapter=recyclerAdapter

                        recyclerHome.layoutManager=layoutManager


                    }
                }else{
                    Toast.makeText(activity as Context,"some error has occurred",Toast.LENGTH_SHORT).show()
                }
            }catch (e:JSONException){

                    Toast.makeText(activity as Context,"some unexpected error occurred",Toast.LENGTH_SHORT).show()
                }

            }
                ,Response.ErrorListener {
                    if (activity != null) {

                        Toast.makeText(
                            activity as Context,
                            "volley error occurred",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }){



            override fun getHeaders():MutableMap<String,String>{
                val headers=HashMap<String,String>()
                headers["Content-type"]="application/json"
                headers["token"]="ac4ab7329bd28c"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }       else{
        val dialog= AlertDialog.Builder(activity as Context)
        dialog.setTitle("Error")
        dialog.setMessage("Internet Connection is not Found")
        dialog.setPositiveButton("open settings"){text,listener ->
            val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(settingsIntent)
            activity?.finish()
        }
        dialog.setNegativeButton("Exit"){text, listener->
            ActivityCompat.finishAffinity(activity as Activity)
        }
        dialog.create()
        dialog.show()

    }


    return view

}


}

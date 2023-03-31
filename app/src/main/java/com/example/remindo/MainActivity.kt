package com.example.remindo

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle :ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val drawerlayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)


        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.status_todo -> Toast.makeText(applicationContext, "Clicked A Faire", Toast.LENGTH_SHORT).show()
                R.id.status_inDoing -> Toast.makeText(applicationContext, "Clicked En cours", Toast.LENGTH_SHORT).show()
                R.id.status_done -> Toast.makeText(applicationContext, "Clicked Terminé", Toast.LENGTH_SHORT).show()

            }
            true
        }



        //val toolbar : Toolbar = findViewById<Toolbar>(R.id.toolbar)
        //val menu : ImageView = findViewById<ImageView>(R.id.imageMenu)

        //toolbar.setNavigationOnClickListener {

        //}








        //we save our tasks in this array
        val allTasks = arrayListOf("Tache 1", "Tache2", "Tache3")
        val allTasks2 = arrayListOf<Task>(Task("Vaccin", "A Faire", Date(2023,4, 25)),
            Task("Tennis", "En cours", Date(2023,7, 15)),
            Task("Projet informatique", "Terminer", Date(2023,5, 30)))

        val listViewTask : ListView = findViewById(R.id.listTask)

        //ato display correctly each task
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            allTasks
        )

        val adapterTask : TaskAdapter = TaskAdapter(allTasks2, this)

        listViewTask.adapter = adapterTask
        //listViewTask.setBackgroundColor(Color.CYAN)




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}

private class Task(title: String, status : String, date : Date?) {
    internal var title: String = title
    internal var status: String = status
    internal var date: Date? = date

}
private class TaskAdapter( items: ArrayList<Task>, ctx : Context) : ArrayAdapter<Task>(ctx, R.layout.activity_main, items){
    private class TaskItemViewHolder {
        internal var title: TextView? = null
        internal var status: TextView? = null
        internal var date: TextView? = null
        internal var btn: ImageButton? = null

    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        val viewHolder : TaskItemViewHolder

        if (view == null){
            val inflater = LayoutInflater.from(viewGroup.context)
            view = inflater.inflate(R.layout.list_item_task, viewGroup, false)

            viewHolder = TaskItemViewHolder()
            viewHolder.title = view!!.findViewById<TextView>(R.id.titre) as TextView
            viewHolder.status = view!!.findViewById(R.id.status) as TextView
            viewHolder.date = view!!.findViewById(R.id.date)

        }else{
            viewHolder = view.tag as TaskItemViewHolder
        }

        val task = getItem(position)
        viewHolder.title!!.text = task!!.title
        viewHolder.status!!.text = task!!.status

        //Change the color of the radioButton of the task according to his status
        if (task!!.status == "A Faire"){
            val radioButton = view!!.findViewById<RadioButton>(R.id.radioButton)
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.grey))
            radioButton.buttonTintList = colorStateList
        }else if (task!!.status == "En cours"){
            val radioButton = view!!.findViewById<RadioButton>(R.id.radioButton)
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.yellow))
            radioButton.buttonTintList = colorStateList
        }else{
            val radioButton = view!!.findViewById<RadioButton>(R.id.radioButton)
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.green))
            radioButton.buttonTintList = colorStateList
        }


        viewHolder.date!!.text = task!!.date!!.toString()

        view.tag = viewHolder

        return view
    }
}
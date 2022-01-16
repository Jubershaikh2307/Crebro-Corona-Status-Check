package task.crebro

import android.view.View
import android.widget.TextView
import task.crebro.model.CategoryList
import task.crebro.viewholder.ChildViewHolder

class CategoryListViewHolder(view: View) : ChildViewHolder(view){
    fun bind(categoryList : CategoryList){
        itemView.findViewById<TextView>(R.id.nameTv).text= "Discharged : "+ categoryList.name
        itemView.findViewById<TextView>(R.id.confirmedCasesIndian).text="Confirmed Cases Indian : "+categoryList.confirmedCasesIndian
        itemView.findViewById<TextView>(R.id.confirmedCasesForeign).text= "Confirmed Cases Foreign : "+ categoryList.confirmedCasesForeign
        itemView.findViewById<TextView>(R.id.deaths).text= "Deaths : "+ categoryList.deaths
        itemView.findViewById<TextView>(R.id.totalConfirmed).text= "Total Confirmed : "+ categoryList.totalConfirmed
    }
}
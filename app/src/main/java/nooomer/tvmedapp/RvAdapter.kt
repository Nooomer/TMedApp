package nooomer.tvmedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**Class for [RecyclerView.Adapter]*/
class RvAdapter(private var pplList: MutableList<MutableList<String?>>, private val itemCount: Int) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var largeTextView: TextView? = null
        var largeTextView2: TextView? = null
        var smallTextView: TextView? = null

        init {
            largeTextView = itemView.findViewById(R.id.textViewLarge)
            largeTextView2 = itemView.findViewById(R.id.textViewLarge2)
            smallTextView = itemView.findViewById(R.id.textViewSmall)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView?.text = pplList[position][0]
        holder.largeTextView2?.text = pplList[position][1]
        holder.smallTextView?.text = pplList[position][2]

    }

    override fun getItemCount(): Int {
        return itemCount
    }
}
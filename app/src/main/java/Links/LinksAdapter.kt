package Links

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinksAdapter:RecyclerView.Adapter<LinksAdapter.LinksHolder> {
    class LinksHolder(item: View): RecyclerView.ViewHolder(item) {
        fun bind(link: Links)
        {

        }
    }
}
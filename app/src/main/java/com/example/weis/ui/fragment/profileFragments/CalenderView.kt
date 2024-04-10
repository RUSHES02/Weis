import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.weis.R
import java.util.Date

class CustomCalendarAdapter(private val context: Context, private val calendarDates: List<Date>, private val specialDates: List<Date>) : BaseAdapter() {

    override fun getCount(): Int {
        return calendarDates.size
    }

    override fun getItem(position: Int): Any {
        return calendarDates[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val date = calendarDates[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: inflater.inflate(R.layout.activity_profile, parent, false)

        // Customize cell appearance here
        val cellBackground = view.findViewById<LinearLayout>(R.id.calendarView)

        // Check if the date is special and set background color accordingly
        if (specialDates.contains(date)) {
            cellBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.accentColor))
        } else {
            cellBackground.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }

        return view
    }
}
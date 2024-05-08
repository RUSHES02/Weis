//import com.applandeo.materialcalendarview.CalendarDay
//import com.google.android.material.datepicker.DayViewDecorator
//
//
//class EventDecorator(private val color: Int, dates: Collection<CalendarDay>?) :
//    DayViewDecorator {
//    private val dates: HashSet<CalendarDay>
//
//    init {
//        this.dates = HashSet(dates)
//    }
//
//    fun shouldDecorate(day: CalendarDay): Boolean {
//        return dates.contains(day)
//    }
//
//    fun decorate(view: DayViewFacade) {
//        view.addSpan(DotSpan(5, color))
//    }
//}
package com.kom.skyfly.presentation.history.filterflighthistory

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentCalendarViewBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class CalendarView : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCalendarViewBinding

    private var departureDate: LocalDate? = null
    private var returnDate: LocalDate? = null
    private var selectingReturnDate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCalendarViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setBottomSheetMaxHeight(maxPeekHeight)

        // Set up the titles using the daysOfWeek list
        val daysOfWeek = DayOfWeek.values()
        val titlesContainer = view.findViewById<ViewGroup>(R.id.titlesContainer)
        titlesContainer.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                textView.text = title
            }

        binding.calendarView.dayBinder =
            object : MonthDayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)

                override fun bind(
                    container: DayViewContainer,
                    data: CalendarDay,
                ) {
                    container.day = data
                    val textView = container.textView
                    textView.text = data.date.dayOfMonth.toString()

                    if (data.position == DayPosition.MonthDate) {
                        // Show the month dates. Remember that views are reused!
                        textView.visibility = View.VISIBLE
                        if ((selectingReturnDate && data.date >= departureDate) || (!selectingReturnDate && data.date == departureDate)) {
                            // If this is the selected departure or return date, show a round background and change the text color.
                            textView.setTextColor(Color.WHITE)
                            textView.setBackgroundResource(R.drawable.selection_background)
                        } else {
                            // If this is NOT the selected departure or return date, remove the background and reset the text color.
                            textView.setTextColor(Color.BLACK)
                            textView.background = null
                        }
                    } else {
                        // Hide in and out dates
                        textView.visibility = View.INVISIBLE
                    }

                    textView.alpha = if (data.position == DayPosition.MonthDate) 1f else 0.3f
                }
            }

        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                override fun bind(
                    container: MonthViewContainer,
                    data: CalendarMonth,
                ) {
                    if (container.titlesContainer.tag == null) {
                        container.titlesContainer.tag = data.yearMonth
                        container.titlesContainer.children.map { it as TextView }
                            .forEachIndexed { index, textView ->
                                val dayOfWeek = daysOfWeek[index]
                                val title =
                                    dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                                textView.text = title
                            }
                    }
                }
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100) // Adjust as needed
        val endMonth = currentMonth.plusMonths(100) // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)
    }

    private fun setBottomSheetMaxHeight(maxHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet = bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val textView = view.findViewById<TextView>(R.id.calendarDayText)
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                // Check the day position as we do not want to select in or out dates.
                if (day.position == DayPosition.MonthDate) {
                    if (departureDate == null || !selectingReturnDate) {
                        // If departure date is not set or we're not selecting return date, set clicked date as departure date.
                        departureDate = day.date
                        selectingReturnDate = true
                    } else if (returnDate == null) {
                        // If return date is not set, set clicked date as return date.
                        returnDate = day.date
                        selectingReturnDate = false
                    } else {
                        // If both departure and return dates are already set, reset them and set clicked date as departure date.
                        departureDate = day.date
                        returnDate = null
                        selectingReturnDate = true
                    }
                    // Update the calendar view to reflect the changes.
                    binding.calendarView.notifyDateChanged(day.date)
                    departureDate?.let { binding.calendarView.notifyDateChanged(it) }
                    returnDate?.let { binding.calendarView.notifyDateChanged(it) }
                }
            }
        }
    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val titlesContainer = view as ViewGroup
    }
}
